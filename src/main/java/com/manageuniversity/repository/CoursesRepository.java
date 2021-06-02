package com.manageuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Courses;

@Repository
public interface CoursesRepository extends JpaRepository<Courses, Integer> {

}