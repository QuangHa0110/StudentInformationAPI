package com.manageuniversity.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.NoArgsConstructor;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Table(name = "registration")
@IdClass(RegistrationsId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Registrations {
	@Id
	@ManyToOne(targetEntity = Students.class, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "student_id", nullable = false)
	private Students students;

	@Id
	@ManyToOne(targetEntity = Classes.class, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "class_id", nullable = false)
	private Classes classes;

	@Column(name = "register_day", nullable = false)
	private Date registerDay;

	@Column(name = "status", nullable = false, length = 10)
	private String status;

	@Column(name = "create_date", nullable = false)
	private Date createDate;

}
