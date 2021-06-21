package com.manageuniversity.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manageuniversity.config.jwt.JwtProvider;
import com.manageuniversity.controller.request.AuthRequest;
import com.manageuniversity.controller.request.ForgotPasswordRequest;
import com.manageuniversity.entity.Users;
import com.manageuniversity.service.UsersService;

@RestController
@RequestMapping("/api/v1/users")
public class UsersController {
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private JwtProvider jwtProvider;
	
	
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody @Validated RegistrationRequest registrationRequest) {
		Users users = new Users();
		users.setPassword(registrationRequest.getPassword());
		users.setUsername(registrationRequest.getUsername());
		users.setRegisterDate(LocalDateTime.now());
		users.setEmail(registrationRequest.getEmail());
		users.setFullName(registrationRequest.getFullName());
		
		if(usersService.createUsers(users)!=null) {
			return ResponseEntity.ok("OK");
		}
		else {
			return ResponseEntity.status(HttpStatus.CREATED).body("Username is created");
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> auth(@RequestBody AuthRequest authRequest) {
		Users users= usersService.findByUsernameAndPassword(authRequest.getUsername(), authRequest.getPassword());
		
		if(users == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Login failed");
		}
		else {
			users.setLastLoginDate(LocalDateTime.now());
			usersService.updateUsers(users);
			
			String token = jwtProvider.generateToken(users.getUsername());
			return ResponseEntity.ok().body(token);
		}
		
		
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) {

		String response = usersService.forgotPassword(email);
		if(response == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
		}

	
		return ResponseEntity.ok().body(response);
	}

	@PutMapping("/reset-password")
	public String resetPassword(@RequestBody ForgotPasswordRequest request) {

		return usersService.resetPassword(request.getToken(),request.getPassword());
	}

}
