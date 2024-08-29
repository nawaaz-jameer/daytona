package com.nawaaz.daytona.entity;

import java.io.Serial;
import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class CVMDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cvmId;

    @ManyToOne
    @JoinColumn(name = "cvm_method_id")
    private CVMMethod cvmMethod;

    @ManyToOne
    @JoinColumn(name = "cvm_condition_id")
    private CVMCondition cvmCondition;

    @ManyToOne
    @JoinColumn(name = "cvm_rule_result_id")
    private CVMRuleResult cvmRuleResult;
}