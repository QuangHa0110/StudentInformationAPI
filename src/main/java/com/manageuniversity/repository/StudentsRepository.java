package com.manageuniversity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Students;

@Repository 
public interface StudentsRepository extends JpaRepository<Students, Integer> {
	
	List<Students> findByFullName(String fullname);
	

	

}
