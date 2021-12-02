package com.jbc.dailyThread;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.jbc.exceptions.GlobalException;
import com.jbc.exceptions.couponException.CouponNotExistException;
import com.jbc.exceptions.customersExceptions.CustomerNotFoundException;
import com.jbc.model.Coupon;
import com.jbc.service.CouponService;



public class MidnightJob implements Runnable {

	@Autowired
	private CouponService couponService;
	private List<Coupon> expiredCoupons=new ArrayList<Coupon>();


	public MidnightJob() {
		
	}

	public MidnightJob(CouponService couponService, List<Coupon> expiredCoupons) {
		this.couponService = couponService;
		this.expiredCoupons = expiredCoupons;
	}

	@Override
	public void run() {

		while (true) {
			if (time() == 0) {
				List<Coupon> listCouponExpired=getExpiredCoupons();
				deleteExpiredCoupons(listCouponExpired);
			}else if(time()>=0) {
				System.out.println(time());
			}else if(Thread.currentThread().isInterrupted()) {
				break;
			}else {
				
			}
		}
		
	}

	/**
	 * Delete expired coupons.
	 *
	 * @param list the list
	 */
	private void deleteExpiredCoupons(List<Coupon> list) {

		expiredCoupons = getExpiredCoupons();
		Iterator<Coupon> it = expiredCoupons.iterator();

		while (it.hasNext()) {
			Coupon coupon = it.next();
			try {
				couponService.deleteCoupon(coupon.getId());
			} catch (CouponNotExistException | GlobalException | CustomerNotFoundException | SQLException e) {
			
			}
		}
	}

	/**
	 * Return how many seconds left till midnight.
	 *
	 * @return the long
	 */
	private Long time() {
		ZoneId zone = ZoneId.systemDefault();
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay(zone);
		return ChronoUnit.SECONDS.between(now, midnight);
	}

	/**
	 * Gets the List<Coupon> that will be expire today!
	 *
	 * @return List<Coupon> expired coupons
	 */
	private List<Coupon> getExpiredCoupons() {

		ZoneId zone = ZoneId.systemDefault();
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime midnight = now.toLocalDate().plusDays(1).atStartOfDay(zone);
		LocalDate dayPlus = midnight.toLocalDate();
		Date dayPlusOne = Date.valueOf(dayPlus);
		expiredCoupons = couponService.getAllCoupons().stream().filter(c -> c.getEndDate().before(dayPlusOne))
				.collect(Collectors.toList());
		return expiredCoupons;

	}

}
