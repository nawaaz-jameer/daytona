package com.nawaaz.daytona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nawaaz.daytona.entity.CVMRuleResult;

@Repository
public interface CVMRuleResultRepository extends JpaRepository<CVMRuleResult, Long> {
}