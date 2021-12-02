package com.jbc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

import com.jbc.controller.client.ClientType;

@Entity
@Table(name = "roles" ,schema = "jbc_coupons")
public class Role implements Serializable {
	
  /**
	 * 
	 */
	private static final long serialVersionUID = 724954922837858740L;


/**
	 * 
	 */



	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	  
	  
  @Enumerated(EnumType.STRING)
  @NaturalId
  @Column
  private ClientType type;



	public Role(ClientType type) {
	
		this.type = type;
	}




	public Role() {
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public ClientType getType() {
		return type;
	}


	public void setType(ClientType type) {
		this.type = type;
	}
	
	
	
	

}
