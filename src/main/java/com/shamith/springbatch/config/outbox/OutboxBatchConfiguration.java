package com.shamith.springbatch.config.outbox;




import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class OutboxBatchConfiguration {


    private final DataSource dataSource;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final OutboxProcessor  outboxProcessor;



    @Bean
    public Step processOutboxStep(){
        return new StepBuilder("OUTBOX-STEP", jobRepository)
                .<OutboxEntity,  Map<String, Object>>chunk(10000, platformTransactionManager)
                .reader(outboxReader())
                .writer(compositeWriter())
//                .writer(chunk -> chunk.forEach(System.out::println))
                .processor(outboxProcessor)
                .build();
    }



    @Bean
    public ItemReader<? extends OutboxEntity> outboxReader(){
        JdbcCursorItemReader<OutboxEntity> reader = new JdbcCursorItemReader<>();
        reader.setDataSource(dataSource);
        reader.setSql("SELECT * FROM outbox_entity");
        reader.setRowMapper(new BeanPropertyRowMapper<>(OutboxEntity.class));
        return reader;
    }


    @Bean
    public JdbcBatchItemWriter<Map<String, Object>> deleteOutboxEntityWriter() {
        JdbcBatchItemWriter<Map<String, Object>> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("DELETE FROM outbox_entity WHERE id = :id");
        writer.setItemSqlParameterSourceProvider(new HashMapItemSqlParameterSourceProvider());
        return writer;
    }


    @Bean
    public JdbcBatchItemWriter<Map<String, Object>> updateMessageEntityWriter() {
        JdbcBatchItemWriter<Map<String, Object>> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("UPDATE message_entity SET lob = :lob, updated_at = NOW() WHERE id = :message_id");
        writer.setItemSqlParameterSourceProvider(new HashMapItemSqlParameterSourceProvider());
        return writer;
    }


    @Bean
    public CompositeItemWriter<Map<String, Object>> compositeWriter() {
        CompositeItemWriter<Map<String, Object>> compositeWriter = new CompositeItemWriter<>();
        compositeWriter.setDelegates(Arrays.asList(deleteOutboxEntityWriter(), updateMessageEntityWriter()));
        return compositeWriter;
    }


    public Job eventRunJob(){
        return new JobBuilder("OUTBOX-JOB", jobRepository)
                .start(processOutboxStep())
                .build();
    }
}
