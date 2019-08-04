package com.jttech.demo.SpringBatchEncryptText;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EncryptTextProcessor implements ItemProcessor<TextLine, TextLine>, StepExecutionListener {
	
	@Value("${caesar.cipher.shift:6}")
	private int shift;
	
    @Autowired
    private ThreadPoolTaskExecutor executor;

   @Override
    public TextLine process(TextLine line) throws Exception {
	   	String original = line.getOriginal();
	   	if (original != null) {
			String encrypted = CaesarCipher.encrypt(original, shift);
			line.setEncrypted(encrypted);
			log.debug("Thread-" + Thread.currentThread().getId() + " encrypting " + line.getOriginal() + " to " + line.getEncrypted());
	   	}
        return line;
    }

	@Override
	public void beforeStep(StepExecution stepExecution) {
		// do nothing
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// need to manually shut thread pool
		executor.shutdown();
		return ExitStatus.COMPLETED;
	}

}