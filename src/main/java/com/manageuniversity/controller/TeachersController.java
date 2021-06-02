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

import com.manageuniversity.entity.Teachers;
import com.manageuniversity.service.TeachersService;

@RestController
@RequestMapping("/api/v1")
public class TeachersController {
	@Autowired
	private TeachersService teachersService;
	
	@GetMapping("/teachers")
	public List<Teachers> findAlll(){
		return teachersService.findAll();
	}
	
	@GetMapping("/teachers/{id}")
	public ResponseEntity<Teachers> findById(@PathVariable(name = "id", required = true) int id){
		
		return teachersService.findById(id);
	}
	
	@PostMapping("/teachers")
	public Teachers createTeacher(@RequestBody Teachers teachers){
		return teachersService.createTeacher(teachers);
	}
	
	@PutMapping("/teachers/{id}")
	public ResponseEntity<Teachers> updateTeacher(@PathVariable(name = "id", required =  true) int id, @RequestBody Teachers teachers){
		return teachersService.updateTeacher(id, teachers);
	}
	
	@DeleteMapping("/teachers/{id}")
	public ResponseEntity<String> deleteTeacher(@PathVariable(name = "id", required = true) int id){
		return teachersService.deleteTeacher(id);
	}
	
	

}
