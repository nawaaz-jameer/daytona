package com.nawaaz.daytona.entity;

import java.io.Serial;
import java.io.Serializable;

import com.nawaaz.daytona.enums.CardScheme;
import com.nawaaz.daytona.enums.CardType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CardDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    private String primaryAccountNumber;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    private CardScheme cardScheme;

    private String cardIssuer;
    private String cardIssuerCountry;
    private String cardHolderName;
    private Integer cardExpiryDate;
    private String cardBankIdentificationNumber;
}