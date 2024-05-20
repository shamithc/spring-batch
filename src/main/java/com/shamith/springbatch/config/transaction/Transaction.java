package com.shamith.springbatch.config.transaction;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Transaction {
    @Id
    @GeneratedValue
    private Integer id;
    private String txnRefNumber;
    private String amount;
    private String type;
}
