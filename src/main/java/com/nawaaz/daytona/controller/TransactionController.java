package com.nawaaz.daytona.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nawaaz.daytona.entity.CardTransaction;
import com.nawaaz.daytona.service.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/process")
    public String processTransactions() {
        transactionService.processCSV();
        return "CSV processing started.";
    }

    @GetMapping("/transactions")
    public List<CardTransaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }
}