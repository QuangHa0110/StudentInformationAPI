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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exam_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResults {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int Id;
	
	@Column(name = "score", nullable = false)
	private int score;
	
	@Column(name = "result_date", nullable = false)
	private Date resultDate;
	
	@Column(name = "note", length = 2000)
	private String note;
	
	@ManyToOne(targetEntity = Students.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "student_id", nullable = false)
	private Students students;
	
	@ManyToOne(targetEntity = Exams.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "exam_id", nullable = false)
	private Exams exams;
	
	@ManyToOne(targetEntity = Classes.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "class_id", nullable = false)
	private Classes classes;

	

	
	
}
