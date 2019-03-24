package com.spring.boot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import com.spring.boot.dto.EmployeeDTO;
import com.spring.boot.entity.Employee;
import com.spring.boot.repository.EmployeeRepository;

@Repository
@EntityScan("com.spring.boot.entity")
public class EmployeeDAOImpl {

	@Autowired
	NamedParameterJdbcTemplate  jdbcTemplate;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeDAOImpl.class);
	
	public void insertEmployee(Employee dto) throws SQLException {
		Employee emp = new Employee();
		emp.setName(dto.getName());
		emp.setPhone(dto.getPhone());
		emp.setRole(dto.getRole());
		try {
			employeeRepository.save(dto);
		}catch(Exception e) {
			log.info("Exception while saving Employee: "+ e);
		}
//		String query = "Insert into firsttable (id , name, phone, role) values (:Id, :Name, :Phone, :Role)";
	}
	
	public EmployeeDTO getEmployee(int id) throws SQLException {
		String query = "select * from firsttable where id = :id";
		Map<String,Integer> map = new HashMap<String,Integer>();
		EmployeeDTO dto = null;
		map.put("id", id);
		ResultSetExtractor<EmployeeDTO> rse = new ResultSetExtractor<EmployeeDTO>() {
			@Override
			public EmployeeDTO extractData(ResultSet rs) throws SQLException, DataAccessException {
				EmployeeDTO dto = new EmployeeDTO();
				while(rs.next()) {
					dto.setId(rs.getInt("ID"));
					dto.setName(rs.getString("NAME"));
					dto.setPhone(rs.getString("PHONE"));
					dto.setRole(rs.getString("ROLE"));	
				}
				return dto;
			}		
		};
		dto = jdbcTemplate.query(query, map, rse);
		log.info("Name: "+dto.toString());
		return dto;
	}
}
