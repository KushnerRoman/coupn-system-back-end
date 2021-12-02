package com.jbc.test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jbc.controller.client.ClientType;
import com.jbc.model.Admin;
import com.jbc.model.Category;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.model.Role;
import com.jbc.repo.AdminRepo;
import com.jbc.repo.CompanyRepo;
import com.jbc.repo.CouponRepo;
import com.jbc.repo.CustomerRepo;
import com.jbc.repo.RoleRepo;

@Component
public class InitTest {

	
	public List<Customer> customerList; 
	  public List<Company> companiesList;
	  public List<Coupon> couponsList;
	  
	
	  
	  @Autowired
	  RoleRepo roleRepo;
	  
	  @Autowired
	  CouponRepo couponRepo;
	 
	  
	  @Autowired
	  AdminRepo adminRepo;
	  	
	  @Autowired
	  CompanyRepo comapnyRepo;
	  
	  @Autowired
	  private PasswordEncoder encoder;
	  
	  

	//@PostConstruct
	public void init() {
		
		
		if(adminRepo.count()==0) {
			  roleRepo.saveAllAndFlush(allRoles());

			  Set<Role> roles = new HashSet<>(); 
				Role userRole = roleRepo.findByType(ClientType.ROLE_Administrator)
							.orElseThrow(() -> new RuntimeException("Error : Role was not found."));
				roles.add(userRole);
				Admin admin=new Admin( "admin@gmail.com", encoder.encode("admin"));
			
			 adminRepo.saveAndFlush(admin);
			 comapnyRepo.saveAll(allCompanies());
			 
			 couponRepo.saveAll(allCoupons());
		}
		
		
		
		
	  }
	  
	 
	  private Set<Role> allRoles() {
		Set<Role> rlList=new HashSet<Role>();
		rlList.add(new Role(ClientType.ROLE_Administrator));
		rlList.add(new Role(ClientType.ROLE_Company));
		rlList.add(new Role(ClientType.ROLE_Customer));
		return rlList;
		
	
	}


	private List<Customer> allCustomers(){
	  
	  Customer romanCustomer=new  Customer("Roman","Kushner","Kushner@gmail.com",encoder.encode("123123123"));
	  Customer anaCustomer=new Customer("ana","pocka","ana@gmail.com", encoder.encode("123123123"));
	  Customer bobCustomer=new Customer("bob","bob","sweet@gmail.com", encoder.encode("123123123"));
	  Customer nosCustomer=new Customer("Nos","Nos","nos@gmail.com", encoder.encode("123123123"));
	  Customer yosiCustomer=new Customer("Yosi","Yosi","Yosi@gmail.com", encoder.encode("123123123"));
	  
	  List<Customer> customerList=new ArrayList<Customer>();
	  customerList.add(yosiCustomer);
	  customerList.add(nosCustomer);
	  customerList.add(bobCustomer); 
	  customerList.add(anaCustomer);
	  customerList.add(romanCustomer);
	  
	  return customerList;
	  
	  
	  }
	  
	  
	  private List<Company> allCompanies(){
	  
	  Company bmw=new Company(10,"BMW","bmw@gmail.com",encoder.encode("123123123")); Company
	  bigElectric=new Company(20,"Big Electra","Big@GMAIL.COM","big123"); Company
	  philips=new Company(30,"philips","philips@GMAIL.COM","philips100"); Company
	  jbc=new Company(40,"jbc","jbc@GMAIL.COM","jbc000");
	  
	  List<Company> companiesList=new ArrayList<Company>(); companiesList.add(bmw);
	  companiesList.add(bigElectric); companiesList.add(philips);
	  companiesList.add(jbc);
	  
	  return companiesList; }
	  
	  
	  @Bean 
	  private List<Coupon> allCoupons() {
	  
	  Coupon coupon1=new Coupon(10,7,Category.CAR,
	  "20% Discount for new car ","discription" ,"image" ,
	  Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(2)),
	  25);
	  
	  Coupon coupon2=new Coupon(20,3, Category.FOOD,
	  "50% Discount for diet dog food","discription" ,"DOG" ,
	  Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(10)),
	  30);
	  
	  Coupon coupon3=new Coupon(30,2, Category.MOBILE,
	  "45% Discount for iPhone ","discription" ,"iPhone" ,
	  Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(1)),
	  15);
	 
	 Coupon coupon4=new Coupon(40,1,Category.ELECTRICITY,
	  "20% Discount for new SmartTV","discription" ,"image" ,
	  Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(2)),
	  50);
	  
	  Coupon coupon5=new Coupon(10,2, Category.FOOD,
	  "50% Discount for 1 slice of Pizza","discription" ,"Pizza" ,
	  Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(10)),
	  88);
	  
	  Coupon coupon6=new Coupon(10,10, Category.HATS,
	  "45% Discount for HATS","discription" ,"HATS" ,
	  Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now().plusDays(5)), 8);
	  
	  ArrayList<Coupon> couponsList=new ArrayList<Coupon>();
	  couponsList.add(coupon1); couponsList.add(coupon2); couponsList.add(coupon3);
	  couponsList.add(coupon4); couponsList.add(coupon5); couponsList.add(coupon6);
	  
	  
	  return couponsList; }
	  
	  public List<Customer> getCustomerList() { return customerList; }
	  
	  public void setCustomerList() { this.customerList = allCustomers(); }
	  
	  public List<Company> getCompaniesList() { return companiesList; }
	  
	  public void setCompaniesList() {
	  
	  this.companiesList = allCompanies(); }
	  
	  public void setCouponList() { this.couponsList = allCoupons(); }
	  
	  public List<Coupon> getCouponsList() { return couponsList; }
	  
	
	
	

}


