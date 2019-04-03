package com.spring.boot.dao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.spring.boot.dto.ChangePasswordDTO;
import com.spring.boot.entity.UserRegistration;
import com.spring.boot.repository.UserRegistrationRepository;

@Repository
@EntityScan("com.spring.boot.entity")
public class CourseDAOImpl {
	
	private static final Logger logger = LoggerFactory.getLogger(CourseDAOImpl.class);
	
	@Autowired
	NamedParameterJdbcTemplate  jdbcTemplate;
	
	@Autowired
	UserRegistrationRepository userRegistrationRepository;
	
	private static final Logger log = LoggerFactory.getLogger(CourseDAOImpl.class);
	
	public void  registerUser(UserRegistration dto) throws Exception{
		try {
			userRegistrationRepository.save(dto);
		}catch(Exception e) {
			log.error("Exception while registering User: "+ e.getMessage());
			throw(e);
		}
	}
	
	public boolean validateEmail(String email) {
		logger.info("validating Email start");
		boolean valid = false;
		try {
			String query = "SELECT COUNT(EMAIL) FROM USERS WHERE UPPER(EMAIL) = UPPER(:email)";
			Map<String,String> map = new HashMap<String, String>();
			map.put("email", email);
			int count = jdbcTemplate.queryForObject(query, map, Integer.class);
			if(count == 0) {
				valid = true;
			}	
		}catch(Exception e) {
			logger.error("error occured while validating email");
		}
		logger.info("validating Email end");
		return valid;
		
	}
	
	public boolean validatePassword(String email, String password) {
		logger.info("validating Password start");
		boolean valid = false;
		try {
			String query = "SELECT COUNT(*) FROM USERS WHERE UPPER(EMAIL) = UPPER(:email) AND PASSWORD = :password";
			Map<String,String> map = new HashMap<String, String>();
			map.put("email", email);
			map.put("password", password);
			int count = jdbcTemplate.queryForObject(query, map, Integer.class);
			if(count == 1) {
				valid = true;
			}	
		}catch(Exception e) {
			logger.error("error occured while validating password");
		}
		logger.info("validating Password end");
		return valid;
		
	}
	
	public UserRegistration  fetchUserDetails(String id){
		UserRegistration user = null;
		try {
			user = userRegistrationRepository.getUser(id);
		}catch(Exception e) {
			log.error("Exception while registering User: "+ e.getMessage());
			throw(e);
		}
		return user;
	}
	
	public void restPassword(String email, String tempPassword) {
		try {
			String query = "UPDATE USERS SET TEMP_PASSWORD=:password WHERE UPPER(EMAIL)=UPPER(:emailId)";
			Map<String,String> map = new HashMap<String,String>();
			map.put("password", tempPassword);
			map.put("emailId", email);
			jdbcTemplate.update(query, map);
		}catch(Exception e) {
			logger.error("error while updating temporary Password: "+e.getMessage());
		}
	}

	public UserRegistration fetchUserId(String email, String password) {
		UserRegistration user = null;
		try {
			user = userRegistrationRepository.getUserDetails(email, password);
		}catch(Exception e) {
			log.error("Exception while registering User: "+ e.getMessage());
			throw(e);
		}
		return user;
	}
	
	public boolean validateRecoveryPassword(ChangePasswordDTO dto){
		boolean valid = false;
		logger.info("validating Recovery Password start");
		try {
			int count = 0;
			String query = "SELECT COUNT(*) FROM USERS WHERE TEMP_PASSWORD=:password AND UPPER(EMAIL)=UPPER(:emailId)";
			Map<String,String> map = new HashMap<String,String>();
			map.put("password", dto.getResetPassword());
			map.put("emailId", dto.getEmail());
			count = jdbcTemplate.queryForObject(query, map, Integer.class);
			if(count == 1) {
				valid = true;
			}
		}catch(Exception e) {
			log.error("Exception while validating recovery password: "+ e.getMessage());
			throw(e);
		}
		logger.info("validating Recovery Password start");
		return valid;
	}
	
	public void changePassword(ChangePasswordDTO dto){
		logger.info("Password change start");
		try {
			String query = "UPDATE USERS SET PASSWORD=:password, TEMP_PASSWORD=null WHERE UPPER(EMAIL)=UPPER(:emailId)";
			Map<String,String> map = new HashMap<String,String>();
			map.put("password", dto.getPassword());
			map.put("emailId", dto.getEmail());
			jdbcTemplate.update(query, map);
		}catch(Exception e) {
			log.error("Exception while changing password: "+ e.getMessage());
			throw(e);
		}
		logger.info("Password change end");
	}
}
