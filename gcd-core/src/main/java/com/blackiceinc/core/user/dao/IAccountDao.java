package com.blackiceinc.core.user.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blackiceinc.model.user.Account;

public interface IAccountDao extends JpaRepository<Account, Long>{
	@Query("SELECT u FROM Account u where u.txtUsername = :username")
	Account findByUserName(@Param("username") String username);
}
