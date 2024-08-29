package com.nawaaz.daytona.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class IssuerResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private Integer responseCode;

    private String responseDescription;
}