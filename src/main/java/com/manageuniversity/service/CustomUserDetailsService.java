package com.manageuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.manageuniversity.config.jwt.CustomUserDetails;
import com.manageuniversity.entity.Users;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UsersService userService;

	@Override
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users users = userService.findByUsername(username);
		return CustomUserDetails.fromUserEntityToCustomUserDetails(users);
	}
	

}
