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

import com.manageuniversity.entity.Teachers;
import com.manageuniversity.service.TeachersService;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeachersController {
	@Autowired
	private TeachersService teachersService;
	
	@GetMapping("/")
	public List<Teachers> findAlll(){
		return teachersService.findAll();
	}
	@GetMapping("")
	public List<Teachers> findPaginated(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize){
		return teachersService.findPaginated(pageNumber, pageSize);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Teachers> findById(@PathVariable(name = "id", required = true) Integer id){
		
		return teachersService.findById(id);
	}
	
	@PostMapping("/")
	public Teachers createTeacher(@RequestBody Teachers teachers){
		return teachersService.createTeacher(teachers);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Teachers> updateTeacher(@PathVariable(name = "id", required =  true) Integer id, @RequestBody Teachers teachers){
		return teachersService.updateTeacher(id, teachers);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteTeacher(@PathVariable(name = "id", required = true) Integer id){
		return teachersService.deleteTeacher(id);
	}
	
	

}
