package com.manageuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Users;
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
	Users findByUsername(String username);
	
	Users findByEmail(String email);
	
	Users findByForgotPasswordToken(String token);

}
