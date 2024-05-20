package com.shamith.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class BatchJobInitializerService {

    private final JobLauncher jobLauncher;
    private final Job job;

    @Scheduled(cron = "0 */1 * * * *")
    void runner(){
            try {
                System.out.print("---Started");
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("startAt", System.currentTimeMillis())
                        .toJobParameters();
                Thread.sleep(100000);
                jobLauncher.run(job, jobParameters);
                System.out.print("---End");
            } catch (JobExecutionAlreadyRunningException
                     | JobRestartException
                     | JobInstanceAlreadyCompleteException
                     | JobParametersInvalidException e) {
//            throw new RuntimeException(e);
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

    }
}
