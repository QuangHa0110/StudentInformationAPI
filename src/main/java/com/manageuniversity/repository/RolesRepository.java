package com.manageuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Roles;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer> {
	
	Roles findByName(String name);
	

}
