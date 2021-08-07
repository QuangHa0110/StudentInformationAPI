package com.manageuniversity.config.jwt;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.manageuniversity.entity.Role;
import com.manageuniversity.entity.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> roles;
	private Collection<? extends GrantedAuthority> permissions;

	public static CustomUserDetails createCustomUserDetails(User user) {
		if (user != null) {
			List<GrantedAuthority> roles = user.getRoles().stream().filter(Objects::nonNull)
					.map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
			List<GrantedAuthority> permissions = user.getRoles().stream().filter(Objects::nonNull)
					.map(Role::getPermissions).flatMap(Collection::stream)
					.map(permission -> new SimpleGrantedAuthority(permission.getName())).collect(Collectors.toList());

			CustomUserDetails customUserDetails = new CustomUserDetails();
			System.out.println("jwt*************");
			customUserDetails.username = user.getUsername();
			customUserDetails.password = user.getPassword();
			customUserDetails.roles = roles;
			customUserDetails.permissions = permissions;
			return customUserDetails;

		}
		return null;

	}

	@Override
	public String getPassword() {

		return password;
	}

	@Override
	public String getUsername() {

		return username;
	}

	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}

	public void setRoles(Collection<? extends GrantedAuthority> roles) {
		this.roles = roles;
	}

	public Collection<? extends GrantedAuthority> getPermissions() {
		return permissions;
	}

	public void setPermissions(Collection<? extends GrantedAuthority> permissions) {
		this.permissions = permissions;
	}

}
