package com.jbc.service;


import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbc.exceptions.GlobalException;
import com.jbc.exceptions.companiesExceptions.CompanyNotFoundException;
import com.jbc.exceptions.couponException.CouponException;
import com.jbc.exceptions.couponException.CouponExistException;
import com.jbc.exceptions.couponException.CouponNotExistException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.exceptions.customersExceptions.CustomerPerchaseException;
import com.jbc.model.Company;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.repo.CompanyRepo;
import com.jbc.repo.CouponRepo;
import com.jbc.repo.CustomerRepo;
import com.jbc.service.serviceIntfc.CouponServiceIntfc;

@Service
public class CouponService implements CouponServiceIntfc {
	
	@Autowired
	CouponRepo repo;
	@Autowired
	CompanyRepo companyRepo;
	@Autowired
	CustomerRepo customerRepo;
	@Autowired
	CustomerService customerService;
	
	/**
	 * add the coupon to the DataBase .
	 *
	 * @param coupon the coupon to add
	 * @throws CouponExistException 
	 * @throws CompanyNotFoundException 
	 * @throws CouponException 
	 */
	@Override
	public void addCoupon(Coupon coupon) throws CouponExistException, CompanyNotFoundException  {
		
		if (companyRepo.findById(coupon.getCompanyId()).isPresent()) {
			
			
			
	//		Company company=companyRepo.findById(coupon.getCompanyId()).get();
	//		List<Coupon> coupons=company.getCouponsSet().stream().filter(c->c.getTitle().equals(coupon.getTitle())).collect(Collectors.toList());
	//		if(coupons.isEmpty()) {
	//			repo.saveAndFlush(coupon);
	//		}else {
	//			throw new CouponExistException("Coupon already exist");
	//		}
			
			
			  if (!repo.existsByTitleAndCompany(coupon.getTitle(),coupon.getCompanyId())) {
				  repo.saveAndFlush(coupon); }
			  	  else { throw new
				  CouponExistException("Coupon already exist"); }
			 
				}else {
				   throw new CompanyNotFoundException("Company by this id do not exist !");
				}
	
	}
	
	/**
	 * Update the coupon in the DataBase and commit.
	 *
	 * @param coupon the coupon to update
	 * @throws CouponNotExistException 
	 */

	@Override
	public void updateCoupon(Coupon coupon) throws CouponNotExistException {
		
		System.out.println(coupon.toString());
		
		if(repo.findById(coupon.getId()).isPresent()) {
			Coupon couponToUpdate=repo.findById(coupon.getId()).get();
			couponToUpdate.setAmount(coupon.getAmount());
			couponToUpdate.setEndDate(coupon.getEndDate());
			couponToUpdate.setPrice(coupon.getPrice());
			couponToUpdate.setImage(coupon.getImage());
			repo.save(couponToUpdate);
		}else{
			throw new CouponNotExistException("Coupon by id: "+coupon.getId()+", was not found !");
		}
	
		
	}
	
	
	/**
	 * Delete the coupon from the DataBase and from all customer's couponList
	 *
	 * @param couponID the coupon ID to delete
	 * @throws CouponNotExistException 
	 * @throws GlobalException 
	 * @throws CustomerNotFoundException 
	 * @throws SQLException 
	 */

	@Override
	public void deleteCoupon(int couponId) throws CouponNotExistException, GlobalException, CustomerNotFoundException, SQLException {
		Coupon coupon=repo.findById(couponId).orElseThrow(()->new CouponNotExistException("Coupon not exist"));
		List<Customer> customers=customerRepo.findAllByCouponId(couponId);		
		for (Customer customer : customers) {
			deleteCouponPurchase(customer.getId(), couponId);
			customer.removeCoupon(coupon);
			customerService.updateCustomer(customer);
			
		}
		repo.delete(coupon);
		
		
	}
	
	/**
	 * Delete coupon from Customer's Coupons list and commit/ .
	 *
	 * @param customerId the customer id
	 * @param couponId the coupon id
	 * @throws GlobalException 
	 */

	@Override
	public void deleteCouponPurchase(int customerId, int couponId) throws GlobalException {
		
		if(customerRepo.findById(customerId).isPresent() && repo.findById(couponId).isPresent()) {
			Customer customer=customerRepo.findById(customerId).get();
			Coupon coupon=repo.findById(couponId).get();
			customer.removeCoupon(coupon);
			customerRepo.save(customer);
		}else {
			throw new GlobalException("Unabale to find Coupon or Customer by those id's");
		}
	
		
	}
	
	/**
	 * Gets the all coupons.
	 *
	 * @return the all coupons
	 */
	@Override
	public List<Coupon> getAllCoupons(){
		return repo.findAll();
	}
	
	
	/**
	 * Gets the one coupon.
	 *
	 * @param couponId the coupon id
	 * @return the one coupon
	 * @throws CouponNotExistException the coupon not exist exception
	 */

	@Override
	public Coupon getOneCoupon(int couponId) throws CouponNotExistException {
		return repo.findById(couponId).orElseThrow(()->new CouponNotExistException("Coupon was not found"));
	}
	
	
	/**
	 * Adds the coupon purchase.
	 *
	 * @param customerId the customer id
	 * @param couponId the coupon id
	 * @see #customerService
	 * @throws CusteomerNotFoundException the customer not found exception
	 * @throws CustomerPerchaseException the customer perchase exception
	 * @throws CouponNotExistException 
	 * @throws CouponExistException 
	 */
	@Override
	public void addCouponPurchase(int customerId,int couponId) throws CustomerNotFoundException, CustomerPerchaseException, CouponNotExistException, CouponExistException,SQLException {
		if(repo.findById(couponId).isPresent()) {
			Coupon couponValid = repo.findById(couponId).get();
			if(customerService.purchaseValidation(couponValid, customerId)) {
				Customer customer = customerRepo.findById(customerId).get();
				customer.addCoupon(couponValid);
				customerService.updateCustomer(customer);
				couponValid.setAmount(couponValid.getAmount()-1);
				updateCoupon(couponValid);
			}else {
				throw new CouponExistException("Customer already have purchased this coupon already");
				}
		}else if (!repo.findById(couponId).isPresent()) {
			throw new CustomerNotFoundException("Custoemr by id: "+customerId+", Coupon id "+couponId+ ", was not found");
		} 
			
		
		
		
	}
	
	/**
	 * Delete list coupons.
	 *
	 * @param list the list
	 */

	@Override
	public void deleteListCoupons(List<Coupon> list) {
		repo.deleteAll(list);
	}

	@Override
	public List<Coupon> getCompanyCoupons(String email) throws CouponNotExistException {
		
		
		
		Company comapny=companyRepo.findByEmail(email).get();
		List<Coupon> coupons=new ArrayList<Coupon>();
		coupons=comapny.getCoupons();
		
		return coupons;
		
	
		
		
			
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
