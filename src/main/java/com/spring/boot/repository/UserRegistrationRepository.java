package com.spring.boot.repository;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.spring.boot.entity.UserRegistration;

@Repository
@ComponentScan("com.spring.boot.entity")
public interface UserRegistrationRepository extends CrudRepository<UserRegistration, Integer>{

	public static final String GET_USER_DETAILS = "SELECT * FROM USERS WHERE UPPER(EMAIL) = UPPER(:email) and password = :password";

	@Query(value = GET_USER_DETAILS, nativeQuery = true)
	public UserRegistration getUserDetails(@Param("email") String email, @Param("password") String password);
}
