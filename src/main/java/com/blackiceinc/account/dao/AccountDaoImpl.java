package com.blackiceinc.account.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.blackiceinc.account.model.Account;

//@Repository
//public class AccountDaoImpl implements AccountDao {
//	
//	@PersistenceContext
//	EntityManager entityManager;
//	
//
//	@Override
//	public Account findByUserName(String username) {
//		Query q = entityManager.createQuery("select a from Account a where a.txtUsername = :txtUsername");
//		q.getResultList();
//		
//		return (Account)q.getResultList();
//	}
//
//
//	@Override
//	public List<Account> getAl(String username) {
//		Query q = entityManager.createQuery("select a from Account a");
//		q.getResultList();
//		System.out.println("--------------");
//		return (List<Account>)q.getResultList();
//	}
//
//}
