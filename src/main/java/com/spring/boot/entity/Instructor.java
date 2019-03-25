package com.spring.boot.entity;

import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "INSTRUCTOR")
public class Instructor {
	
	private static long millis = System.currentTimeMillis(); 

	@Id
	private String id;
	
	@Column
	private String courseId;
	
	@Column
	private String deptId;
	
	@Column
	private static Date enrollDate = new Date(millis);

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Date getEnrollDate() {
		return enrollDate;
	}	
	
}
