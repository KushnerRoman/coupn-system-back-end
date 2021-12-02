package com.jbc.service.serviceIntfc;

import java.sql.SQLException;
import java.util.List;

import com.jbc.exceptions.GlobalException;
import com.jbc.exceptions.couponException.CouponNotExistException;
import com.jbc.exceptions.customersExceptions.CustomerExistAlreadyException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.exceptions.customersExceptions.CustomerPerchaseException;
import com.jbc.model.Category;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;

public interface CustomerServiceIntf {

	/**
	 * check if the coupon is valid (endDate bigger from today,amount must be bigger that 0)
	 * check if the customer purchased already this coupon.
	 *
	 * @param coupon the coupon
	 * @param customerId the customer id
	 * @return true, if successful
	 * @throws CustomerPerchaseException the customer perchase exception
	 */
	boolean purchaseValidation(Coupon coupon, int customerId) throws CustomerPerchaseException;


	/**
	 * Gets the customer coupons.
	 * @param Id the csutomerID
	 * @return the customer coupons
	 */
	List<Coupon> getCustomerCoupons(int customerId);


	/**
	 * Delete purchased coupon customer CouponCustomer List 
	 * and from DB	 *
	 * @param customerId the customer id
	 * @param couponId the coupon id
	 * @throws CustomerNotFoundException 
	 * @throws CouponException the coupon 
	 */
	void deletePurchasedCoupon(int customerId, int couponId) throws GlobalException, CustomerNotFoundException;
	


	/**
	 * Gets the coupons by max price.
	 *
	 * @param maxPrice the max price
	 * @return List coupons by price
	 * @throws CustomerNotFoundException 
	 */
	List<Coupon> getCouponsByPrice(double maxPrice, int customerId) throws CustomerNotFoundException;


	/**
	 * Gets the coupons by category.
	 *
	 * @param category the category
	 * @return the coupons by category
	 */
	List<Coupon> getCouponsByCategory(Category category, int customerId);


	/**
	 * Purchase coupon only if not exist already
	 *
	 * @param customerId the customer@Override
	 id
	 * @param couponId   the coupon id
	 * @throws CustomerPerchaseException the customer purchase exception
	 * @throws CouponNotExistException 
	 */
	void purchaseCouponן(int customerId, int couponId) throws CustomerPerchaseException, CouponNotExistException;


	/**
	 * Adds the customer, if not exist.
	 *
	 * @param customer the customer
	 * @see #isCustomerExist(String, String).
	 * @throws CustomerExistAlreadyException the customer exist already exception
	 */
	void addCustomer(Customer customer) throws CustomerExistAlreadyException;


	/**
	 * Checks if is customer exist by email and password
	 *
	 * @param email the email
	 * @param password the password
	 * @return true, if is customer exist
	 */
	boolean isCustomerExist(String email, String password);


	/**
	 * Delete customer if exist.
	 *
	 * @param customerId the customer id
	 * @throws CustomerNotFoundException the customer not found exception
	 * @throws GlobalException 
	 */
	void deletCustomer(int customerId) throws CustomerNotFoundException, GlobalException;


	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	List<Customer> getAllCustomers();



	/**
	 * Gets the one customer.
	 *
	 * @param customerId the customer id
	 * @return the one customer
	 * @throws CustomerNotFoundException the customer not found exception
	 */
	Customer getOneCustomer(String email) throws CustomerNotFoundException;


	/**
	 * Update customer.
	 *
	 * @param customer the customer
	 * @throws CustomerNotFoundException
	 * @throws SQLException 
	 */
	void updateCustomer(Customer customer) throws CustomerNotFoundException, SQLException;


	/**
	 * Gets thee one customer.
	 *
	 * @param email the customer id
	 * @return the one customer
	 * @throws CustomerNotFoundException the customer not found exception
	 */
	Customer getOneCustomerbyId(int id) throws CustomerNotFoundException;


	/**
	 * Purchase coupon only if not exist already
	 * and pass validation 
	 * @param customerId the customer id
	 * @param couponId   the coupon id
	 * @see #purchaseValidation(Coupon, int).
	 * @throws CustomerPerchaseException the customer purchase exception
	 * @throws CouponNotExistException 
	 */
	//void purchaseCoupon(int customerId, int couponId) throws CustomerPerchaseException, CouponNotExistException;


	

	
}
