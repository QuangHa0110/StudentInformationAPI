package com.manageuniversity.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.manageuniversity.entity.Roles;
import com.manageuniversity.entity.Users;
import com.manageuniversity.repository.RolesRepository;
import com.manageuniversity.repository.UsersRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class UsersService.
 */
@Service
public class UsersService {
	
	/** The Constant EXPIRE_TOKEN_AFTER_MINUTES. */
	private static final int EXPIRE_TOKEN_AFTER_MINUTES = 10;

	/** The users repository. */
	@Autowired
	private UsersRepository usersRepository;

	/** The roles repository. */
	@Autowired
	private RolesRepository rolesRepository;

	/** The password encoder. */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Creates the users.
	 *
	 * @param users the users
	 * @return the users
	 */
	public Users createUsers(Users users) {
		
		if(usersRepository.findByUsername(users.getUsername())!=null) {
			return  null;
		}
		Roles roles = rolesRepository.findByName("ROLE_USER");
		users.setRoles(roles);
		users.setPassword(passwordEncoder.encode(users.getPassword()));
		return usersRepository.save(users);

	}
	
	public Users updateUsers(Users users) {
		return usersRepository.save(users);
	}

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the users
	 */
	public Users findByUsername(String username) {
		return usersRepository.findByUsername(username);
	}

	/**
	 * Find by username and password.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the users
	 */
	public Users findByUsernameAndPassword(String username, String password) {
		Users users = findByUsername(username);
		if (users != null) {
			if(passwordEncoder.matches(password, users.getPassword())) {
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
		Optional<Users> userOptional= Optional.ofNullable(usersRepository.findByEmail(email));
		
		if(!userOptional.isPresent()) {
			return null;
			
		}
		
		Users users = userOptional.get();
		
		users.setForgotPasswordToken(generateToken());
		users.setTokenCreationDate(LocalDateTime.now());
		
		users = usersRepository.save(users);
		
		return users.getForgotPasswordToken();
	}
	

	/**
	 * Reset password.
	 *
	 * @param token the token
	 * @param password the password
	 * @return the string
	 */
	public String resetPassword(String token, String password) {

		Optional<Users> userOptional = Optional
				.ofNullable(usersRepository.findByForgotPasswordToken(token));

		if (!userOptional.isPresent()) {
			return "Invalid token.";
		}

		LocalDateTime tokenCreationDate = userOptional.get().getTokenCreationDate();

		if (isTokenExpired(tokenCreationDate)) {
			return "Token expired.";

		}

		Users user = userOptional.get();

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

		return token.append(UUID.randomUUID().toString())
				.append(UUID.randomUUID().toString()).toString();
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
	
}

