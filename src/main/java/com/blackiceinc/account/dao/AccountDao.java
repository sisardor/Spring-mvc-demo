package com.blackiceinc.account.dao;

import org.springframework.data.jpa.repository.Query;

import com.blackiceinc.account.model.Account;

public interface AccountDao {
	
	@Query("SELECT u FROM Account u where u.username = :username")
	Account findByUserName(String username);
}
