package com.jbc.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;


import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "coupons", schema = "jbc_coupons")
public class Coupon implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1685660501827043517L;


	/**
	 * 
	 */
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	
	@Column
	public Integer company;
	@Column
	private int amount;
	
	
	@Enumerated(EnumType.STRING)
	@Column
	private Category category;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private String image;
	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private double price;
	
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Coupon_VS_Customer", joinColumns = @JoinColumn(name = "Coupon_id"), inverseJoinColumns = @JoinColumn(name = "Customer_id"))
	@ApiModelProperty()
	@JsonBackReference
	private List<Customer> customers;

	public Coupon() {
	}

	public Coupon(int company_Id, int amount, Category category, String title, String description, String image,
			Date startDate, Date endDate, double price) {

		this.company = company_Id;
		this.amount = amount;
		this.category = category;
		this.title = title;
		this.description = description;
		this.image = image;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}
	public Coupon( int amount, Category category, String title, String description, String image,
			Date startDate, Date endDate, double price) {

		
		this.amount = amount;
		this.category = category;
		this.title = title;
		this.description = description;
		this.image = image;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = Company.class ,fetch = FetchType.LAZY)
	@JoinColumn(name = "companyId", nullable = true)
	@JsonPropertyDescription("The id of Exist Company ")
	public Integer getCompanyId() {
		return company;
	}

	public void setCompanyId(int companyId) {
		this.company = companyId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCategoryString() {
		
		return category.name();
	}

	public Category getCategory() {
		
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	





	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + company;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Coupon other = (Coupon) obj;
		if (category != other.category)
			return false;
		if (company != other.company)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", companyId=" + company + ", amount=" + amount + ", category=" + category
				+ ", title=" + title + ", description=" + description + ", image=" + image + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", price=" + price + "]";
	}

}
