package com.nawaaz.daytona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nawaaz.daytona.entity.CVMCondition;

@Repository
public interface CVMConditionRepository extends JpaRepository<CVMCondition, Long> {
}