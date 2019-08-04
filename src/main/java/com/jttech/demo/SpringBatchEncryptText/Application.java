package com.jttech.demo.SpringBatchEncryptText;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * Spring batch application to encrypt text file with CaesarCipher
 * 
 * Requires two command line inputs "-inputFile=sample.txt -threadCount=4" to run
 * 
 * 
 * @author Jun Li
 *
 */

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) throws Exception {
		if (args.length < 2) {
			log.error("Please provide two parameters: -inputFile=sample.txt -threadCount=4");
			return;
		}
		long time = System.currentTimeMillis();
		SpringApplication.run(Application.class, args);
		time = System.currentTimeMillis() - time;
		log.info("Runtime: {} seconds.", ((double)time/1000));	
	}

}
