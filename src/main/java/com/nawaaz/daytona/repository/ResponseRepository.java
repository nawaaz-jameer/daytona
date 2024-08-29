package com.nawaaz.daytona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nawaaz.daytona.entity.IssuerResponse;

@Repository
public interface ResponseRepository extends JpaRepository<IssuerResponse, Long> {
}