package com.blackiceinc.core.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.blackiceinc.model.user.Role;

public interface IRoleDao extends JpaRepository<Role, Long>, 
JpaSpecificationExecutor<Role>{
	
//	@Query("SELECT u FROM User u where u.username = :username")
//	Role getRole(int id);
}

