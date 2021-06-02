package com.manageuniversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.entity.Events;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.EventsRepository;

@Service
public class EventsService {
	@Autowired
	private EventsRepository eventsRepository;
	
	public List<Events> findAll() {
		return eventsRepository.findAll();
	}

	public ResponseEntity<Events> findById(int id)  {
		Events event = eventsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
		return ResponseEntity.ok().body(event);
	}

	public Events createCourse(Events event) {
		return eventsRepository.save(event);
	}

	public ResponseEntity<Events> updateCourse(int id, Events event) {
		Events event2 = eventsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
		event.setId(event2.getId());
		eventsRepository.save(event);
		return ResponseEntity.ok().body(event);
	}
	
	public ResponseEntity<String> deleteCourse(int id){
		Events event =	eventsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Event not found with id: "+ id));
		eventsRepository.delete(event);
		return ResponseEntity.ok().body("Event deleted with success");
	}
	
	public List<Events> findByHappenDate(String happenDate){
		return eventsRepository.findByHappenDate(happenDate);
	}
	
	public List<Events> getListEventsByClassId(int classId){
		return eventsRepository.getListEventsByClassId(classId);
	}

}
