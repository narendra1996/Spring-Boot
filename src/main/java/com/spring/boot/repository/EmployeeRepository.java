package com.spring.boot.repository;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.spring.boot.entity.Employee;

@Repository
@ComponentScan("com.spring.boot.entity")
public interface EmployeeRepository extends CrudRepository<Employee, Integer>{
	
//	List<Employee> findById(long id);
}
