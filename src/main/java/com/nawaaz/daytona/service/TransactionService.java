package com.nawaaz.daytona.service;

import java.util.List;

import com.nawaaz.daytona.entity.CardTransaction;

public interface TransactionService {
    void processCSV();

    List<CardTransaction> getAllTransactions();
}