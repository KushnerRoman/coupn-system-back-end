package com.jbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;


import com.jbc.test.InitTest;


@SpringBootApplication
@EnableConfigurationProperties
public class CouponJavaApplication {
	

	//@Autowired
	InitTest test;

	public static void main(String[] args) {
		
		
		 
		SpringApplication.run(CouponJavaApplication.class, args);
		

		
	
	
	}

}
