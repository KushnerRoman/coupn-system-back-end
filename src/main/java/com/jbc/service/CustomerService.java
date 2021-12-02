
package com.jbc.service;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jbc.exceptions.GlobalException;
import com.jbc.exceptions.couponException.CouponNotExistException;
import com.jbc.exceptions.customersExceptions.CustomerExistAlreadyException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.exceptions.customersExceptions.CustomerPerchaseException;
import com.jbc.model.Category;
import com.jbc.model.Coupon;
import com.jbc.model.Customer;
import com.jbc.repo.CouponRepo;
import com.jbc.repo.CustomerRepo;
import com.jbc.service.serviceIntfc.CustomerServiceIntf;

@Service
public class CustomerService implements CustomerServiceIntf {

	@Autowired
	CustomerRepo repo;
	@Autowired
	CouponRepo couponRepo;

	public CustomerService() {

	}

	/**
	 * Checks if is customer exist by email and password
	 *
	 * @param email    the email
	 * @param password the password
	 * @return true, if is customer exist
	 */

	@Override
	public boolean isCustomerExist(String email, String password) {
		return repo.isExist(email, password);
	}

	/**
	 * Adds the customer, if not exist.
	 *
	 * @param customer the customer
	 * @see #isCustomerExist(String, String).
	 * @throws CustomerExistAlreadyException the customer exist already exception
	 */

	@Override
	public void addCustomer(Customer customer) throws CustomerExistAlreadyException {
		if (!repo.isExistByEmail(customer.getEmail())) {
			repo.save(customer);

		} else {
			throw new CustomerExistAlreadyException("Customer by this mail already exist ");
		}

	}

	/**
	 * Update customer.
	 *
	 * @param customer the customer
	 * @throws CustomerNotFoundException
	 */

	@Override
	public void updateCustomer(Customer customer) throws CustomerNotFoundException,SQLException {
		if (repo.existsById(customer.getId())) {
			Customer updateToCustomer = repo.findById(customer.getId()).get();
			updateToCustomer.setEmail(customer.getEmail());
			updateToCustomer.setFirstName(customer.getFirstName());
			updateToCustomer.setLastName(customer.getLastName());
			updateToCustomer.setPassword(customer.getPassword());
			if(customer.getCoupons()!=null) {
				updateToCustomer.getCoupons().addAll(customer.getCoupons());
			}
			updateToCustomer.setCoupons(customer.getCoupons());
			repo.saveAndFlush(updateToCustomer);
			//repo.save(updateToCustomer);
		}else {
			throw new CustomerNotFoundException("Customer by id: " + customer.getId() + ", was not found");
		}
		
	}

	/**
	 * Gets the all customers.
	 *
	 * @return the all customers
	 */
	@Override
	public List<Customer> getAllCustomers() {
		return repo.findAll();
	}

	/**
	 * Gets the one customer.
	 *
	 * @param email the customer id
	 * @return the one customer
	 * @throws CustomerNotFoundException the customer not found exception
	 */
	@Override
	public Customer getOneCustomer(String email) throws CustomerNotFoundException {
		if (repo.findByEmail(email).isPresent()) {
			return repo.findByEmail(email).get();
		} else {
			throw new CustomerNotFoundException("Customer by id: " + email + ", was not found");
		}
	}
	/**
	 * Gets the one customer.
	 *
	 * @param edmail the customer id
	 * @return the one customer
	 * @throws CustomerNotFoundException the customer not found exception
	 */
	@Override
	public Customer getOneCustomerbyId(int id) throws CustomerNotFoundException {
		if (repo.findById(id).isPresent()) {
			return repo.findById(id).get();
		} else {
			throw new CustomerNotFoundException("Customer by id: " + id + ", was not found");
		}
	}

	/**
	 * Purchase coupon only if not exist already and pass validation
	 * 
	 * @param customerId the customer id
	 * @param couponId   the coupon id
	 * @see #purchaseValidation(Coupon, int).
	 * @throws CustomerPerchaseException the customer purchase exception
	 * @throws CouponNotExistException
	 */

	@Deprecated
	@Override
	public void purchaseCouponן(int customerId, int couponId)
			throws CustomerPerchaseException, CouponNotExistException {
		CouponService couponService = new CouponService();
		if (repo.findById(customerId).isPresent() && couponRepo.findById(couponId).isPresent()) {

			Customer customer = repo.findById(customerId).get();
			Coupon couponValid = couponRepo.findById(couponId).get();

			if (purchaseValidation(couponValid, customerId)) {
				customer.addCoupon(couponValid);
				repo.save(customer);
				couponValid.setAmount(couponValid.getAmount() - 1);
				couponService.updateCoupon(couponValid);
			}

		}
	}

