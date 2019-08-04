package com.jttech.demo.SpringBatchEncryptText;


import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class LineReader implements ItemReader<TextLine>, StepExecutionListener {

	private List<TextLine> lines;
	private int index = 0;
	
	@SuppressWarnings("unchecked")
	@Override
	public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();
	    this.lines = (List<TextLine>)executionContext.get("lines");
    }

	@Override
	public TextLine read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		synchronized (lines) {
			if (index < lines.size()) {
				TextLine line = lines.get(index);
				index++;
				return line;
			}		
		}
		return null;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;	
    }

}
