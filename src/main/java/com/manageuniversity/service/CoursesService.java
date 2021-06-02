package com.manageuniversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.entity.Courses;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.CoursesRepository;

@Service
public class CoursesService {
	@Autowired
	private CoursesRepository coursesRepository;

	public List<Courses> findAll() {
		return coursesRepository.findAll();
	}

	public ResponseEntity<Courses> findById(int id) {
		Courses courses = coursesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
		return ResponseEntity.ok().body(courses);
	}

	public Courses createCourse(Courses courses) {
		return coursesRepository.save(courses);
	}

	public ResponseEntity<Courses> updateCourse(int id, Courses courses) {
		Courses courses2 = coursesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
		courses.setId(courses2.getId());
		coursesRepository.save(courses);
		return ResponseEntity.ok().body(courses);
	}
	
	public ResponseEntity<String> deleteCourse(int id){
		Courses courses = coursesRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Course not found with id: "+ id));
		
		coursesRepository.delete(courses);
		return ResponseEntity.ok().body("Course deleted with success");
	}

}
