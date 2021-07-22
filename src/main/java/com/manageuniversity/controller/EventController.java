package com.manageuniversity.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.manageuniversity.dto.EventDTO;
import com.manageuniversity.entity.Event;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.mapper.EventMapperImpl;
import com.manageuniversity.repository.specification.EventSpecification;
import com.manageuniversity.repository.specification.SearchCriteria;
import com.manageuniversity.repository.specification.SearchOperation;
import com.manageuniversity.service.EventService;

@RestController
@RequestMapping("/api/v1/events")
public class EventController {
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	@Autowired
	private EventService eventsService;

	private final EventMapperImpl eventMapper = new EventMapperImpl();

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<EventDTO> findAll(
			// id
			@RequestParam(name = "id.greaterThan", required = false) Integer gtId,
			@RequestParam(name = "id.greaterThanOrEqual", required = false) Integer gteId,
			@RequestParam(name = "id.lessThan", required = false) Integer ltId,
			@RequestParam(name = "id.lessThanOrEqual", required = false) Integer lteId,
			@RequestParam(name = "id.in", required = false) List<Integer> inId,
			@RequestParam(name = "id.notIn", required = false) List<Integer> notInId,

			// name
			@RequestParam(name = "name.equal", required = false) String name,
			@RequestParam(name = "name.notEqual", required = false) String notName,
			@RequestParam(name = "name.like", required = false) String likeName,
			@RequestParam(name = "name.in", required = false) List<String> inName,
			@RequestParam(name = "name.notIn", required = false) List<String> notInName,

			// status
			@RequestParam(name = "status.equal", required = false) String status,
			@RequestParam(name = "status.notEqual", required = false) String notStatus,
			@RequestParam(name = "status.like", required = false) String likeStatus,
			@RequestParam(name = "status.in", required = false) List<String> inStatus,
			@RequestParam(name = "status.notIn", required = false) List<String> notInStatus,

			// createDate
			@RequestParam(name = "createDate.equal", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date eqCreateDate,
			@RequestParam(name = "createDate.notEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date notEqCreateDate,
			@RequestParam(name = "createDate.greaterThan", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date gtCreateDate,
			@RequestParam(name = "createDate.greaterThanOrEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date gteCreateDate,
			@RequestParam(name = "createDate.lessThan", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date ltCreateDate,
			@RequestParam(name = "createDate.lessThanOrEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date lteCreateDate,
			@RequestParam(name = "createDate.in", required = false) @DateTimeFormat(pattern = DATE_FORMAT) List<Date> inCreateDate,
			@RequestParam(name = "createDate.notIn", required = false) @DateTimeFormat(pattern = DATE_FORMAT) List<Date> notInCreateDate,

			// happenDate
			@RequestParam(name = "happenDate.equal", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date eqHappenDate,
			@RequestParam(name = "happenDate.notEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date notEqHappenDate,
			@RequestParam(name = "happenDate.greaterThan", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date gtHappenDate,
			@RequestParam(name = "happenDate.greaterThanOrEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date gteHappenDate,
			@RequestParam(name = "happenDate.lessThan", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date ltHappenDate,
			@RequestParam(name = "happenDate.lessThanOrEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date lteHappenDate,
			@RequestParam(name = "happenDate.in", required = false) @DateTimeFormat(pattern = DATE_FORMAT) List<Date> inHappenDate,
			@RequestParam(name = "happenDate.notIn", required = false) @DateTimeFormat(pattern = DATE_FORMAT) List<Date> notInHappenDate,

			// classId
			@RequestParam(name = "classId.equal", required = false) Integer eqClassId,
			@RequestParam(name = "classId.notEqual", required = false) Integer notEqClassId,
			@RequestParam(name = "classId.greaterThan", required = false) Integer gtClassId,
			@RequestParam(name = "classId.greaterThanOrEqual", required = false) Integer gteClassId,
			@RequestParam(name = "classId.lessThan", required = false) Integer ltClassId,
			@RequestParam(name = "classId.lessThanOrEqual", required = false) Integer lteClassId,
			@RequestParam(name = "classId.in", required = false) List<Integer> inClassId,
			@RequestParam(name = "classId.notIn", required = false) List<Integer> notInClassId,

			// className
			@RequestParam(name = "className.equal", required = false) String eqClassName,
			@RequestParam(name = "className.notEqual", required = false) String notEqClassName,
			@RequestParam(name = "className.like", required = false) String likeClassName,
			@RequestParam(name = "className.in", required = false) List<String> inClassName,
			@RequestParam(name = "className.notIn", required = false) List<String> notInClassName,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request
			) {

		EventSpecification specification = new EventSpecification();
		Set<String> keySet = request.getParameterMap().keySet();
		for (String s : keySet) {

			if (!s.equals("page-number") && !s.equals("page-size") && !s.equals("sort-by")
					&& request.getParameterValues(s) != null) {
				String[] nameSplit = s.replace('.', ' ').split(" ");

				switch (nameSplit[1]) {
				case "equal":

					specification.add(new SearchCriteria(nameSplit[0], SearchOperation.EQUAL, request.getParameter(s)));
					break;
				case "notEqual":
					specification
							.add(new SearchCriteria(nameSplit[0], SearchOperation.NOT_EQUAL, request.getParameter(s)));
					break;
				case "greaterThan":
					specification.add(
							new SearchCriteria(nameSplit[0], SearchOperation.GREATER_THAN, request.getParameter(s)));
					break;
				case "greaterThanOrEqual":
					specification.add(new SearchCriteria(nameSplit[0], SearchOperation.GREATER_THAN_EQUAL,
							request.getParameter(s)));
					break;
				case "lessThan":
					specification
							.add(new SearchCriteria(nameSplit[0], SearchOperation.LESS_THAN, request.getParameter(s)));
					break;
				case "lessThanOrEqual":
					specification.add(
							new SearchCriteria(nameSplit[0], SearchOperation.LESS_THAN_EQUAL, request.getParameter(s)));
					break;
				case "like":
					specification.add(new SearchCriteria(nameSplit[0], SearchOperation.LIKE, request.getParameter(s)));
					break;
				case "in":
					specification.add(
							new SearchCriteria(nameSplit[0], SearchOperation.IN, request.getParameterMap().get(s)));
					break;
				case "notIn":
					specification.add(
							new SearchCriteria(nameSplit[0], SearchOperation.NOT_IN, request.getParameterMap().get(s)));
				default:
					break;
				}
			}

		}

		List<Order> orders = new ArrayList<Order>();
		for (String sortBy : sort) {
			if (sortBy.charAt(0) == '+') {
				Order order = new Order(Sort.Direction.ASC, sortBy.substring(1));
				orders.add(order);

			} else if (sortBy.charAt(0) == '-') {
				Order order = new Order(Sort.Direction.DESC, sortBy.substring(1));
				orders.add(order);
			} else {
				throw new BadRequestException("Parameter incorrect");
			}
		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orders));

		return eventMapper.listEventToListEventDTO(eventsService.findAll(specification, pageable).toList());

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
