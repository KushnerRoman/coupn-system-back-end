package com.jbc.model;

import java.util.ArrayList;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.jbc.controller.client.ClientType;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "companies", schema = "jbc_coupons")
public class Company extends UserApp {




	@OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	

	private List<Coupon> coupons;

	@Column(nullable = false, unique = true)
	private String name;
	
	
	@Column(nullable = false)
	private String userName;

	
	public Company() {
		role = ClientType.ROLE_Company;
	}

	public Company(String name, String email, String password) {
		coupons = new ArrayList<Coupon>();
		role =ClientType.ROLE_Company;
		this.name = name;
		this.email = email;
		this.password = password;
		this.userName=email;
	}
	public Company(int id,String name, String email, String password) {
		coupons = new ArrayList<Coupon>();
		role =ClientType.ROLE_Company;
		this.name = name;
		this.email = email;
		this.password = password;
		this.userName=email;
		this.id=id;
	}

	@Override
	public String toString() {
		String string = "Company(id=" + id + ", name=" + name + ", email=" + email + ", password=" + password;
		if (coupons.isEmpty())
			return string + ", coupons=None)";
		return string + ", coupons=" + coupons + ")";
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	
}
