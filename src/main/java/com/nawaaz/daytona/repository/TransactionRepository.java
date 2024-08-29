package com.nawaaz.daytona.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nawaaz.daytona.entity.CardTransaction;

@Repository
public interface TransactionRepository extends JpaRepository<CardTransaction, String> {
}