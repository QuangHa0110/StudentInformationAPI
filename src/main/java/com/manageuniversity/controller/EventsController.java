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

import com.manageuniversity.entity.Events;
import com.manageuniversity.service.EventsService;

@RestController
@RequestMapping("/api/v1/events")
public class EventsController {
	@Autowired
	private EventsService eventsService;
	
	@GetMapping("/")
	public List<Events> findAll(){
		return eventsService.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Events> findById(@PathVariable(name = "id") int id){
		return eventsService.findById(id);
	}
	@GetMapping("/class_id/{classId}")
	public List<Events> getListEventsByClassId(@PathVariable(name = "classId") int classId){
		return eventsService.getListEventsByClassId(classId);
	}
	@GetMapping("/happen_date/{happenDate}")
	public List<Events> findByHappenDate(@PathVariable(name = "happenDate") String happenDate){
		return eventsService.findByHappenDate(happenDate);
				
	}
	@PostMapping("/")
	public Events createCourse(@RequestBody Events events) {
		return eventsService.createCourse(events);
	}
	@PutMapping("/{id}")
	public ResponseEntity<Events> updateCourse(@PathVariable(name = "id")int id, @RequestBody Events events){
		return eventsService.updateCourse(id, events);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCourse(@PathVariable(name = "id")int id){
		return eventsService.deleteCourse(id);
	}

}
