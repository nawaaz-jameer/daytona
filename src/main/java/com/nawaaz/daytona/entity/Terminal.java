package com.nawaaz.daytona.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Terminal implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String terminalId;

    private String terminalSerial;
}