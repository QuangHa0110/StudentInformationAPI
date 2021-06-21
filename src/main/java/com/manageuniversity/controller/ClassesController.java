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
@RequestMapping("/api/v1/classes")
public class ClassesController {
	@Autowired
	private ClassesService classesService;
	
	@GetMapping("/")
	public List<Classes> findAll(){
		return classesService.findAll();
	}
	
	@GetMapping("")
	public List<Classes> findPaginated(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize){
		return classesService.findPaginated(pageNumber, pageSize);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Classes> findById(@PathVariable(name = "id", required = true) Integer id){
		return classesService.findById(id);
	}
	
	@GetMapping("/course_id/{course_id}")
	public List<Classes> findByCoursesId(@PathVariable(name = "course_id", required = true) Integer course_id){
		return classesService.findByCourseId(course_id);
	}
	
	@GetMapping("/course_name/{course_name}")
	public List<Classes> findByCourseName(@PathVariable(name = "course_name", required = true) String course_name)
	{
		return classesService.findByCourseName(course_name);
	}
	
	@GetMapping("/teacher_name/{teacher_name}")
	public List<Classes> findByTeacherName(@PathVariable(name = "teacher_name", required = true) String teacher_name){
		return classesService.findByTeacherName(teacher_name);
	}
	
	
	
	@PostMapping("/")
	public Classes createClass(@RequestBody Classes classes) {
		
		return classesService.createClass(classes);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Classes> updateClass(@PathVariable(name = "id", required = true) Integer id, @RequestBody Classes classes){
		return classesService.updateClass(id, classes);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteClass(@PathVariable(name = "id", required = true) Integer id){
		return classesService.deleteClass(id);
	}
	
	
	
}
