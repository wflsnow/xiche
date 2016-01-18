package com.xiche.web.model;

import java.util.Date;

public class Order {
	private String number;
	private String status;
	private String user;
	private String license;
	private String location;
	private String category;
	private double price;
	private Date startTime;
	private Date estimationTime;
	private Date finishedTime;
	private int rate;
	private String comments;
	private String picBefore;
	private String picAfter;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStateTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEstimationTime() {
		return estimationTime;
	}

	public void setEstimationTime(Date estimationTime) {
		this.estimationTime = estimationTime;
	}

	public Date getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(Date finishedTime) {
		this.finishedTime = finishedTime;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getPicBefore() {
		return picBefore;
	}

	public void setPicBefore(String picBefore) {
		this.picBefore = picBefore;
	}

	public String getPicAfter() {
		return picAfter;
	}

	public void setPicAfter(String picAfter) {
		this.picAfter = picAfter;
	}
}
