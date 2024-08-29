package com.nawaaz.daytona.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.nawaaz.daytona.service.TransactionService;


@SpringBootTest
class TransactionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Autowired
    private TransactionController transactionController;

    @Test
    void testGetAllTransactions() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();

        // Mock the service to return an empty list for testing
        when(transactionService.getAllTransactions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/transactions")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}