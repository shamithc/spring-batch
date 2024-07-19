package com.shamith.springbatch.config.transaction_new;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TransactionEventRepository extends JpaRepository<TransactionEvent, Long> {

    @Query("SELECT eq.id AS ID, eq.transactionId AS TXN_ID FROM TransactionEvent eq")
    Page<Map<String, Object>> findAllTransactionEvents(Pageable pageable);
}
