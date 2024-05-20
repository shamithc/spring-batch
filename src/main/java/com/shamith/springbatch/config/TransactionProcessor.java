package com.shamith.springbatch.config;

import com.shamith.springbatch.config.transaction.Transaction;
import org.springframework.batch.item.ItemProcessor;

public class TransactionProcessor implements ItemProcessor<Transaction, Transaction> {

    @Override
    public Transaction process(Transaction transaction) throws Exception {
        transaction.setId(null);
        return transaction;
    }
}
