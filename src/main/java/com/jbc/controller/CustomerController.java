package com.jbc.controller;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import com.jbc.messages.requests.PricedCoupons;
import com.jbc.messages.requests.PurchaseCouponForm;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jbc.controller.client.ClientType;
import com.jbc.controller.client.LoggedIdToken;
import com.jbc.exceptions.couponException.CouponExistException;
import com.jbc.exceptions.couponException.CouponNotExistException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.exceptions.customersExceptions.CustomerPerchaseException;
import com.jbc.model.Category;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.service.CouponService;
import com.jbc.service.CustomerService;
import com.jbc.swaggerConfig.Annotations.DefaultApiResponses;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/apis/customercontroller")
@CrossOrigin(origins="*",maxAge=3600)
public class CustomerController implements LoggedIdToken{

	@Autowired
	CustomerService service;
	
	@Autowired
	CouponService couponService;
	

	@ApiOperation(value="purchase coupon and add the coupon to customer couponsList")
	@DefaultApiResponses
	@PutMapping("/purchaseCoupon")
	@PreAuthorize("hasRole('ROLE_Customer')")
	public ResponseEntity <?> purchaseCoupon(@RequestBody Coupon coupon){
		
		Long idL=getUserId();
		int id=idL.intValue();

		try {
			couponService.addCouponPurchase(id, coupon.getId());
			return new ResponseEntity<String>("Coupopn was Purchased Successfully",HttpStatus.OK);
		} catch (SQLException|CustomerNotFoundException | CustomerPerchaseException | CouponNotExistException | CouponExistException e) {
			
			return new ResponseEntity<String>(e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="get the coupons of the logged customer")
	@DefaultApiResponses
	@GetMapping(value = "/getcustomercoupons")
	public ResponseEntity<?> getCustomerCoupons(@RequestParam(name="email") String email) {
		Long idL=getUserId();
		int id=idL.intValue();
		System.out.println(id +"-----------------");
		try {
			Customer customer = service.getOneCustomerbyId(id);
			return new ResponseEntity<List<Coupon>>(customer.getCoupons(), HttpStatus.OK);
		} catch (CustomerNotFoundException e) {

			return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	
	@ApiOperation(value="get cusdtomer coupons by specific category ")
	@DefaultApiResponses
	@GetMapping(value = "/getCustomerCouponsByCategory")
	public ResponseEntity<?> getCustomerCouponsByCategory(@RequestParam(name = "category") Category category) {
		Long idL=getUserId();
		int id=idL.intValue();
		System.out.println(service.getCouponsByCategory(category, id).toString());
			return new ResponseEntity<List<Coupon>>(service.getCouponsByCategory(category, id)
					, HttpStatus.OK);

	}
	
	@ApiOperation(value="get customer coupons by specific category ")
	@DefaultApiResponses
	@PostMapping(value = "/getCustomerCouponsByMaxPrice")
	public ResponseEntity<?> getCustomerCouponsByMaxPrice(@RequestBody PricedCoupons reqPricedSearch)  {
		
		Long idL=getUserId();
		int id=idL.intValue();
		
		double maxPrice=reqPricedSearch.getPrice();
				
				try {
					return new ResponseEntity<List<Coupon>>(service.getCouponsByPrice(maxPrice, id)
							, HttpStatus.OK);
				} catch (CustomerNotFoundException e) {
					return new ResponseEntity<String>("Error"
							, HttpStatus.OK);
				}
			
				
		

	}
	
	
	@ApiOperation(value="get info of the logged Customer ")
	@DefaultApiResponses
	@GetMapping(value = "/getcustomerinfo")
	public ResponseEntity<?> getCustomerInfo(@RequestParam(name="email") String email) {
		System.out.println(email);
		Long idL=getUserId();
		int id=idL.intValue();
		
		
			try {
				Customer custo=service.getOneCustomerbyId(id);
				//Customer customerr=service.getOneCustomer(email);
				
				
				return new ResponseEntity<Customer>(custo
						, HttpStatus.OK);
			} catch (CustomerNotFoundException e) {
				return new ResponseEntity<String>(e.getLocalizedMessage()
						, HttpStatus.BAD_REQUEST);
			}

	}
	
	

}
