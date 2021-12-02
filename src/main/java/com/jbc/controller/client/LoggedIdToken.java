package com.jbc.controller.client;

import org.springframework.security.core.context.SecurityContextHolder;

import com.jbc.service.userServices.UserPrinciple;



public interface LoggedIdToken {
	
	
	public default long getUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int id = 0;
		if (principal instanceof UserPrinciple)
			id = ((UserPrinciple) principal).getId();
		return id;
	}

}
