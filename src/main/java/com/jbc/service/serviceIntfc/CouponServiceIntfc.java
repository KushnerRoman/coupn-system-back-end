package com.jbc.service.serviceIntfc;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

import com.jbc.exceptions.GlobalException;
import com.jbc.exceptions.companiesExceptions.CompanyNotFoundException;
import com.jbc.exceptions.couponException.CouponExistException;
import com.jbc.exceptions.couponException.CouponNotExistException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.exceptions.customersExceptions.CustomerPerchaseException;
import com.jbc.model.Company;
import com.jbc.model.Coupon;

public interface CouponServiceIntfc {

	/**
	 * add the coupon to the DataBase .
	 *
	 * @param coupon the coupon to add
	 * @throws CouponExistException 
	 * @throws CompanyNotFoundException 
	 * @throws CouponException 
	 */
	void addCoupon(Coupon coupon) throws CouponExistException, CompanyNotFoundException;

	/**
	 * Update the coupon in the DataBase and commit.
	 *
	 * @param coupon the coupon to update
	 * @throws CouponNotExistException 
	 */
	void updateCoupon(Coupon coupon) throws CouponNotExistException;

	/**
	 * Delete the coupon from the DataBase and from all customer's couponList
	 *
	 * @param couponID the coupon ID to delete
	 * @throws CouponNotExistException 
	 * @throws GlobalException 
	 * @throws CustomerNotFoundException 
	 * @throws SQLException 
	 */
	void deleteCoupon(int couponID) throws CouponNotExistException, GlobalException, CustomerNotFoundException, SQLException;

	/**
	 * Delete coupon from Customer's Coupons list and commit/ .
	 *
	 * @param customerId the customer id
	 * @param couponId the coupon id
	 * @throws GlobalException 
	 */
	void deleteCouponPurchase(int customerId, int couponId) throws GlobalException;

	/**
	 * Gets the all coupons.
	 *
	 * @return the all coupons
	 */
	List<Coupon> getAllCoupons();

	/**
	 * Gets the one coupon.
	 *
	 * @param couponId the coupon id
	 * @return the one coupon
	 * @throws CouponNotExistException the coupon not exist exception
	 */
	Coupon getOneCoupon(int couponId) throws CouponNotExistException;

	/**
	 * Adds the coupon purchase.
	 *
	 * @param customerId the customer id
	 * @param couponId the coupon id
	 * @see #customerService
	 * @throws CustomerNotFoundException the customer not found exception
	 * @throws CustomerPerchaseException the customer perchase exception
	 * @throws CouponNotExistException 
	 * @throws CouponExistException 
	 * @throws SQLIntegrityConstraintViolationException 
	 * @throws SQLException 
	 */
	void addCouponPurchase(int customerId, int couponId) throws CustomerNotFoundException, CustomerPerchaseException, CouponNotExistException, CouponExistException, SQLIntegrityConstraintViolationException, SQLException;

	/**
	 * Delete list coupons.
	 *
	 * @param list the list
	 */
	void deleteListCoupons(List<Coupon> list);

	
	
	 List<Coupon> getCompanyCoupons(String email) throws CouponNotExistException;

}
