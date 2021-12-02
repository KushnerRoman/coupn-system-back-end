package com.jbc.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jbc.controller.client.ClientType;

@Entity
@Table(name = "customers", schema = "jbc_coupons")
public class Customer extends UserApp implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3593422648039579604L;
	/**
	 * 
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private int id;
	@Column(nullable = false)
	private String firstName;
	@Column(nullable = false)
	private String lastName;
	@Column(name = "email", nullable = false)
	private String email;

	@Column(nullable = false)
	@JsonIgnore()
	private String password;
	
	@Column(nullable = false)
	private String userName;

	@OneToMany
	@JoinTable(name = "Coupon_VS_Customer", joinColumns = @JoinColumn(name = " Customer_id"), inverseJoinColumns = @JoinColumn(name = "Coupon_id"))
	
	private List<Coupon> coupons;
	
	


	public Customer() {
		role = ClientType.ROLE_Customer;
	}

	public Customer(String firstName, String lastName, String email, String password) {
		
		this.role=ClientType.ROLE_Customer;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.userName=email;
		this.password = password;
	}


	public Customer(int id, String firstName, String lastName, String email, String password, List<Coupon> coupons) {
		this.role=ClientType.ROLE_Customer;
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.coupons = coupons;
		this.userName=email;
	}



	public void addCoupon(Coupon coupon) {
		coupons.add(coupon);
	}

	public void removeCoupon(Coupon coupon) {
		coupons.remove(coupon);
	}

	public void removeListOfCoupon(List<Coupon> list) {
		Iterator<Coupon> iter = list.iterator();
		while (iter.hasNext()) {
			Coupon deleCoupon = iter.next();

			removeCoupon(deleCoupon);

		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Coupon> getCustomerCoupon() {
		return coupons;
	}

	public void setCustomerCoupon(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}

	public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", coupons=" + coupons + "]";
	}

	

}
