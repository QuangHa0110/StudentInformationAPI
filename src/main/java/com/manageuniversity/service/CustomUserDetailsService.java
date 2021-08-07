package com.manageuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.manageuniversity.config.jwt.CustomUserDetails;
import com.manageuniversity.entity.User;

@Component
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	@Cacheable(cacheNames = "user", key = "#username")
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User users = userService.findByUsername(username);
		return CustomUserDetails.createCustomUserDetails(users);
	}
	

}
