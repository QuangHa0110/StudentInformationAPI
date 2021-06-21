package com.manageuniversity.entity;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teachers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "full_name", length = 250, nullable = false)
	private String fullName;

	@Column(name = "email", length = 100)
	private String email;

	@Column(name = "phone", length = 15)
	private String phone;

	@Column(name = "address", length = 250)
	private String address;

	@Column(name = "grade", length = 20)
	private String grade;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "teachers", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Classes> classes;

}
