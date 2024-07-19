package com.shamith.springbatch.config.outbox;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class MessageEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String txnRefNumber;
    private String amount;
    private String lob;
    private String usecase;
}
