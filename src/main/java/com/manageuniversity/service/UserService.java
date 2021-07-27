package com.manageuniversity.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.manageuniversity.entity.Role;
import com.manageuniversity.entity.User;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.RoleRepository;
import com.manageuniversity.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersService.
 */
@Service
public class UserService {

	/** The Constant EXPIRE_TOKEN_AFTER_MINUTES. */
	private static final int EXPIRE_TOKEN_AFTER_MINUTES = 10;

	/** The users repository. */

	private UserRepository usersRepository;

	/** The roles repository. */

	private RoleRepository rolesRepository;

	/** The password encoder. */

	private PasswordEncoder passwordEncoder;

	

	public UserService(UserRepository usersRepository, RoleRepository rolesRepository,
			PasswordEncoder passwordEncoder) {
		this.usersRepository = usersRepository;
		this.rolesRepository = rolesRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Creates the users.
	 *
	 * @param users the users
	 * @return the users
	 */
	public User createUsers(User users) {

		if (usersRepository.findByUsername(users.getUsername()) != null) {
			return null;
		}
		Role roles = rolesRepository.findByName("ROLE_STAFF");
		users.setRole(roles);
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		return usersRepository.save(users);

	}
	
	public User createAdmin(User users) {
		if (usersRepository.findByUsername(users.getUsername()) != null) {
			return null;
		}
		Role roles = rolesRepository.findByName("ROLE_ADMIN");
		users.setRole(roles);
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		return usersRepository.save(users);
	}

	/**
	 * Update users.
	 *
	 * @param users the users
	 * @return the users
	 */
	public User updateUsers(User users) {
		return usersRepository.save(users);
	}

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the users
	 */
	public User findByUsername(String username) {
		return usersRepository.findByUsername(username);
	}

	/**
	 * Find by username and password.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the users
	 */
	public User findByUsernameAndPassword(String username, String password) {
		User users = findByUsername(username);
		if (users != null) {
			if (passwordEncoder.matches(password, users.getPassword())) {
				return users;
			}
		}
		return null;
	}

	/**
	 * Forgot password.
	 *
	 * @param email the email
	 * @return the string
	 */
	public String forgotPassword(String email) {
		Optional<User> userOptional = Optional.ofNullable(usersRepository.findByEmail(email));
		
		if (!userOptional.isPresent()) {
			return null;
		}

		User users = userOptional.get();

		users.setForgotPasswordToken(generateToken());
		users.setTokenCreationDate(LocalDateTime.now());

		users = usersRepository.save(users);
		

		return users.getForgotPasswordToken();
	}

	/**
	 * Reset password.
	 *
	 * @param token    the token
	 * @param password the password
	 * @return the string
	 */
	public String resetPassword(String token, String password) {

		Optional<User> userOptional = Optional.ofNullable(usersRepository.findByForgotPasswordToken(token));

		if (!userOptional.isPresent()) {
			return "Invalid token.";
		}

		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			return "Token expired.";

		}

		User user = userOptional.get();

		user.setPassword(passwordEncoder.encode(password));
		user.setForgotPasswordToken(null);
		user.setTokenCreationDate(null);

		usersRepository.save(user);

		return "Your password successfully updated.";
	}

	/**
	 * Generate unique token. You may add multiple parameters to create a strong
	 * token.
	 * 
	 * @return unique token
	 */
	private String generateToken() {
		StringBuilder token = new StringBuilder();

		return token.append(UUID.randomUUID().toString()).append(UUID.randomUUID().toString()).toString();
	}

	/**
	 * Check whether the created token expired or not.
	 *
	 * @param tokenCreationDate the token creation date
	 * @return true or false
	 */
	private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

		LocalDateTime now = LocalDateTime.now();
		Duration diff = Duration.between(tokenCreationDate, now);

		return diff.toMinutes() >= EXPIRE_TOKEN_AFTER_MINUTES;
	}
	
	
	/**
	 * Change password.
	 *
	 * @param username the username
	 * @param oldPassword the old password
	 * @param newPassword the new password
	 * @return the response entity
	 */
	public ResponseEntity<String> changePassword(String username, String oldPassword, String newPassword) {
		User users = usersRepository.findByUsername(username);
		if(users == null) {
			throw new ResourceNotFoundException(username+" not found in system");
		}
		else if(!passwordEncoder.matches(oldPassword, users.getPassword())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password incorrect");
			
		}else {
			users.setPassword(passwordEncoder.encode(newPassword));
			usersRepository.save(users);
			return ResponseEntity.ok().body("Change password successful");
		}
	}
	

}
