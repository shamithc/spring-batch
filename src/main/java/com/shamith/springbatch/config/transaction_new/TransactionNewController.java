package com.shamith.springbatch.config.transaction_new;

import com.shamith.springbatch.config.BatchConfig;
import com.shamith.springbatch.config.BatchConfigNew;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions_new")
@RequiredArgsConstructor
public class TransactionNewController {

    private final JobLauncher jobLauncher;

//    private final Job job;
     private final BatchConfigNew batchConfignew;


    @PostMapping
    public void importCSV() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        try {
            jobLauncher.run(batchConfignew.eventRunJob(), jobParameters);
        } catch (JobExecutionAlreadyRunningException
                 | JobRestartException
                 | JobInstanceAlreadyCompleteException
                 | JobParametersInvalidException e) {
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

}
