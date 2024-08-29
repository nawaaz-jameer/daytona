package com.nawaaz.daytona.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class TransactionType implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer transactionTypeCode;

    private String transactionTypeDescription;
}