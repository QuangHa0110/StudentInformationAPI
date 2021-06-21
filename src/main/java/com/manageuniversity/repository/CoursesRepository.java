package com.manageuniversity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Courses;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {
	List<Courses> findByName(String name);
	
	List<Courses> findByType(String type);

}
