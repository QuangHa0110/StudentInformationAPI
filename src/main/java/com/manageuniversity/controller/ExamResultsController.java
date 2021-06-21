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

import com.manageuniversity.entity.ExamResults;
import com.manageuniversity.service.ExamResultsService;

@RestController
@RequestMapping("/api/v1/exam_result")
public class ExamResultsController {
	@Autowired
	private ExamResultsService examResultsService;
	
	
	@GetMapping("/")
	public List<ExamResults> findAll(){
		return examResultsService.findAll();
	}
	@GetMapping("")
	public List<ExamResults> findPaginated(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize){
		return examResultsService.findPaginated(pageNumber, pageSize);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ExamResults> findById(@PathVariable(name = "id") Integer id){
		return examResultsService.findById(id);
	}
	
	@PostMapping("/")
	public ExamResults createExamResult(@RequestBody ExamResults examResults) {
		return examResultsService.createExamResult(examResults);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ExamResults> updateExamResult(@PathVariable(name = "id") Integer id, @RequestBody ExamResults examResults){
		return examResultsService.updateExamResult(id, examResults);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteExamResult(@PathVariable(name = "id") Integer id){
		return examResultsService.deleteExamResult(id);
	}

}
