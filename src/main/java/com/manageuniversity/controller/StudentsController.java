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

import com.manageuniversity.entity.Students;
import com.manageuniversity.service.StudentsService;

@RestController
@RequestMapping("/api/v1")
public class StudentsController {
	@Autowired
	private StudentsService studentsService;
	
	@GetMapping("/students")
	public List<Students> findAll(){
		return studentsService.findAll();
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<Students> findById(@PathVariable(name = "id") int id) {
				return studentsService.findById(id);
		
	}
	@GetMapping("/student")
	public List<Students> findByFullName(@RequestParam(required = true) String fullname) {
		return studentsService.findByFullName(fullname);
	}
	
	@PostMapping("/students")
	public Students createStudent( @RequestBody Students students) {
		return studentsService.addStudent(students);
	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<Students> updateStudent(@PathVariable(name = "id")  int id, @RequestBody Students students){
		
		return studentsService.updateStudent(id, students);
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable(name = "id") int id){
		return studentsService.deleteStudent(id);
	}

}
