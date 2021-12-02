package com.jbc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.jbc.model.Customer;
@Repository
@EnableJpaRepositories

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
	
	
	
	@Query("SELECT count(c)>0 from Customer c where email=?1 and password=?2")
	public boolean isExist(String email,String password);
	
	@Query("SELECT count(c)>0 from Customer c where email=?1")
	public boolean isExistByEmail(String email);

	@Query("SELECT customer FROM Customer customer inner join customer.coupons coupon where coupon.id=?1")
	public List<Customer> findAllByCouponId(int couponId);
	
	@Query("SELECT customer.id FROM Customer customer inner join customer.coupons coupon where coupon.id=?1  ") 
	public List<Integer> customerIdWhithCoupon(int couponId);
	
	
	@Query("SELECT customer FROM Customer customer inner join customer.coupons coupon where coupon.company=?1")
	public List<Customer> findAllCustomerByCouponCompanyId(int companyId);
	
	
	public Optional <Customer> findByEmail(String email);
	
	
	//@Query("SELECT count(c)>0 from Customer customer where id=?1 inner join customer.coupons coupon where coupon.id=?2")
	//public boolean isCouponExistForCustomer(Long customerId,Long couponId);
	
	

}
