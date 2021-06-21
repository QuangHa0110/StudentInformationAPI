package com.manageuniversity.config.jwt;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.manageuniversity.entity.Users;

public class CustomUserDetails implements UserDetails {
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> grantedAuthorities;
	
	public static CustomUserDetails fromUserEntityToCustomUserDetails(Users users) {
		CustomUserDetails customUserDetails = new CustomUserDetails();
		customUserDetails.username = users.getUsername();
		customUserDetails.password = users.getPassword();
		customUserDetails.grantedAuthorities = Collections.singletonList(new SimpleGrantedAuthority(users.getRoles().getName()));
		return customUserDetails;
	}
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true	;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
