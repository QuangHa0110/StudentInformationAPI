package com.manageuniversity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.dto.EventDTO;
import com.manageuniversity.entity.Event;
import com.manageuniversity.mapper.EventMapper;
import com.manageuniversity.repository.specification.EventSpecification;
import com.manageuniversity.service.EventService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {

	private EventService eventsService;
	private EventMapper eventMapper;

	public EventController(EventService eventsService, EventMapper eventMapper) {
		this.eventsService = eventsService;
		this.eventMapper = eventMapper;
	}

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<EventDTO> findAll(@RequestParam(required = false) List<List<String>> requestList,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request) {

		if (requestList == null) {
			return eventMapper.listEventToListEventDTO(
					eventsService.findAll(GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		} else {
			EventSpecification specification = new EventSpecification(requestList,
					request.getParameterValues("requestList").length);

			return eventMapper.listEventToListEventDTO(eventsService
					.findAll(specification, GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Event> findById(@PathVariable(name = "id") Integer id) {
		return eventsService.findById(id);
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public Event createCourse(@RequestBody EventDTO eventsDTO) {
		return eventsService.createCourse(eventsDTO);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<EventDTO> updateCourse(@PathVariable(name = "id") Integer id,
			@RequestBody EventDTO eventsDTO) {
		return eventsService.updateCourse(id, eventsDTO);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCourse(@PathVariable(name = "id") Integer id) {
		return eventsService.deleteCourse(id);
	}

}
