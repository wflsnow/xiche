package com.xiche.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiche.web.model.Order;
import com.xiche.web.model.OrderSearch;
import com.xiche.web.service.RemoteService;

@Controller
@RequestMapping({ "/orders" })
public class OrderController {

	@Autowired
	private RemoteService service;

	@RequestMapping(method = GET)
	public String orders(Model model) {
		model.addAttribute(new OrderSearch());
		return "orders";
	}

	@RequestMapping(method = POST)
	public String orders(OrderSearch orderSearch, Model model) {
		model.addAttribute("orders", service.getOrders(orderSearch.getCategory(), orderSearch.getStatus(),
				orderSearch.getStart(), orderSearch.getEnd()));
		return "orders";
	}
	
	@RequestMapping(value = "/order", method = GET)
	public String order(Model model) {
		model.addAttribute("orderForm", new Order());
		return "order";
	}
	
	@RequestMapping(value = "/order/{number}", method = GET)
	public String order(@PathVariable String number, Model model) {
		model.addAttribute("orderForm", service.getOrder(number));
		return "order";
	}
	
	@RequestMapping(value = "/del/{number}", method = GET, headers = "Accept=application/json")
	public @ResponseBody boolean delete(@PathVariable String number, Model model) {
		return service.deleteOrder(number);
	}
	
	@RequestMapping(value = "/order", method = POST, headers = "Accept=application/json")
	public @ResponseBody boolean order(@Valid Order order, Errors errors) {
		if (errors.hasErrors()) {
			return false;
		}
		if (order.getNumber() != null) {
			return service.editOrder(order);
		}
		return service.addOrder(order);
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm"), true);
		binder.registerCustomEditor(Date.class, editor);
	}
}
