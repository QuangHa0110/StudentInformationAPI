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

import com.manageuniversity.entity.Plans;
import com.manageuniversity.service.PlansService;

@RestController
@RequestMapping("/api/v1/plans")
public class PlansController {
	@Autowired
	private PlansService plansService;
	
	@GetMapping("/")
	public List<Plans> findAll(){
		return plansService.findAll();
	}
	@GetMapping("")
	public List<Plans> findPaginated(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize){
		return plansService.findPaginated(pageNumber, pageSize);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Plans> findById(@PathVariable(name = "id") Integer id){
		return plansService.findById(id);
	}
	
	@PostMapping("/")
	public Plans createPlan(@RequestBody Plans plans) {
		return plansService.createPlan(plans);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Plans> updatePlan(@PathVariable(name = "id") Integer id, @RequestBody Plans plans){
		return plansService.updatePlan(id, plans);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePlan(@PathVariable(name = "id") Integer id){
		return plansService.deletePlan(id);
	}

}
