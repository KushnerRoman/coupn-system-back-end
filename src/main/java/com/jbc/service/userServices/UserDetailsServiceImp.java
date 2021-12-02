package com.jbc.service.userServices;

import org.apache.logging.log4j.LoggingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jbc.controller.AdminController;
import com.jbc.model.UserApp;
import com.jbc.repo.AdminRepo;
import com.jbc.repo.CompanyRepo;
import com.jbc.repo.CustomerRepo;

@Service
public class UserDetailsServiceImp implements UserDetailsService{
	
	@Autowired
	CustomerRepo customerRepo;

	@Autowired
	CompanyRepo companyRepo;
	
	@Autowired
	AdminRepo adminRepo;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(customerRepo.isExistByEmail(username)) {
			
			UserApp user= customerRepo.findByEmail(username).get();
			
			return UserPrinciple.build(user);
		}
		if (companyRepo.isExistByEmail(username)) {
			System.out.println(username);
			UserApp user=companyRepo.findByEmail(username).get();
			System.out.println(user.toString());
			return UserPrinciple.build(user);
		}
		if(adminRepo.existsByEmail(username)) {
			System.out.println(username);
				UserApp userAdmin=adminRepo.findByEmail(username).get();
				return UserPrinciple.build(userAdmin);
			
		}
		

			throw new LoggingException("User not Found with email : "+username);
		} 
		
		
	}


