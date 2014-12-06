package com.blackiceinc.core.assessment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blackiceinc.model.assessment.Assessment;

public interface IAssessmentDao extends JpaRepository<Assessment, Long> {

}
