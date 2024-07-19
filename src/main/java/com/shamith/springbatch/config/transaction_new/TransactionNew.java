package com.shamith.springbatch.config.transaction_new;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class TransactionNew {
    @Id
    @GeneratedValue
    private Long id;
    private String txnRefNumber;
    private String amount;
    private String type;
}

