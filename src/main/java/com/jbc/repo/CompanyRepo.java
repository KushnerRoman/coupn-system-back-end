package com.jbc.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.jbc.model.Company;

@Repository
@EnableJpaRepositories
public interface CompanyRepo extends JpaRepository<Company, Integer> {

	@Query("SELECT count(c)>0 FROM Company c  WHERE email=?1 and password=?2")
	public boolean findByEmailAndPassword(String email, String password);

	@Query("Select id From Company where email=?1 and password=?2")
	public int getIdByEmailAndPassword(String email, String passord);

	@Query("SELECT count(c)>0 From Company c where email=?1 or name=?2")
	public boolean companyValid(String email, String name);


	public Optional<Company> findByEmail(String email);
	
	@Query("SELECT count(c)>0 from Company c where userName=?1")
	public boolean isExistByEmail(String email);

}
