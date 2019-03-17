package com.spring.boot.controller;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.spring.boot.dao.CourseDAOImpl;
import com.spring.boot.dto.UserLoginDTO;
import com.spring.boot.dto.UserRegistrationDTO;
import com.spring.boot.entity.UserRegistration;

@RestController
@RequestMapping(value ="/course")
@ComponentScan("com.spring.boot.dao")
@EnableAutoConfiguration
public class CourseController {
	
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	CourseDAOImpl courseDAOImpl;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> userRegistration( @RequestBody UserRegistration dto) throws SQLException {
		logger.info("new user registration start");
		try {
				if(courseDAOImpl.validateEmail(dto.getEmail())) {
					logger.info("Email validation passed registering user");
					courseDAOImpl.registerUser(dto);	
				}
				else {
					return ResponseEntity.status(500).body("{\"message\" : \""+"Email Id already registered, Please try to login"+"\"}");
				}			
		}catch(Exception e){
			logger.info("error while registering user: "+ e.getMessage());
			return ResponseEntity.status(500).body("{\"message\" : \""+"error occured while registering user, Please retry again"+"\"}");
		}
		logger.info("new user registration end");
		return ResponseEntity.status(200).body("{\"message\" : \""+"Email Id Registered Succesfully"+"\"}");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> userLogin( @RequestBody UserLoginDTO dto) throws SQLException {
		logger.info("user login start");
		try {
				if(!courseDAOImpl.validateEmail(dto.getEmail())) {
					logger.info("Email validation passed validating password");
					
					if(courseDAOImpl.validatePassword(dto.getEmail(), dto.getPassword())) {
						logger.info("Password validation passed fetching User details");
						
						UserRegistration user = courseDAOImpl.fetchUserDetails(dto.getEmail(), dto.getPassword());
						logger.info(user.toString());
					}
					else {
						return ResponseEntity.status(500).body("{\"message\" : \""+"Incorrect Password, Please try again"+"\"}");
					}
				}
				else {
					return ResponseEntity.status(500).body("{\"message\" : \""+"Incorrect Email Id, Please try again"+"\"}");
				}			
		}catch(Exception e){
			logger.info("error while registering user: "+ e.getMessage());
			return ResponseEntity.status(500).body("{\"message\" : \""+"error occured while logging in, Please try again"+"\"}");
		}
		logger.info("user login end");
		return ResponseEntity.status(200).body("{\"message\" : \""+"Email Id LogIn Succesful"+"\"}");
	}
	
}
