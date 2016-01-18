package com.xiche.web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class Employee {
	@NotNull
	@Size(min=2, max=20)
	private String name;
	@NotNull
	@Size(min=15, max=18)
	private String idno;
	private String empno;
	private String gender;
	private int age;
	private String address;
	@NotNull
	private String phone;
	private String city;
	private String district;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idNo) {
		this.idno = idNo;
	}

	public String getEmpno() {
		return empno;
	}

	public void setEmpno(String empNo) {
		this.empno = empNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}
}
