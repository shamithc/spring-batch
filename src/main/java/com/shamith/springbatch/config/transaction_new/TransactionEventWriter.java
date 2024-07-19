package com.shamith.springbatch.config.transaction_new;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TransactionEventWriter implements ItemWriter<Map<String, Object>> {

    private final TransactionNewRepository transactionNewRepository;
    private final TransactionEventRepository transactionEventRepository;

    @Override
    public void write(Chunk<? extends Map<String, Object>> chunk) throws Exception {

//        TransactionNew[] transactionArray = new TransactionNew[chunk.size()];

        ArrayList<TransactionNew> transactionNewArrayList = new ArrayList<>();
        ArrayList<Long> transactionIdArrayList = new ArrayList();



        for (Map<String, Object> item: chunk){
            System.out.println(item.get("messageId"));
            System.out.println(item.get("messageObject"));
            TransactionNew transactionNew = (TransactionNew) item.get("messageObject");
            transactionNewArrayList.add(transactionNew);
            transactionIdArrayList.add((Long) item.get("messageId"));
            System.out.println(transactionNew);
        }

        doWrite(transactionIdArrayList, transactionNewArrayList);
    }

    @Transactional
    private void doWrite(List<Long> transactionIdList, List<TransactionNew> transactionNewList){

        transactionNewRepository.saveAll(transactionNewList);
    }
}
