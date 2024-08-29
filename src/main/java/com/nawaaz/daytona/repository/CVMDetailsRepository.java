package com.nawaaz.daytona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nawaaz.daytona.entity.CVMDetails;

@Repository
public interface CVMDetailsRepository extends JpaRepository<CVMDetails, Long> {
}