package com.jbc.messages.requests;

import javax.validation.constraints.NotBlank;

public class PricedCoupons {
	
	@NotBlank
	private int customerId;
	
	@NotBlank
	private double price;

	public PricedCoupons(@NotBlank int customerId, @NotBlank double price) {
		
		this.customerId = customerId;
		this.price = price;
	}

	public PricedCoupons() {
		
		
	}
	
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}


	
	

}
