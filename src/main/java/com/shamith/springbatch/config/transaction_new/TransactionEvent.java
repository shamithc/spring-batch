package com.shamith.springbatch.config.transaction_new;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transaction_events")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long transactionId;

}
