package com.manageuniversity.entity;

import java.io.Serializable;

public class RegistrationId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer student;
	private Integer classes;
	
	public RegistrationId() {
		super();
	}
	public RegistrationId(Integer student, Integer classes) {
		super();
		this.student = student;
		this.classes = classes;
	}
	public Integer getStudent() {
		return student;
	}
	public void setStudent(Integer student) {
		this.student = student;
	}
	public Integer getClasses() {
		return classes;
	}
	public void setClasses(Integer classes) {
		this.classes = classes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	


	

}
