package com.manageuniversity.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Students {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int Id;
	@Column(name = "full_name", nullable = false, length = 250)
	private String fullName;
	@Column(name = "address", length = 250)
	private String address;
	@Column(name = "email", nullable = false, length = 100)
	private String email;
	@Column(name = "phone", length = 15)
	private String phone;
	@Column(name = "birthday")
	private Date birthDay;
	@Column(name = "note", length = 2000)
	private String note;
	@Column(name = "facebook", length = 100)
	private String facebook;
	@Column(name = "create_date", nullable = false)
	private Date createDate;

	
	

}
