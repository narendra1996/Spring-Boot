package com.spring.boot.learning.SpringBoot;

import org.apache.commons.lang3.RandomStringUtils;

public class TestClass {

	public static void main(String [] args) {
		
		String generatedString = RandomStringUtils.randomNumeric(8);
//				randomAlphanumeric(8);
		System.out.println("generatedString: "+generatedString);
	}
}
