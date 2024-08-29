package com.nawaaz.daytona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nawaaz.daytona.entity.CVMMethod;

@Repository
public interface CVMMethodRepository extends JpaRepository<CVMMethod, Long> {
}