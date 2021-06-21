package com.manageuniversity.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.entity.Events;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.EventsRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class EventsService.
 */
@Service
public class EventsService {
	
	/** The events repository. */
	@Autowired
	private EventsRepository eventsRepository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "eventsAll")
	public List<Events> findAll() {
		return eventsRepository.findAll();
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "events", key = "#id")
	public ResponseEntity<Events> findById(Integer id)  {
		Events event = eventsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
		return ResponseEntity.ok().body(event);
	}

	/**
	 * Creates the course.
	 *
	 * @param event the event
	 * @return the events
	 */
	
	public Events createCourse(Events event) {
		return eventsRepository.save(event);
	}

	/**
	 * Update course.
	 *
	 * @param id the id
	 * @param event the event
	 * @return the response entity
	 */
	@CachePut(cacheNames = "events", key = "#id")
	public ResponseEntity<Events> updateCourse(Integer id, Events event) {
		Events event2 = eventsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found with id: " + id));
		event.setId(event2.getId());
		eventsRepository.save(event);
		return ResponseEntity.ok().body(event);
	}
	
	/**
	 * Delete course.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "events", key = "#id")
	public ResponseEntity<String> deleteCourse(Integer id){
		Events event =	eventsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Event not found with id: "+ id));
		eventsRepository.delete(event);
		return ResponseEntity.ok().body("Event deleted with success");
	}
	
	/**
	 * Find by happen date.
	 *
	 * @param happenDate the happen date
	 * @return the list
	 */
	@Cacheable(cacheNames = "classHappenDate", key = "#happenDate")
	public List<Events> findByHappenDate(Date happenDate){
		return eventsRepository.findByHappenDate(happenDate);
	}
	
	/**
	 * Gets the list events by class id.
	 *
	 * @param classId the class id
	 * @return the list events by class id
	 */
	@Cacheable(cacheNames = "eventClassId", key = "#classId")
	public List<Events> getListEventsByClassId(Integer classId){
		return eventsRepository.getListEventsByClassId(classId);
	}
	
	
	/**
	 * Gets the list events by class name.
	 *
	 * @param className the class name
	 * @return the list events by class name
	 */
	@Cacheable(cacheNames = "eventClassName", key = "#className")
	public List<Events> getListEventsByClassName(String className){
		return eventsRepository.getListEventsByClassName(className);
	}
	
	/**
	 * Find paginated.
	 *
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @return the list
	 */
	@Cacheable(cacheNames = "eventsPage", key = "#pageNumber")
	public List<Events> findPaginated(int pageNumber, int pageSize){
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<Events> pageResutl = eventsRepository.findAll(paging);
		return pageResutl.toList();
	}
	

}
