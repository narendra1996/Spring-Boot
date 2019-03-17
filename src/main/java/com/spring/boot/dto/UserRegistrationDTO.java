package com.spring.boot.dto;

public class UserRegistrationDTO {
	
	private int id;

	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String acoountType;
	
	private String password;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAcoountType() {
		return acoountType;
	}

	public void setAcoountType(String acoountType) {
		this.acoountType = acoountType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}