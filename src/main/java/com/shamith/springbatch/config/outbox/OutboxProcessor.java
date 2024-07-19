package com.shamith.springbatch.config.outbox;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OutboxProcessor implements ItemProcessor<OutboxEntity, Map<String, Object>> {


//    @Override
//    public OutboxEntity process(OutboxEntity item) throws Exception {
//        return item;
//    }


    @Override
    public Map<String, Object> process(OutboxEntity item) throws Exception {
        if(item.getId() % 2 ==0 ){
            return null;
        }

        final Map<String, Object> mapObject = new HashMap<>();
        mapObject.put("id", item.getId());
        mapObject.put("message_id", item.getMessageId());
        mapObject.put("lob", "PG");
        return mapObject;
    }


}
