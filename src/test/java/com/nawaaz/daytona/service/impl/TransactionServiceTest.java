package com.nawaaz.daytona.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.nawaaz.daytona.service.TransactionService;

@SpringBootTest
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Test
    void testProcessCSV() {
        transactionService.processCSV();

        // TODO add assertions
    }
}