package com.jbc.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.jbc.controller.client.ClientType;
import com.jbc.model.Role;




@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {

	/* @Query("SELECT role FROM Role where type=?1") */
	public Optional<Role> findByType(ClientType type);
}
