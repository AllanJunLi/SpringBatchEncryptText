package com.jttech.demo.SpringBatchEncryptText;

import lombok.Data;

@Data
public class TextLine {
	
	private String original;
	private String encrypted;
	
	public TextLine(String text) {
		this.original = text;
	}
	
}
