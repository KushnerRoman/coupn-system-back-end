package com.jbc.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jgit.gitrepo.RepoCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.jbc.exceptions.companiesExceptions.CompanyNotFoundException;
import com.jbc.exceptions.couponException.CouponExistException;
import com.jbc.exceptions.couponException.CouponNotExistException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.model.Category;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.service.CompanyService;
import com.jbc.service.CouponService;
import com.jbc.swaggerConfig.Annotations.DefaultApiResponses;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;




@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/apis/companycontroller")
@Api(value="\"User : Company operation \"")

public class CompanyController implements LoggedIdToken {
	


	
	@Autowired
	CompanyService service;
	@Autowired
	CouponService couponService;



	@ApiOperation(value="Add coupon to DB")
	@DefaultApiResponses
	@PutMapping(value = "/addCoupon")
	@PreAuthorize("hasAnyRole('ROLE_Administrator','ROLE_Company')")
	public ResponseEntity<?> addCoupon(@RequestBody Coupon coupon) {
		try {
			Long idL=getUserId();
			int id=idL.intValue();
			coupon.setCompanyId(id);
			couponService.addCoupon(coupon);
			return new ResponseEntity<String>("Coupon Was added Successfully", HttpStatus.OK);
		} catch (  CompanyNotFoundException | CouponExistException  e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}

	}
	@ApiOperation(value="update coupon of specific company from DB")
	@DefaultApiResponses
	@PostMapping(value = "/updateCoupon")
	@PreAuthorize("hasAnyRole('ROLE_Administrator','ROLE_Company')")
	
	public ResponseEntity<?> updateCoupon(@RequestBody Coupon coupon) {
		try {
			Long idL=getUserId();
			int id=idL.intValue();
			coupon.setCompanyId(id);
			couponService.updateCoupon(coupon);
			return new ResponseEntity<String>("Coupon Was Updated Successfully", HttpStatus.OK);
		} catch (CouponNotExistException e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
		

	}
	// needed to update for delete coupon only the coupon of the logged company!
	@ApiOperation(value="delete coupon and the coupon of all customers")
	@DefaultApiResponses
	@DeleteMapping(value = "/deleteCoupon")
	@PreAuthorize("hasAnyRole('ROLE_Administrator','ROLE_Company')")
	public ResponseEntity<?> deleteCoupon(@RequestParam("couponId") int couponId) {
		try {
			couponService.deleteCoupon(couponId);
			return new ResponseEntity<String>("Coupon was deleted successfully", HttpStatus.OK);
		} catch (CouponNotExistException | GlobalException | CustomerNotFoundException | SQLException e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value="get all coupons of specific company from DB")
	@DefaultApiResponses
	@GetMapping(value = "/getallcoupons")

	public ResponseEntity<?> getAllCoupons() {

		if (!couponService.getAllCoupons().isEmpty()){
			
			return new ResponseEntity<List<Coupon>>(couponService.getAllCoupons(), HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("No coupons was found ",HttpStatus.BAD_REQUEST);
	}

	}
	//edit to get all coupon by category from specific company 
	@ApiOperation(value="get all coupon fropm specific category of a company from DB")
	@DefaultApiResponses
	@GetMapping(value = "/getAllCouponsFromCategory/{category}/{companyId}")
	
	public ResponseEntity<?> getAllCouponsFromCategory(@RequestParam("category") Category category ,@RequestParam("companyId")int companyId) {

		if (!couponService.getAllCoupons().isEmpty()){
			
			return new ResponseEntity<List<Coupon>>(couponService.getAllCoupons().stream()
					.filter(c->c.getCategoryString().equalsIgnoreCase(category.name())).collect(Collectors.toList())
					, HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("No Coupon was found ",HttpStatus.BAD_REQUEST);
	}

	}
	
	@GetMapping(value = "/getAllCouponsFromCategory/{maxPrice}")
	@ApiOperation(value="gett al coupon of specific company by maximum price")
	@DefaultApiResponses
	public ResponseEntity<?> getAllCouponsByMaxPrice(@RequestParam(name = "maxPrice") double maxPrice) {

		if (!couponService.getAllCoupons().isEmpty()){
			//List<Coupon> pricedCoupons=couponService.getAllCoupons().stream().filter(c->c.getPrice()<=maxPrice).collect(Collectors.toList());
			return new ResponseEntity<List<Coupon>>(couponService.getAllCoupons().stream()
					.filter(c->c.getPrice()<=maxPrice).collect(Collectors.toList())
					, HttpStatus.OK);
	}else {
		return new ResponseEntity<String>("No Coupon was found under: "+maxPrice+" NIS"
	,HttpStatus.BAD_REQUEST);
	}


	}
	
	
	@GetMapping(value = "/getAllCouponsofCompany")
	@ApiOperation(value="gett all coupon of specific company")
	@DefaultApiResponses
	public ResponseEntity<?> getAllCouponsOfCompany(@RequestParam(name = "email") String email) {
		
		try {
			return new ResponseEntity<List<Coupon>>(couponService.getCompanyCoupons(email),HttpStatus.OK) ;
		} catch (CouponNotExistException e1) {
			return new ResponseEntity<String>("No Coupon was found for: "+email+" NIS"
					,HttpStatus.BAD_REQUEST);
		}
		

	}
	
	
	
	
	
	
	
	
	
	@ApiOperation(value="get info of logged company")
	@DefaultApiResponses
	@GetMapping(value = "/getCompanyInfo")
	public ResponseEntity<?> getCompanyInfo(@RequestParam(name = "email") String email) {
		try {
			return new ResponseEntity<Company>(service.getOneCompany(email),HttpStatus.OK);
		} catch (CompanyNotFoundException e) {
			return new ResponseEntity<String>(e.getLocalizedMessage(),HttpStatus.NOT_FOUND);
		}
		
		
		
	}
	@ApiOperation(value="get all companies")
	@DefaultApiResponses
	@GetMapping(value = "/getAll")
	public ResponseEntity<?> getAllCompanies(){
		return new ResponseEntity<List<Company>>(service.getAllCompanies(),HttpStatus.OK);
	}
	
	
	@ApiOperation(value="get all Catergories")
	@DefaultApiResponses
	@GetMapping(value = "/allCategories")
	public List<String> getAllCategoreis(){
		System.out.println();
		 
				List<String> enumNames = Stream.of(Category.values())
                .map(Category::name)
                .collect(Collectors.toList());
				
				return enumNames;
	}
	
	
	
	
	
}
