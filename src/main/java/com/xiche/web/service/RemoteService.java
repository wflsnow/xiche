package com.xiche.web.service;

import java.util.Date;
import java.util.List;

import com.xiche.web.model.Employee;
import com.xiche.web.model.Order;

public interface RemoteService {
	boolean login(String username, String password);

	List<Employee> getEmployees(String city, String district);

	Employee getEmployee(String empno);

	List<String> getCities();

	List<String> getDistricts(String city);

	boolean addEmployee(Employee emp);

	boolean deleteEmployee(String empno);

	boolean editEmployee(Employee emp);

	List<Order> getOrders(String category, String status, Date start, Date end);
	
	boolean addOrder(Order order);

	boolean deleteOrder(String number);

	boolean editOrder(Order order);
	
	Order getOrder(String number);
}
