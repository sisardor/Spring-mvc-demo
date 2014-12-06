package com.blackiceinc.core.assessment.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blackiceinc.model.assessment.AssessmentDate;

public interface IAssessmentDateDao extends JpaRepository<AssessmentDate, Long> {

}
