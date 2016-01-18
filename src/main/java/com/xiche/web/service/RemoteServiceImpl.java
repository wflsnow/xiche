package com.xiche.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.xiche.web.model.Employee;
import com.xiche.web.model.Order;

@Service
public class RemoteServiceImpl implements RemoteService {

	@Override
	public boolean login(String username, String password) {
		/*
		 * Map<String, Object> params = new HashMap<>(); params.put("userid",
		 * username); params.put("password", password); params.put("type", 1);
		 * JSONObject result =
		 * ServletUtils.getResultFromRemoteServletDoPost(Constants.
		 * SERVLET_LOGIN, "login", params); return
		 * result.getBoolean(Constants.JSON_RESULT);
		 */
		return true;
	}

	@Override
	public List<Employee> getEmployees(String city, String district) {
		List<Employee> emps = new ArrayList<>();
		if (city == null) {
			return employees;
		} else {
			for (Employee emp : employees) {
				if (emp.getCity().equals(city)) {
					if (district == null || (district != null && emp.getDistrict().equals(district))) {
						emps.add(emp);
					}
				}
			}
		}
		return emps;

	}

	@Override
	public Employee getEmployee(String empno) {
		for (Employee emp : employees) {
			if (emp.getEmpno().equals(empno)) {
				return emp;
			}
		}
		return null;
	}

	@Override
	public List<String> getCities() {
		return Arrays.asList(new String[] { "Weifang" });
	}

	@Override
	public List<String> getDistricts(String city) {
		return Arrays.asList(new String[] { "Kuiwen", "Weicheng", "Saiting", "Gaoxin", "Fangzi" });
	}

	@Override
	public boolean addEmployee(Employee emp) {
		emp.setEmpno("201500" + String.format("%02d", ++MAX_EMP_NO));
		employees.add(emp);
		return true;
	}

	@Override
	public boolean deleteEmployee(String empno) {
		for (Employee emp : employees) {
			if (emp.getEmpno().equals(empno)) {
				employees.remove(emp);
				return true;
			}
		}
		return true;
	}

	@Override
	public boolean editEmployee(Employee emp) {
		for (Employee employee : employees) {
			if (emp.getEmpno().equals(employee.getEmpno())) {
				employee.setAddress(emp.getAddress());
				employee.setAge(emp.getAge());
				employee.setCity(emp.getCity());
				employee.setDistrict(emp.getDistrict());
				employee.setGender(emp.getGender());
				employee.setIdno(emp.getIdno());
				employee.setName(emp.getName());
				employee.setPhone(emp.getPhone());
				return true;
			}
		}
		return true;
	}

	@Override
	public List<Order> getOrders(String category, String status, Date start, Date end) {

		return orders;
	}

	@Override
	public boolean addOrder(Order order) {
		orders.add(order);
		return true;
	}

	@Override
	public boolean deleteOrder(String number) {
		for (Order order : orders) {
			if (order.getNumber().equals(number)) {
				orders.remove(order);
			}
		}
		return true;
	}

	@Override
	public boolean editOrder(Order order) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Order getOrder(String number) {
		for (Order order : orders) {
			if (order.getNumber().equals(number)) {
				return order;
			}
		}
		return null;
	}

	// =============================================== Test Data
	private static void setEmployee(Employee emp) {
		String[] gender = { "Male", "Female" };
		String[] district = { "Kuiwen", "Weicheng", "Saiting", "Gaoxin", "Fangzi" };
		Random random = new Random();
		emp.setGender(gender[random.nextInt(gender.length)]);
		emp.setDistrict(district[random.nextInt(district.length)]);
		emp.setAddress("...");
		emp.setAge(random.nextInt(20) + 20);
	}

	private static List<Employee> employees = new ArrayList<>();
	private static List<Order> orders = new ArrayList<>();

	private static int MAX_EMP_NO = 88;

	static {
		for (int i = 1; i <= MAX_EMP_NO; i++) {
			Employee emp = new Employee();
			emp.setName("Emp" + String.format("%02d", i));
			emp.setEmpno("201500" + String.format("%02d", i));
			emp.setIdno("2102831985120110" + String.format("%02d", i));
			emp.setPhone("188888888" + String.format("%02d", i));
			emp.setCity("Weifang");
			setEmployee(emp);
			employees.add(emp);
		}

		for (int i = 1; i <= 30; i++) {
			Order order = new Order();
			order.setNumber("201500" + String.format("%02d", i));
			order.setCategory("Outer");
			order.setUser("13333333333");
			order.setLicense("LBY1111");
			order.setLocation("location");
			order.setStatus("Waiting");
			order.setPrice(12.00);
			order.setStateTime(new Date());
			order.setEstimationTime(new Date());
			order.setFinishedTime(new Date());
			order.setRate(5);
			order.setComments("Very good");
			order.setPicBefore("");
			order.setPicAfter("");
			orders.add(order);
		}
	}

}
