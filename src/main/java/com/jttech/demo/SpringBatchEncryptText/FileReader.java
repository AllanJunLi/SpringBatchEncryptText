package com.jttech.demo.SpringBatchEncryptText;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

public class FileReader implements Tasklet, StepExecutionListener {

    @Value("#{jobParameters[inputFile]}")
    private String inputFile;

    private List<TextLine> lines;    
    
    @Override
    public void beforeStep(StepExecution stepExecution) {
        lines = new ArrayList<TextLine>();
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
    	this.lines = Files.lines(Paths.get(inputFile)).map(TextLine::new).collect(Collectors.toList());
    	return RepeatStatus.FINISHED;
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution
          .getJobExecution()
          .getExecutionContext()
          .put("lines", this.lines);
        return ExitStatus.COMPLETED;
    }
}