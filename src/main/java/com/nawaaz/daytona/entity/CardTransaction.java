package com.nawaaz.daytona.entity;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CardTransaction implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String transactionId;

    private String originalId;
    private ZonedDateTime transactionTimestamp;
    private String referenceId;
    private BigDecimal amount;
    private Integer systemTraceAuditNumber;
    private String retrievalReferenceNumber;

    @ManyToOne
    @JoinColumn(name = "response_code_id")
    private IssuerResponse issuerResponse;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private CardDetails cardDetails;

    @ManyToOne
    @JoinColumn(name = "cvm_id")
    private CVMDetails cvmDetails;

    @ManyToOne
    @JoinColumn(name = "terminal_id")
    private Terminal terminal;
}