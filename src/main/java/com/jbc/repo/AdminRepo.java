package com.jbc.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.jbc.model.Admin;
import com.jbc.model.Customer;


@Repository
@EnableJpaRepositories
public interface AdminRepo extends JpaRepository<Admin, Integer> {

	public boolean existsByEmailAndPassword (String email ,String password);

	public boolean existsByEmail(String username);

	public Optional<Admin> findByEmail(String username);

	
	
	
}
