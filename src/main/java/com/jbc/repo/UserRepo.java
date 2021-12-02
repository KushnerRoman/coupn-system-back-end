package com.jbc.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbc.model.Role;

public interface UserRepo extends JpaRepository<Role, Long>{

	
	
}
