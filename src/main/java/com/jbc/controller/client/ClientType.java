package com.jbc.controller.client;


public enum ClientType {
	
	
	
	ROLE_Administrator,
	ROLE_Customer,
	ROLE_Company;
	
	
	@Override
	public String toString() {
		switch (this) {
		case ROLE_Administrator:
			return "ROLE_Administrator";
		case ROLE_Customer:
			return "ROLE_Customer";
		case ROLE_Company:
			return "ROLE_Company";
		default:
			return super.toString();
		}
	}
}
