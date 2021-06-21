package com.manageuniversity.entity;

import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class Users {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer id;
	
	@Column(name = "username", nullable = false, unique = true)
	private String username;
	
	@Column(name = "password", nullable = false, unique = true)
	private String password;
	
	@Column(name = "fullname")
	private String fullName;
	
	@Column(name = "email", unique = true)
	private String email;
	
	@Column(name = "last_login_date")
	private LocalDateTime lastLoginDate;
	
	@Column(name = "lock_out_date")
	private LocalDateTime lockOutDate;
	
	@Column(name = "login_failed_count")
	private Integer loginFailedCount;
	
	@Column(name = "register_date")
	private LocalDateTime registerDate;
	
	@Column(name = "forgot_password_token")
	private String forgotPasswordToken;
	
	@Column(name = "token_creation_date")
	private LocalDateTime tokenCreationDate;

	@ManyToOne(targetEntity = Roles.class)
	@JoinColumn(name = "role_id", nullable =  false)
	private Roles roles;
	


}
