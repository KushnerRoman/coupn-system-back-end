package com.jbc.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbc.controller.client.LoggedIdToken;
import com.jbc.exceptions.GlobalException;
import com.jbc.exceptions.companiesExceptions.CompanyAlreadyExistException;
import com.jbc.exceptions.companiesExceptions.CompanyNotFoundException;
import com.jbc.exceptions.customersExceptions.CustomerExistAlreadyException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.repo.CompanyRepo;
import com.jbc.repo.CustomerRepo;
import com.jbc.service.CompanyService;
import com.jbc.service.CouponService;
import com.jbc.service.CustomerService;

import com.jbc.swaggerConfig.Annotations.DefaultApiResponses;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(value="\"User : Admin operation \"")
@RestController
@RequestMapping(value = "/apis/adminController")
@PreAuthorize("hasRole('ROLE_Administrator')")
@CrossOrigin(origins="*",maxAge=3600)
public class AdminController implements LoggedIdToken {
	
	
	
	/** The company service. */
	@Autowired
	CompanyService companyService;
	
	/** The customer service. */
	@Autowired
	CustomerService customerService;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	CompanyRepo companyRepo;
	@Autowired
	CouponService couponsServ;



	/**
	 * Adds the company.
	 *
	 * @param company the company
	 * @return the response entity
	 */
	
	
	@ApiOperation(value="Add company to DB")
	@DefaultApiResponses
	@PutMapping(value = "/addcompany" , produces="application/json")
	public ResponseEntity<?> addCompany(@RequestBody Company company) {
		try {
			companyService.addCompany(company);
			return new ResponseEntity<String>("Company Was added Successfully", HttpStatus.OK);
		} catch (CompanyAlreadyExistException  e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	
	/**
	 * Update company.
	 *
	 * @param company the company
	 * @return the response entity
	 */
	@ApiOperation(value="Update compnay in the DB ")
	@DefaultApiResponses
	@PostMapping(value = "/updateCompany")
	public ResponseEntity<?> updateCompany(@RequestBody Company company ) {
		try {
			
			companyService.updateCompany(company);
			return new ResponseEntity<String>("Company Was Updated Successfully", HttpStatus.OK);
		} catch ( CompanyNotFoundException  e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	
	/**
	 * Delete company.
	 *
	 * @param companyId the company id
	 * @return the response entity
	 */
	@ApiOperation(value="Deleting company from the DB and all the related coupons")
	@DefaultApiResponses
	@DeleteMapping(value = "/deletecompany")
	public ResponseEntity<?> deleteCompany(@RequestParam(name = "companyId") int companyId) {
		
		if(companyRepo.existsById(companyId)) {
			companyRepo.deleteById(companyId);
		}
		 return new
				  ResponseEntity<String>("Company was deleted successfully", HttpStatus.OK); 
		/*
		 * try {
		 * 
		 * companyService.deleteCompany(companyId); return new
		 * ResponseEntity<String>("Company was deleted successfully", HttpStatus.OK); }
		 * catch (CompanyNotFoundException | GlobalException | CustomerNotFoundException
		 * e) { return new ResponseEntity<String>(e.getLocalizedMessage(),
		 * HttpStatus.BAD_REQUEST); }
		 */
		 
	
	}
	
	
	/**
	 * Gets the all companies.
	 *
	 * @return the all companies
	 */
	@ApiOperation(value="Return list of all companies in the DB")
	@DefaultApiResponses
	@GetMapping(value = "/getallcompanies")
	public ResponseEntity<?> getAllCompanies() {

		if (!companyService.getAllCompanies().isEmpty()) {
			
			return new ResponseEntity<List<Company>>(companyService.getAllCompanies(), HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("No companies was found ",HttpStatus.BAD_REQUEST);
	}

	}
	
	/**
	 * Gets the one company.
	 *
	 * @param email the company id
	 * @return the one company
	 */
	@ApiOperation(value="get one specific company from the DB by the company ID")
	@DefaultApiResponses
	@GetMapping(value = "/getcompanybyemail")
	public ResponseEntity<?> getOneCompany(@RequestParam(name = "email") String email) {
		
		//WebMvcLinkBuilder linkToCustomers=linkTo(methodOn(this.getClass()).getAllCustomers());
		try {
			return new ResponseEntity<Company>(companyService.getOneCompany(email),HttpStatus.OK);
		} catch (CompanyNotFoundException e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * Adds the customer.
	 *
	 * @param customer the customer
	 * @return the response entity
	 */
	@ApiOperation(value="Add customer to DB if not exist already")
	@DefaultApiResponses
	@PutMapping(value = "/addcustomer")
	public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
		try {
			
			customerService.addCustomer(customer);
			return new ResponseEntity<String>("customer Was added Successfully", HttpStatus.OK);
		} catch (CustomerExistAlreadyException e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	
	/**
	 * Update custoemr.
	 *
	 * @param customer the customer
	 * @return the response entity
	 */	
	@ApiOperation(value="Update the cutomer in the DB")
	@DefaultApiResponses
	@PostMapping(value = "/updatecustomer")
	public ResponseEntity<?> updateCustoemr(@RequestBody Customer customer ) {
		try {
			Customer upCustomer=customerService.getOneCustomerbyId(customer.getId());
			upCustomer.setFirstName(customer.getFirstName());
			upCustomer.setLastName(customer.getLastName());
			upCustomer.setEmail(customer.getEmail());
			customerService.updateCustomer(upCustomer);
			return new ResponseEntity<String>("Customer was Updated successfully", HttpStatus.OK);
		} catch (CustomerNotFoundException | SQLException e) {
		
			return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	/**
	 * Delete customer.
	 *
	 * @param customerId the customer id
	 * @return the response entity
	 */
	@ApiOperation(value="Deleting specific customer from the DB by ID")
	@DefaultApiResponses
	@DeleteMapping(value = "/deletecustomer")
	public ResponseEntity<?> deleteCustomer(@RequestParam(name = "customerId") int customerId) {
		
			
			try {
				customerService.deletCustomer(customerId);
				return new ResponseEntity<String>("Customer was deleted successfully",HttpStatus.OK);
			} catch (CustomerNotFoundException | GlobalException e) {
				return new ResponseEntity<String>(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
			}
	}
	/**
	 * Gets the customer by id.
	 *
	 * @param id the id
	 * @return the customer by id
	 */
	@ApiOperation(value="Get specific customer from the DB by customer ID")
	@DefaultApiResponses
	@GetMapping(value = "/getcustomerbyemail")
	public ResponseEntity<?> getCustomerById(@RequestParam(name = "email") String email) {
		 
		Customer customer = customerRepo.findByEmail(email).get();
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);

	}
	
	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	@ApiOperation(value=" Get list of all customers from the DB")
	@DefaultApiResponses
	@GetMapping(value = "/getallcustomers")
	public ResponseEntity<?> getAllCustomers() {

		
		
	return new ResponseEntity<List<Customer>>(customerService.getAllCustomers(),HttpStatus.OK);

	}
	@ApiOperation(value=" Get list of all customers from the DB")
	@DefaultApiResponses
	@GetMapping(value = "/getallCoupons")
	public ResponseEntity<?> getAllCoupons() {

		
		
	return new ResponseEntity<List<Coupon>>(couponsServ.getAllCoupons(),HttpStatus.OK);

	}
	
	
}
