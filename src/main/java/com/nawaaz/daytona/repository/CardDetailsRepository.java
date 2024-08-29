package com.nawaaz.daytona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nawaaz.daytona.entity.CardDetails;

@Repository
public interface CardDetailsRepository extends JpaRepository<CardDetails, Long> {
}