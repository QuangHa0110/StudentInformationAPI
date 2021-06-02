package com.manageuniversity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manageuniversity.entity.Courses;
import com.manageuniversity.service.CoursesService;

@RestController
@RequestMapping("/api/v1/courses")
public class CoursesController {

	@Autowired
	private CoursesService coursesService;
	
	@GetMapping("/")
	public List<Courses> findAll(){
		return coursesService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Courses> findById(@PathVariable(name = "id") int id){
		return coursesService.findById(id);
	}
	@PostMapping("/")
	public Courses createCourse(@RequestBody Courses courses) {
		return coursesService.createCourse(courses);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Courses> updateCourse(@PathVariable(name = "id")int id, @RequestBody Courses courses){
		return coursesService.updateCourse(id, courses);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCourse(@PathVariable(name = "id")int id){
		return coursesService.deleteCourse(id);
	}
}
