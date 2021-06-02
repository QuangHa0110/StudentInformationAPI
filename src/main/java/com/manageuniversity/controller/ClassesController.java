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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manageuniversity.entity.Classes;
import com.manageuniversity.entity.Courses;
import com.manageuniversity.service.ClassesService;

@RestController
@RequestMapping("/api/v1")
public class ClassesController {
	@Autowired
	private ClassesService classesService;
	
	@GetMapping("/classes")
	public List<Classes> findAll(){
		return classesService.findAll();
	}
	
	@GetMapping("/classes/{id}")
	public ResponseEntity<Classes> findById(@PathVariable(name = "id", required = true) int id){
		return classesService.findById(id);
	}
	
	@GetMapping("/classes/course_id/{course_id}")
	public List<Classes> findByCoursesId(@PathVariable(name = "course_id", required = true) int course_id){
		return classesService.findByCourseId(course_id);
	}
	
	@GetMapping("/classes/course_name/{course_name}")
	public List<Classes> findByCourseName(@PathVariable(name = "course_name", required = true) String course_name)
	{
		return classesService.findByCourseName(course_name);
	}
	
	@GetMapping("/classes/teacher_name/{teacher_name}")
	public List<Classes> findByTeacherName(@PathVariable(name = "teacher_name", required = true) String teacher_name){
		return classesService.findByTeacherName(teacher_name);
	}
	
	
	
	@PostMapping("/classes")
	public Classes createClass(@RequestBody Classes classes) {
	
		return classesService.createClass(classes);
	}
	
	@PutMapping("/classes/{id}")
	public ResponseEntity<Classes> updateClass(@PathVariable(name = "id", required = true) int id, @RequestBody Classes classes){
		return classesService.updateClass(id, classes);
	}
	
	@DeleteMapping("/classes/{id}")
	public ResponseEntity<String> deleteClass(@PathVariable(name = "id", required = true) int id){
		return classesService.deleteClass(id);
	}
	
	
	
}
