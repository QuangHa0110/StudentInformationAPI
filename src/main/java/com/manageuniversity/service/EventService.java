package com.manageuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.dto.EventDTO;
import com.manageuniversity.entity.Class;
import com.manageuniversity.entity.Event;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.mapper.EventMapperImpl;
import com.manageuniversity.repository.ClassRepository;
import com.manageuniversity.repository.EventRepository;
import com.manageuniversity.repository.specification.EventSpecification;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsService.
 */
@Service
public class EventService {

	/** The events repository. */
	@Autowired
	private EventRepository eventsRepository;

	@Autowired
	private ClassRepository classesRepository;
	
	
	private final EventMapperImpl eventMapper= new EventMapperImpl();

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "eventsAll")
	public Page<Event> findAll(EventSpecification specification, Pageable pageable ) {

		return eventsRepository.findAll(specification,pageable);
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "events", key = "#id")
	public ResponseEntity<Event> findById(Integer id) {
		Event event = eventsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
		return ResponseEntity.ok().body(event);
	}

	/**
	 * Creates the course.
	 *
	 * @param event the event
	 * @return the events
	 */

	public Event createCourse(EventDTO eventsDTO) {
		eventsDTO.setId(null);
		Class classes = classesRepository.findById(eventsDTO.getClassId())
				.orElseThrow(() -> new ResourceNotFoundException("Class no found with id: " + eventsDTO.getClassId()));
		Event event = eventMapper.eventDTOToEvent(eventsDTO);
		event.setClasses(classes);

		return eventsRepository.save(event);
	}

	/**
	 * Update course.
	 *
	 * @param id    the id
	 * @param event the event
	 * @return the response entity
	 */
	@CachePut(cacheNames = "events", key = "#id")
	public ResponseEntity<EventDTO> updateCourse(Integer id, EventDTO eventsDTO) {
		Event event2 = eventsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));

		if (eventsDTO.getCreateDate() != null) {
			event2.setCreateDate(eventsDTO.getCreateDate());
		}
		if (eventsDTO.getClassId() != null) {
			Class classes = classesRepository.findById(eventsDTO.getClassId()).orElseThrow(
					() -> new ResourceNotFoundException("Class no found with id: " + eventsDTO.getClassId()));
			
			event2.setClasses(classes);
		}
		if (eventsDTO.getHappenDate() != null) {
			event2.setHappenDate(eventsDTO.getHappenDate());

		}
		if(eventsDTO.getName() !=null) {
			event2.setName(eventsDTO.getName());
			
		}
		if(eventsDTO.getStatus() != null) {
			event2.setStatus(eventsDTO.getStatus());
		}
		eventsRepository.save(event2);
		
	
		return ResponseEntity.ok().body(eventMapper.eventToEventDTO(event2));
	}

	/**
	 * Delete course.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "events", key = "#id")
	public ResponseEntity<String> deleteCourse(Integer id) {
		Event event = eventsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
		eventsRepository.delete(event);
		return ResponseEntity.ok().body("Event deleted with success");
	}



}
