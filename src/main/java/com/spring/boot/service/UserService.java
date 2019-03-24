package com.spring.boot.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

@Service
public class UserService {

	
	public String generateRestPassword() throws Exception{
		String generatedString = null;
		try {
			 generatedString = RandomStringUtils.randomAlphanumeric(8);
		}catch(Exception e) {
			throw(e);
		}
		return generatedString;
		
	}
}
