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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "class")
public class Class {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name", length = 250, nullable = false)
	private String name;

	@Column(name = "start_date", nullable = false)

	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "status", length = 20)
	private String status;



	@ManyToOne(targetEntity = Teacher.class, optional = false)
	@JoinColumn(name = "teacher_id", nullable = false)

	private Teacher teacher;

	@ManyToOne(targetEntity = Course.class, optional = false)
	@JoinColumn(name = "course_id", nullable = false)

	private Course course;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "classes", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Event> events;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "classes", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<ExamResult> examResults;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "classes", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Registration> registrations;

	@Override
	public String toString() {
		return "Classes [id=" + id + ", name=" + name + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", status=" + status + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<ExamResult> getExamResults() {
		return examResults;
	}

	public void setExamResults(List<ExamResult> examResults) {
		this.examResults = examResults;
	}

	public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}
	
	


	
	

}
