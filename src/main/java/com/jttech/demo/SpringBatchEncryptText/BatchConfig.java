package com.jttech.demo.SpringBatchEncryptText;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Batch Process Config
 * 
 * @author Jun Li
 *
 */
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;
    
    @Value("${output.file.name}")
    private String outputFile;
    
    /**
     * The job demos the following 
     * 
     * 1> Read file in one task
     * 2> Process each line in multi-threaded fashion, one thread per line
     * 3> Write results in one task
     * 
     * In real life when file is big, it will consume more memory, need to consider other implementation 
     * 
     * @return
     */
	@Bean
    public Job encryptFileJob() {
    	return jobs
    			.get("encryptFile")
    			.incrementer(new RunIdIncrementer())
                .start(readLines())
                .next(processLines())
                .next(writeLines())
                .build();
    }

    @Bean
    public Step readLines() {
        return steps
          .get("readLines")
          .tasklet(linesReader())
          .build();
    }
    
    @Bean
    @StepScope
    protected FileReader linesReader() {
    	return new FileReader();
    }
 
    @Bean
    protected Step writeLines() {
        return steps
          .get("writeLines")
          .tasklet(linesWriter())
          .build();
    }
    
    @Bean
    protected FileWriter linesWriter() {
        return new FileWriter();
    }

    @Bean
    protected Step processLines() {
        return steps.get("processLines").<TextLine, TextLine> chunk(1)	// one line at a time
          .reader(new LineReader())
          .processor(encryptTextProcessor())
          .writer(new EmptyItemWriter())
          .taskExecutor(taskExecutor(null))
          .build();
    }
    
    @Bean
    protected EncryptTextProcessor encryptTextProcessor() {
    	return new EncryptTextProcessor();
    }
    
    @Bean
    @StepScope
    protected ThreadPoolTaskExecutor taskExecutor(@Value("#{jobParameters[threadCount]}") Long count) {
    	ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    	executor.setCorePoolSize(count.intValue());
    	return executor;
    }

}
