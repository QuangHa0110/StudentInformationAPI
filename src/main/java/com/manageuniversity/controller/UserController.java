package com.manageuniversity.controller;

import java.time.LocalDateTime;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.config.jwt.JwtProvider;
import com.manageuniversity.controller.request.AuthRequest;
import com.manageuniversity.controller.request.RegistrationRequest;
import com.manageuniversity.controller.response.TokenResponse;
import com.manageuniversity.entity.User;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.service.EmailService;
import com.manageuniversity.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private UserService usersService;

	private EmailService emailSErvice;

	private PasswordEncoder passwordEncoder;

	private JwtProvider jwtProvider;

	public UserController(UserService usersService, EmailService emailSErvice, PasswordEncoder passwordEncoder,
			JwtProvider jwtProvider) {
		this.usersService = usersService;
		this.emailSErvice = emailSErvice;
		this.passwordEncoder = passwordEncoder;
		this.jwtProvider = jwtProvider;
	}

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest registrationRequest) {

		User users = new User();
		users.setPassword(registrationRequest.getPassword());
		users.setUsername(registrationRequest.getUsername());
		users.setRegisterDate(LocalDateTime.now());
		users.setEmail(registrationRequest.getEmail());
		users.setFullName(registrationRequest.getFullName());
		

		if (usersService.createStaff(users) != null) {
			System.out.println("************** controller");
			return ResponseEntity.ok("Register successful");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is created");
		}

	}

	@PostMapping("/admin/register")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> registerAdmin(@RequestBody RegistrationRequest request) {
		User users = new User();
		users.setPassword(request.getPassword());
		users.setUsername(request.getUsername());
		users.setRegisterDate(LocalDateTime.now());
		users.setEmail(request.getEmail());
		users.setFullName(request.getFullName());

		if (usersService.updateUsers(users) != null) {
			return ResponseEntity.ok("Register successful");
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is created");
		}

	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody AuthRequest authRequest) {
		User users = usersService.findByUsername(authRequest.getUsername());

		if (users == null) {
			throw new ResourceNotFoundException(authRequest.getUsername() + " not found in system");
		} else if (!passwordEncoder.matches(authRequest.getPassword(), users.getPassword())) {
			if (users.getLoginFailedCount() == null) {
				users.setLoginFailedCount(1);
				usersService.updateUsers(users);
			} else {
				users.setLoginFailedCount(users.getLoginFailedCount() + 1);
				usersService.updateUsers(users);
			}
			throw new BadRequestException("Login falied");

		} else {
			users.setLastLoginDate(LocalDateTime.now());
			usersService.updateUsers(users);
			String token = jwtProvider.generateToken(users.getUsername());

			return ResponseEntity.ok().body(new TokenResponse(token));
		}

	}

	@PostMapping("/forgot-password")
	public ResponseEntity<String> forgotPassword(@RequestParam String email, HttpServletRequest request)
			throws MessagingException {

		String response = usersService.forgotPassword(email);

		if (response == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
		} else {

			emailSErvice.sendMail(email, response, request);

			return ResponseEntity.ok().body("A password reset link has been sent to " + email);
		}

	}

	// @GetMapping("/reset-password")
	// public String resetPassword(@RequestParam(name = "token") String token,
	// @RequestParam(name = "password") String password) {
	// return usersService.resetPassword(token, password);
	// }

	// @PostMapping("/change-password")
	// @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	// public ResponseEntity<String> changePassword(@RequestBody
	// ChangePasswordRequest changePasswordRequest) {
	// return usersService.changePassword(changePasswordRequest.getUsername(),
	// changePasswordRequest.getOldPassword(),
	// changePasswordRequest.getNewPassword());
	// }

}
