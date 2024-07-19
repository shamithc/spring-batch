package com.shamith.springbatch.config;

import com.shamith.springbatch.config.transaction.Transaction;
import com.shamith.springbatch.config.transaction_new.TransactionEvent;
import com.shamith.springbatch.config.transaction_new.TransactionEventProcessor;
import com.shamith.springbatch.config.transaction_new.TransactionEventRepository;
import com.shamith.springbatch.config.transaction_new.TransactionEventWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class BatchConfigNew {

    private final JobRepository jobRepository;
    private final TransactionEventRepository repository;
    private final PlatformTransactionManager platformTransactionManager;
    private final TransactionEventProcessor transactionEventProcessor;
    private final TransactionEventWriter transactionEventWriter;

//    @Bean
//    public RepositoryItemReader<Map<String, Object>> eventReader1() {
//        RepositoryItemReader<Map<String, Object>> reader = new RepositoryItemReader<>();
//        reader.setRepository(repository);
//        reader.setMethodName("findAllTransactionEvents");
////        reader.setArguments(List.of(EventQueueTypeEnum.raw_aggregated_transactions));
////        HashMap<String, Sort.Direction> sorts = new HashMap<>();
////        sorts.put("id", Sort.Direction.ASC);
////        reader.setSort(sorts);
//        reader.setPageSize(10);
//
//        return reader;
//    }


    @Bean
    public RepositoryItemReader<Map<String, Object>> eventReader() {
        RepositoryItemReader<Map<String, Object>> reader = new RepositoryItemReader<>();
        reader.setRepository(repository);
        reader.setMethodName("findAllTransactionEvents");
//        reader.setArguments(List.of(EventQueueTypeEnum.raw_aggregated_transactions));
        HashMap<String, Sort.Direction> sorts = new HashMap<>();
        sorts.put("id", Sort.Direction.ASC);
        reader.setSort(sorts);
        reader.setPageSize(10);

        return reader;
    }

    @Bean
    public Step eventImportStep(){
        return new StepBuilder("CSV-IMPORTER-1", jobRepository)
                .<Map<String, Object>, Map<String, Object>>chunk(10000, platformTransactionManager)
                .reader(eventReader())
                .processor(transactionEventProcessor)
                .writer(transactionEventWriter)
                .taskExecutor(eventTaskExecutor())
                .build();
    }

    @Bean
    public TaskExecutor eventTaskExecutor(){
        SimpleAsyncTaskExecutor simpleAsyncTaskExecutor = new SimpleAsyncTaskExecutor();
        simpleAsyncTaskExecutor.setConcurrencyLimit(6);
        return simpleAsyncTaskExecutor;
    }

//    @Bean(name = "IMPORT-TRANSACTION-1")
    public Job eventRunJob(){
        return new JobBuilder("IMPORT-TRANSACTION-1", jobRepository)
                .start(eventImportStep())
                .build();
    }
}
