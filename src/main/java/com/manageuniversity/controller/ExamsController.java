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

import com.manageuniversity.entity.Exams;
import com.manageuniversity.service.ExamsService;

@RestController
@RequestMapping("/api/v1/exams")
public class ExamsController {
	@Autowired
	private ExamsService examsService;
	
	@GetMapping("/")
	public List<Exams> findAll(){
		return examsService.findAll();
		
	}
	@GetMapping("")
	public List<Exams> findPaginated(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize){
		return examsService.findPaginated(pageNumber, pageSize);
				
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Exams> findById(@PathVariable(name = "id") Integer id){
		return examsService.findById(id);
	}

	
	@PostMapping("/")
	public Exams createExam(@RequestBody Exams exams) {
		return examsService.createExam(exams);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Exams> updateExam(@PathVariable(name = "id") Integer id, @RequestBody Exams exams){
		return examsService.updateExam(id, exams);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteExam(@PathVariable(name = "id") Integer id){
		return examsService.deleteExam(id);
	}
}
