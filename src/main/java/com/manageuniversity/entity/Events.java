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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Events {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "name", nullable =  false, length = 250)
	private String name;
	
	@Column(name = "create_date", nullable = false)
	private Date createDate;
	
	@Column(name = "status", length = 20)
	private String status;
	
	@Column(name = "happen_date", nullable = false)
	private Date happenDate;
	
	@ManyToOne(targetEntity = Classes.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "class_id")
	private Classes classes;
	
	


	
	

	
	

}
