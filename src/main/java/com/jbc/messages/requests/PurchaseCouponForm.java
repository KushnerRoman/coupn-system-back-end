package com.jbc.messages.requests;

import javax.validation.constraints.NotBlank;

public class PurchaseCouponForm {
	
	@NotBlank
	private int customerId;
	
	@NotBlank
	private int couponId;

	public PurchaseCouponForm(@NotBlank int customerId, @NotBlank int couponId) {

		this.customerId = customerId;
		this.couponId = couponId;
	}

	public PurchaseCouponForm() {
		super();
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getCouponId() {
		return couponId;
	}

	public void setCouponId(int couponId) {
		this.couponId = couponId;
	}



}
