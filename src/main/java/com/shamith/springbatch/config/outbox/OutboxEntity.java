package com.shamith.springbatch.config.outbox;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class OutboxEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long messageId;
}
