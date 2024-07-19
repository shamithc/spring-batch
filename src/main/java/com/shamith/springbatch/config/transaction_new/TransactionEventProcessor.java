package com.shamith.springbatch.config.transaction_new;

import com.shamith.springbatch.config.transaction.Transaction;
import com.shamith.springbatch.config.transaction.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TransactionEventProcessor implements ItemProcessor<Map<String, Object>, Map<String, Object>> {

    private final TransactionRepository transactionRepository;


    @Override
    public Map<String, Object> process(Map<String, Object> item) throws Exception {
        Long eventId = (Long) item.get("ID");
        Long transactionId = (Long) item.get("TXN_ID");
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        Transaction transaction = optionalTransaction.get();

        TransactionNew transactionNew = new TransactionNew();
        transactionNew.setAmount(transaction.getAmount());
        transactionNew.setTxnRefNumber(transaction.getTxnRefNumber());
        transactionNew.setType(transaction.getType());


        return Map.of(
                "messageObject", transactionNew,
                "messageId", eventId
        );
    }
}