	/**
	 * Delete customer if exist.
	 *
	 * @param customerId the customer id
	 * @throws CustomerNotFoundException the customer not found exception
	 * @throws GlobalException
	 */
	@Override
	public void deletCustomer(int customerId) throws CustomerNotFoundException, GlobalException {
		if (repo.findById(customerId).isPresent()) {
			Customer customer = repo.findById(customerId).get();
			if (!customer.getCoupons().isEmpty()) {
				List<Coupon> couponsList = customer.getCoupons();
				for (Coupon coupon : couponsList) {
					deletePurchasedCoupon(customerId, coupon.getId());
				}
			
			}
			repo.deleteById(customerId);
			repo.flush();

		} else {
			throw new CustomerNotFoundException("Customer was Not found by this id: " + customerId + ".");
		}
	}

	/**
	 * Delete purchased coupon customer CouponCustomer List and from DB *
	 * 
	 * @param customerId the customer id
	 * @param couponId   the coupon id
	 * @throws CustomerNotFoundException
	 * @throws CouponException the coupon exception
	 */

	@Override
	public void deletePurchasedCoupon(int customerId, int couponId)
			throws GlobalException, CustomerNotFoundException {

		if (repo.findById(customerId).isPresent() && couponRepo.findById(couponId).isPresent()) {
			Customer customer = repo.findById(customerId).get();
			Coupon coupon = couponRepo.findById(couponId).get();
			customer.removeCoupon(coupon);
			try {
				updateCustomer(customer);
			} catch (CustomerNotFoundException |SQLException e) {
				throw new GlobalException(e.getLocalizedMessage());
			}

		} else {
			throw new GlobalException("Customer or Coupon  does not exist ");
		}

	}

	/**
	 * Gets the customer coupons.
	 * 
	 * @param Id the csutomerID
	 * @return the customer coupons
	 */

	@Override
	public List<Coupon> getCustomerCoupons(int customerId) {
		Customer customer = repo.findById(customerId).get();

		return customer.getCoupons();

	}

	/**
	 * Gets the coupons by category.
	 *
	 * @param category the category
	 * @return the coupons by category
	 */

	@Override
	public List<Coupon> getCouponsByCategory(Category category, int customerId) {

		return getCustomerCoupons(customerId).stream().filter(c -> c.getCategory().equals(category))
				.collect(Collectors.toList());

	}

	/**
	 * Gets the coupons by max price.
	 *
	 * @param maxPrice the max price
	 * @return List coupons by price
	 * @throws CustomerNotFoundException 
	 */
	@Override
	public List<Coupon> getCouponsByPrice(double maxPrice, int customerId) throws CustomerNotFoundException {
		
		
		List<Coupon> filtred=getCustomerCoupons(customerId).stream().filter(c -> c.getPrice() <= maxPrice)
		.collect(Collectors.toList());
		
			if(filtred.isEmpty()) {
				throw  new CustomerNotFoundException("Coupons was not found");
				
			}else {
				return filtred;
			}
				
				
		
	

	}

	/**
	 * check if the coupon is valid (endDate bigger from today,amount must be bigger
	 * that 0) check if the customer purchased already this coupon.
	 *
	 * @param coupon     the coupon
	 * @param customerId the customer id
	 * @return true, if successful
	 * @throws CustomerPerchaseException the customer perchase exception
	 */

	@Override
	public boolean purchaseValidation(Coupon coupon, int customerId) throws CustomerPerchaseException {
		if (repo.findById(customerId).isPresent()) {
			Customer customer = repo.findById(customerId).get();
			if (!customer.getCoupons().stream().noneMatch(c -> c.getId() == coupon.getId())) {
				throw new CustomerPerchaseException("Customer is already purchase this coupon ");
			} else if (!(coupon.getAmount() > 0)) {
				System.out.println("No coupons left (amount = 0)");
				throw new CustomerPerchaseException("Sorry ! no coupons left ");
			} else if (!coupon.getEndDate().after(Date.valueOf(LocalDate.now()))) {
				System.out.println("Coupon end date is expired !");
				throw new CustomerPerchaseException("Coupon end date is expired !");
			} else {
				return true;
			}
		}
		return false;

	}

	


}


