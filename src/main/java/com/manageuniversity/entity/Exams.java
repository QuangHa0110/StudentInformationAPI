package com.manageuniversity.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exam")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Exams {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "name", nullable = false, length = 500)
	private String name;
	
	@ManyToOne(targetEntity = Courses.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", nullable = false)
	private Courses courses;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exams", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ExamResults> examResults;
	
	
	
	
	
	

}
