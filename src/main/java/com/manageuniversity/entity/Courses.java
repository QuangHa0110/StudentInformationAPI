package com.manageuniversity.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.web.jsf.FacesContextUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Courses {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "name", nullable = false, length = 200)
	private String name;
	
	@Column(name = "type", length = 50)
	private String type;
	
	@Column(name = "create_date", nullable = false)
	private Date createDate;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "courses", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Classes> classes;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "courses", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Plans> plans;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "courses", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Exams> exams;
	

}
