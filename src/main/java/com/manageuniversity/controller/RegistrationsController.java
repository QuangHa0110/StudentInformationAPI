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

import com.manageuniversity.entity.Registrations;
import com.manageuniversity.entity.RegistrationsId;
import com.manageuniversity.service.RegistrationsService;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationsController {
	@Autowired
	private RegistrationsService registrationsService;
	
	@GetMapping("/")
	public List<Registrations> findAll(){
		return registrationsService.findAll();
	}
	@GetMapping("")
	public List<Registrations> findPaginated(@RequestParam(name = "pageNumber") int pageNumber, @RequestParam(name = "pageSize") int pageSize){
		return registrationsService.findPaginated(pageNumber, pageSize);
	}
	
	@GetMapping("/{classId}/{studentId}")
	public ResponseEntity<Registrations> findById(@PathVariable(name = "classId") Integer classId, @PathVariable(name = "studentId") Integer studentId){
		RegistrationsId registrationsId = new RegistrationsId(studentId, classId);
		return registrationsService.findByRegistrationsId(registrationsId);
	}
	
	
	@PostMapping("/")
	public Registrations createRegistration(@RequestBody Registrations registrations) {	
		return registrationsService.createRegistration(registrations);
	}
	
	@PutMapping("/")
	public ResponseEntity<Registrations> updateRegistration(@RequestParam(name = "classId") Integer classId,@RequestParam(name = "studentId") Integer studentId, @RequestBody Registrations registrations){
		RegistrationsId registrationsId = new RegistrationsId(studentId, classId);
		return registrationsService.updateRegistration(registrationsId, registrations);
	}
	
	@DeleteMapping("/")
	public ResponseEntity<String> deleteRegistration(@RequestParam(name = "classId") Integer classId, @RequestParam(name = "studentId") Integer studentId){
		RegistrationsId registrationsId = new RegistrationsId(studentId, classId);
		return registrationsService.deleteRegistration(registrationsId);
				
	}
	
	@GetMapping("/class{classId}")
	public List<Registrations> findByClassId(@PathVariable(name = "classId") Integer classId){
		return registrationsService.findByClassId(classId);
	}
	
	@GetMapping("/student{studentId}")
	public List<Registrations> findByStudentId(@PathVariable(name = "studentId") Integer studentId){
		return registrationsService.findByStudentId(studentId);
	}
}
