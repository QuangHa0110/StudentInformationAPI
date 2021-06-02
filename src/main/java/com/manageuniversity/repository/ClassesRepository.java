package com.manageuniversity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Classes;
import com.manageuniversity.entity.Courses;

@Repository
public interface ClassesRepository extends JpaRepository<Classes, Integer> {
	
	@Query(value =  "FROM Classes c WHERE c.courses.id = ?1")
	List<Classes> getListClassByCourseId(int courseId);
	
	@Query(value = "FROM Classes c WHERE c.courses.name = ?1  ")
	List<Classes> getListClassByCourseName(String courseName);
	
	@Query(value = "FROM Classes c WHERE c.teachers.fullName = ?1")
	List<Classes> getListClassByTeacherName(String teacherName);

}
