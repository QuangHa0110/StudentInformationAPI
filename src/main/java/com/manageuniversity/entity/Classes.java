package com.manageuniversity.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "class")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Classes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private int Id;

	@Column(name = "name", length = 250, nullable = false)
	private String name;

	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "status", length = 20)
	private String status;

	@ManyToOne(targetEntity = Teachers.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "teacher_id", nullable = false)
	private Teachers teachers;

	@ManyToOne(targetEntity = Courses.class, fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "course_id", nullable = false)
	private Courses courses;

	

}
