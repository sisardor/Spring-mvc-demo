package com.blackiceinc.account.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blackiceinc.account.model.Account;

public interface AccountDao extends JpaRepository<Account, Long>{
	
	@Query("SELECT u FROM Account u where u.txtUsername = :username")
	Account findByUserName(@Param("username") String username);
}
