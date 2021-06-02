package com.manageuniversity.entity;

import java.io.Serializable;

import lombok.Setter;

import lombok.Getter;
@Getter
@Setter
public class RegistrationsId implements Serializable {
	private Students students;
	private Classes classes;

	public RegistrationsId(Students students, Classes classes) {
		super();
		this.students = students;
		this.classes = classes;
	}

	

}
