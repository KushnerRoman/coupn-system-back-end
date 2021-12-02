package com.jbc.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import com.jbc.model.Coupon;

@Repository
@EnableJpaRepositories
public interface CouponRepo extends JpaRepository<Coupon, Integer> {

	
	public List<Coupon> findAllByCompany(int companyId);

	@Query("SELECT count(c)>0 FROM Coupon c  WHERE  c.title=?1  and c.company=?2  ")
	public boolean isExist(String title,int company );

	
	public boolean existsByTitleAndCompany(String title,int company );
	
	
	
	


	 
}
