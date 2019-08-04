package com.jttech.demo.SpringBatchEncryptText;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class EmptyItemWriter implements ItemWriter<TextLine> {

	@Override
	public void write(List<? extends TextLine> items) throws Exception {
		// do nothing
	}

}
