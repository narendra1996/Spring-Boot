package com.spring.boot.controller;

import java.sql.SQLException;
import javax.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.spring.boot.dao.EmployeeDAOImpl;
import com.spring.boot.dto.EmployeeDTO;
import com.spring.boot.entity.Employee;
import com.spring.boot.repository.EmployeeRepository;

@RestController
@RequestMapping(value ="/employee")
@ComponentScan("com.spring.boot.dao")
@EnableAutoConfiguration
public class EmployeeController {
	
	@Autowired
	EmployeeDAOImpl employeeDAOImpl;
		
	private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@RequestMapping(value = "/getEmp", method = RequestMethod.GET)
	public EmployeeDTO getEmployeeName(@PathParam("id") String id) throws SQLException {
		EmployeeDTO dto = null;
		logger.info("Processing Request for Id: " + id);
		try {
				int Id = Integer.parseInt(id);
				dto = employeeDAOImpl.getEmployee(Id);
		}catch(SQLException e){
			logger.info("error while retrieving employee name: "+ e.getMessage());
		}catch(Exception e) {
			logger.info("error while retrieving PathParam id: "+ e.getMessage());
		}
		return dto;
	}
	
	@RequestMapping(value = "/emp.save", method = RequestMethod.POST)
	public EmployeeDTO createEmployee(@RequestBody Employee emp){
		EmployeeDTO dto = null;
		logger.info("Processing new Employee Request");
		try {
			if(emp != null ) {
				employeeDAOImpl.insertEmployee(emp);
			}
		}catch(Exception e){
			logger.info("error while creating new employee: "+ e.getMessage());
		}
		return dto;
	}
}
