package com.xiche.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiche.web.model.Employee;
import com.xiche.web.model.SearchForm;
import com.xiche.web.service.RemoteService;

@Controller
@RequestMapping({ "/" })
public class PageController {

	@Autowired
	private RemoteService service;

	@RequestMapping(method = GET)
	@RolesAllowed("USER")
	public String welcome(Model model, Principal principle) {
		model.addAttribute("user", principle.getName());
		return "home";
	}

	@RequestMapping(value = "staff", method = GET)
	public String staff(Model model) {
		model.addAttribute("cities", service.getCities());
		model.addAttribute(new SearchForm());
		return "staff";
	}

	@RequestMapping(value = "staff", method = POST)
	public String staff(SearchForm searchForm, Model model) {
		List<Employee> employees;
		if (searchForm.getEmpno() != null) {
			employees = new ArrayList<>();
			Employee emp = service.getEmployee(searchForm.getEmpno());
			if (emp != null) {
				employees.add(emp);
			}
		} else {
			employees = service.getEmployees(searchForm.getCity(), searchForm.getDistrict());
		}
		model.addAttribute("employees", employees);
		model.addAttribute("cities", service.getCities());
		if (searchForm.getCity() != null) {
			model.addAttribute("districts", service.getDistricts(searchForm.getCity()));
		}
		return "staff";
	}

	@RequestMapping(value = "staff/{city}/{district}", method = GET, headers = "Accept=application/json")
	public @ResponseBody Map<String, List<Employee>> staff(@PathVariable String city, @PathVariable String district,
			Model model) {
		Map<String, List<Employee>> map = new HashMap<>();
		map.put("aaData",
				service.getEmployees(city.equals("none") ? null : city, district.equals("none") ? null : district));
		return map;
	}

	@RequestMapping(value = "staff/{empno}", method = GET, headers = "Accept=application/json")
	public @ResponseBody Map<String, List<Employee>> staff(@PathVariable String empno, Model model) {
		Map<String, List<Employee>> map = new HashMap<>();
		List<Employee> emps = new ArrayList<>();
		emps.add(service.getEmployee(empno));
		map.put("aaData", emps);
		return map;
	}

	@RequestMapping(value = "cities", method = GET, headers = "Accept=application/json")
	public @ResponseBody List<String> cities(Model model) {
		return service.getCities();
	}

	@RequestMapping(value = "districts/{city}", method = GET, headers = "Accept=application/json")
	public @ResponseBody List<String> districts(@PathVariable String city, Model model) {
		return service.getDistricts(city);
	}

	@RequestMapping(value = "employee/{empno}", method = GET, headers = "Accept=application/json")
	public @ResponseBody Employee employee(@PathVariable String empno, Model model) {
		return service.getEmployee(empno);
	}

	@RequestMapping(value = "delemp/{empno}", method = GET, headers = "Accept=application/json")
	public @ResponseBody boolean deleteEmployee(@PathVariable String empno, Model model) {
		return service.deleteEmployee(empno);
	}

	@RequestMapping(value = "emp", method = POST, headers = "Accept=application/json")
	public @ResponseBody boolean employee(@Valid Employee employee, Errors errors) {
		if (errors.hasErrors()) {
			return false;
		}
		if (employee.getEmpno() != null && !employee.getEmpno().isEmpty()) {
			return service.editEmployee(employee);
		}
		return service.addEmployee(employee);
	}

	@RequestMapping(value = "emp/{empno}", method = GET)
	public String emp(@PathVariable String empno, Model model) {
		Employee employee = service.getEmployee(empno);
		List<String> cities = service.getCities();
		List<String> districts = service.getDistricts(employee.getCity());
		model.addAttribute("cities", cities);
		model.addAttribute("districts", districts);
		model.addAttribute("empForm", employee);
		return "emp";
	}

	@RequestMapping(value = "emp", method = GET)
	public String emp(Model model) {
		List<String> cities = service.getCities();
		model.addAttribute("cities", cities);
		model.addAttribute("empForm", new Employee());
		return "emp";
	}

	/* Converts empty strings into null when a form is submitted */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
}
