package com.blackiceinc.core.regulator.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.blackiceinc.model.regulator.ReviewType;

public interface IReviewTypeDao extends JpaRepository<ReviewType, Long>,
		JpaSpecificationExecutor<ReviewType> {

}
