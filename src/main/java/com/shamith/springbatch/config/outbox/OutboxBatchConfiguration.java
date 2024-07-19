//package com.shamith.springbatch.config.outbox;
//
//
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.util.Map;
//
//@Configuration
//@RequiredArgsConstructor
//public class OutboxBatchConfiguration {
//
//
//    private final DataSource dataSource;
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager platformTransactionManager;
//
//
//
//
//    public Step processOutboxStep(){
//        return new StepBuilder("OUTBOX", jobRepository)
//                .<OutboxEntity,  MessageEntity>chunk(10, platformTransactionManager)
//                .reader()
//                .writer()
//                .processor()
//                .build();
//    }
//
//}
