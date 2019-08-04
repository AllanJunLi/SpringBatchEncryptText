package com.jttech.demo.SpringBatchEncryptText;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileWriter implements Tasklet, StepExecutionListener {
	
	private List<TextLine> lines;
	
    @Value("${output.file.name}")
    private String outputFile;
    
	@SuppressWarnings("unchecked")
	@Override
	public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
        this.lines = (List<TextLine>) executionContext.get("lines");
	}

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Files.write(Paths.get(outputFile), (Iterable<String>)lines.stream().map(line -> line.getEncrypted())::iterator);
         return RepeatStatus.FINISHED;	
    }

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		log.info("Encrypted file generated at '{}'", outputFile);
        return ExitStatus.COMPLETED;
	}

}
