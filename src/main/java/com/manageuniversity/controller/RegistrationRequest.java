package com.manageuniversity.controller;

import org.jetbrains.annotations.NotNull;

import lombok.Data;

@Data
public class RegistrationRequest {
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private String email;
	
	@NotNull
	private String fullName;

}
