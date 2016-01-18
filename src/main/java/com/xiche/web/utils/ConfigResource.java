package com.xiche.web.utils;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

@Component
public class ConfigResource {

	private static Properties props;
	private static Resource resource;

	private ConfigResource() {
	}

	public static String get(String key) {
		String value = props.getProperty(key);
		return value == null ? "" : value;
	}
	
	public static String get(String configPath, String key) {
		try {
			Resource resource = new ClassPathResource(configPath);
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			String value = props.getProperty(key);
			return value == null ? "" : value;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@PostConstruct
	protected static void initialize() {
		System.out.println("===========Config File Loading===========");
		resource = new ClassPathResource("/config.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("===========Failed to Load Config File===========");
		}
		System.out.println("===========Config File Loaded===========");
	}
}
