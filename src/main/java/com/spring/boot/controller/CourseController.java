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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.spring.boot.dao.CourseDAOImpl;
import com.spring.boot.dto.ChangePasswordDTO;
import com.spring.boot.dto.UserLoginDTO;
import com.spring.boot.entity.UserRegistration;
import com.spring.boot.service.MailService;
import com.spring.boot.service.UserService;

@RestController
@RequestMapping(value ="/course")
@ComponentScan({"com.spring.boot.dao", "com.spring.boot.service"})
@EnableAutoConfiguration
public class CourseController {
	
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	@Autowired
	CourseDAOImpl courseDAOImpl;
	
	@Autowired
	MailService mailService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> userRegistration(@RequestBody UserRegistration dto) {
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
			logger.error("error while registering user: "+ e.getMessage());
			return ResponseEntity.status(500).body("{\"message\" : \""+"error occured while registering user, Please retry again"+"\"}");
		}
		logger.info("new user registration end");
		return ResponseEntity.status(200).body("{\"message\" : \""+"Email Id Registered Succesfully"+"\"}");
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> userLogin( @RequestBody UserLoginDTO dto) {
		logger.info("user login start");
		UserRegistration user = null;
		try {
				if(!courseDAOImpl.validateEmail(dto.getEmail())) {
					logger.info("Email validation passed validating password");
					
					if(courseDAOImpl.validatePassword(dto.getEmail(), dto.getPassword())) {
					 logger.info("Password validation passed fetching User details");
						 user = courseDAOImpl.fetchUserId(dto.getEmail(), dto.getPassword());
						 user.setPassword(null);
					}
					else {
						return ResponseEntity.status(500).body("{\"message\" : \""+"Incorrect Password, Please try again"+"\"}");
					}
				}
				else {
					return ResponseEntity.status(500).body("{\"message\" : \""+"Incorrect Email Id, Please try again"+"\"}");
				}			
		}catch(Exception e){
			logger.error("error while registering user: "+ e.getMessage());
			return ResponseEntity.status(500).body("{\"message\" : \""+"error occured while logging in, Please try again"+"\"}");
		}
		logger.info("user login end");
		return ResponseEntity.status(200).body(user);
	}
	
	@RequestMapping(value = "/resetpassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> mailService(@RequestParam String emailId) {
		logger.info("reset password service start");
		try {
			String tempPassword = userService.generateRestPassword();
			courseDAOImpl.restPassword(emailId, tempPassword);
			mailService.sendRecoveryEmail(emailId, tempPassword);	
		}catch(Exception e){
			logger.error("error while resetting password: "+ e.getMessage());
			return ResponseEntity.status(500).body("{\"message\" : \""+"error occured while resetting  password, Please try again"+"\"}");
		}
		logger.info("reset password service end");
		return ResponseEntity.status(200).body("{\"message\" : \""+"Recovery password sent to your Email"+"\"}");
	}
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDTO dto){
		logger.info("change password service start");
		try {
			if(courseDAOImpl.validateRecoveryPassword(dto)) {
				logger.info("Recovery Password Validation passed");
				courseDAOImpl.changePassword(dto);
			}else {
				logger.info("Incorrect Recovery Password, Sending error response");
				return ResponseEntity.status(500).body("{\"message\" : \""+"Incorrect Recovery password, Please try again"+"\"}");
			}
		}catch(Exception e){
			logger.error("error while resetting password: "+ e.getMessage());
			return ResponseEntity.status(500).body("{\"message\" : \""+"error occured while Changing password, Please try again"+"\"}");
		}
		logger.info("change password service end");
		return ResponseEntity.status(200).body("{\"message\" : \""+"Password changed Successfully, directing to Login Page"+"\"}");
	}
	
	@RequestMapping(value = "/getUserDetail", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> userDetailService(@RequestParam String id) {
		UserRegistration user = null;
		logger.info("userDetailService start");
		try {
			user = courseDAOImpl.fetchUserDetails(id);
			user.setPassword(null);
		}catch(Exception e){
			logger.error("error while fetching user details: "+ e.getMessage());
			return ResponseEntity.status(500).body("{\"message\" : \""+"error occured while retrieving  User Details"+"\"}");
		}
		logger.info("userDetailService end");
		return ResponseEntity.status(200).body(user);
	}
	
/*	public String constructUserJson(UserRegistration user) throws JSONException{
		logger.info("constructing user Json start");
		JSONObject data = new JSONObject();
		JSONObject json = new JSONObject();
		json.put("id", user.getId());
		json.put("firstName", user.getFirstName());
		json.put("lastName", user.getLastName());
		json.put("email", user.getEmail());
		json.put("accountType", user.getAccountType());
		data.putOpt("data", json);
		logger.info("constructing user Json end");
		return data.toString();
	}*/
}
