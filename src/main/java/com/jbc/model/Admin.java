package com.jbc.model;


import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.Table;

import com.jbc.controller.client.ClientType;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString
@Entity
@Table(name = "admin" , schema = "jbc_coupons")
public class Admin extends UserApp {
	
	
	@Column(nullable = false)
	String userName;

	public Admin() {
		role = ClientType.ROLE_Administrator;
	}

	public Admin(String email, String password) {
		this.userName=email;
		role = ClientType.ROLE_Administrator;
		this.email = email;
		this.password = password;
	}	
}
