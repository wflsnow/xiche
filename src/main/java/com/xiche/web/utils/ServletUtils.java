package com.xiche.web.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.xiche.web.config.Constants;

public class ServletUtils {

	private static final Log logger = LogFactory.getLog(ServletUtils.class);

	private ServletUtils() {
	}

	public static String encodeParams(Map<String, String> params) {
		StringBuffer sb = new StringBuffer();
		for (Entry<String, String> entry : params.entrySet()) {
			sb.append(encode(entry.getKey())).append("=").append(encode(entry.getValue())).append("&");

		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	public static URL createUrl(String servletName) {
		try {
			return new URL(ConfigResource.get(Constants.REMOTE_SERVER) + servletName);
		} catch (MalformedURLException e) {
			logger.error("Failed to create URL", e);
			throw new RuntimeException(e);
		}
	}

	public static JSONObject getResultFromRemoteServletDoGet(String servletName, Map<String, String> params) {
		URL url = createUrl(ConfigResource.get(servletName));
		URLConnection conn;
		try {
			conn = url.openConnection();
		} catch (IOException e) {
			logger.error("Failed to open connection", e);
			throw new RuntimeException(e);
		}
		conn.setDoOutput(true);

		BufferedWriter out;
		try {
			out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		} catch (IOException e) {
			logger.error("Failed to create buffered writer", e);
			throw new RuntimeException(e);
		}
		String data = encodeParams(params);
		try {
			out.write(data);
			out.flush();
		} catch (IOException e) {
			logger.error("Write data error", e);
			throw new RuntimeException(e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				logger.warn("Failed to close buffered writer", e);
			}
		}

		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} catch (IOException e) {
			logger.error("Failed to create buffered reader", e);
			throw new RuntimeException(e);
		}

		StringBuffer sb = new StringBuffer();
		String line;
		try {
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			logger.error("Read data error", e);
			throw new RuntimeException(e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				logger.warn("Failed to close buffered reader", e);
			}
		}

		return new JSONObject(sb.toString());
	}

	public static JSONObject getResultFromRemoteServletDoPost(String servletName, String method,
			Map<String, Object> params) {

		String url = ConfigResource.get(Constants.REMOTE_SERVER) + ConfigResource.get(servletName) + "?"
				+ Constants.PARAM_METHOD + "=" + method;
		String encoding = ConfigResource.get(Constants.ENCODING);

		HttpParams parms = new BasicHttpParams();
		parms.setParameter("content-type", "application/json");
		parms.setParameter("charset", encoding);
		HttpConnectionParams.setConnectionTimeout(parms, 8 * 1000);
		HttpConnectionParams.setSoTimeout(parms, 8 * 1000);
		HttpClient httpclient = new DefaultHttpClient(parms);
		HttpPost httppost = new HttpPost(url.toString());
		httppost.addHeader("Content-Type", "application/json;charset=" + encoding);

		JSONObject jobj = new JSONObject();
		for (String key : params.keySet()) {
			jobj.put(key, params.get(key));
		}
		StringEntity entity = new StringEntity(jobj.toString(), encoding);
		entity.setContentEncoding(encoding);

		httppost.setEntity(entity);
		try {
			HttpResponse response = httpclient.execute(httppost);
			int code = response.getStatusLine().getStatusCode();
			if (code == 200) {
				return new JSONObject(EntityUtils.toString(response.getEntity()));
			}
		} catch (IOException e) {
			logger.error("Post error", e);
			throw new RuntimeException(e);
		}

		return new JSONObject();

	}

	private static String encode(String string) {
		try {
			return URLEncoder.encode(string, ConfigResource.get(Constants.ENCODING));
		} catch (UnsupportedEncodingException e) {
			logger.warn("Encoding error: " + string, e);
			return string;
		}
	}
}
