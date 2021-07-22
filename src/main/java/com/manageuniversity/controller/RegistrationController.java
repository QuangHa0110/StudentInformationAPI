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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manageuniversity.dto.RegistrationDTO;
import com.manageuniversity.entity.Registration;
import com.manageuniversity.entity.RegistrationId;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.mapper.RegistrationMapperImpl;
import com.manageuniversity.repository.specification.RegistrationSpecification;
import com.manageuniversity.repository.specification.SearchCriteria;
import com.manageuniversity.repository.specification.SearchOperation;
import com.manageuniversity.service.RegistrationService;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController {
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	@Autowired
	private RegistrationService registrationsService;

	private final RegistrationMapperImpl registrationMapper = new RegistrationMapperImpl();

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<RegistrationDTO> findAll(
			// studentId
			@RequestParam(name = "studentId.equal", required = false) Integer eqStudentId,
			@RequestParam(name = "studentId.notEqual", required = false) Integer notEqStudentId,
			@RequestParam(name = "studentId.greaterThan", required = false) Integer gtStudentId,
			@RequestParam(name = "studentId.greaterThanOrEqual", required = false) Integer gteStudentId,
			@RequestParam(name = "studentId.lessThan", required = false) Integer ltStudentId,
			@RequestParam(name = "studentId.lessThanOrEqual", required = false) Integer lteStudentId,
			@RequestParam(name = "studentId.in", required = false) List<Integer> inStudentId,
			@RequestParam(name = "studentId.notIn", required = false) List<Integer> notInStudentId,

			// studentName
			@RequestParam(name = "studentName.equal", required = false) String eqStudentName,
			@RequestParam(name = "studentName.notEqual", required = false) String notEqStudentName,
			@RequestParam(name = "studentName.like", required = false) String likeStudentName,
			@RequestParam(name = "studentName.in", required = false) List<String> inStudentName,
			@RequestParam(name = "studentName.notIn", required = false) List<String> notInStudentName,

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

			// createDate
			@RequestParam(name = "createDate.equal", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date eqCreateDate,
			@RequestParam(name = "createDate.notEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date notEqCreateDate,
			@RequestParam(name = "createDate.greaterThan", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date gtCreateDate,
			@RequestParam(name = "createDate.greaterThanOrEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date gteCreateDate,
			@RequestParam(name = "createDate.lessThan", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date ltCreateDate,
			@RequestParam(name = "createDate.lessThanOrEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date lteCreateDate,
			@RequestParam(name = "createDate.in", required = false) @DateTimeFormat(pattern = DATE_FORMAT) List<Date> inCreateDate,
			@RequestParam(name = "createDate.notIn", required = false) @DateTimeFormat(pattern = DATE_FORMAT) List<Date> notInCreateDate,

			// status
			@RequestParam(name = "status.equal", required = false) String status,
			@RequestParam(name = "status.notEqual", required = false) String notStatus,
			@RequestParam(name = "status.like", required = false) String likeStatus,
			@RequestParam(name = "status.in", required = false) List<String> inStatus,
			@RequestParam(name = "status.notIn", required = false) List<String> notInStatus,
			// registerDay
			@RequestParam(name = "registerDay.equal", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date eqRegisterDay,
			@RequestParam(name = "registerDay.notEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date notEqRegisterDay,
			@RequestParam(name = "registerDay.greaterThan", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date gtRegisterDay,
			@RequestParam(name = "registerDay.greaterThanOrEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date gteRegisterDay,
			@RequestParam(name = "registerDay.lessThan", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date ltRegisterDay,
			@RequestParam(name = "registerDay.lessThanOrEqual", required = false) @DateTimeFormat(pattern = DATE_FORMAT) Date lteRegisterDay,
			@RequestParam(name = "registerDay.in", required = false) @DateTimeFormat(pattern = DATE_FORMAT) List<Date> inRegisterDay,
			@RequestParam(name = "registerDay.notIn", required = false) @DateTimeFormat(pattern = DATE_FORMAT) List<Date> notInRegisterDay,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false) String[] sort, HttpServletRequest request) {

		RegistrationSpecification specification = new RegistrationSpecification();
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
		if (sort != null) {
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

		}

		Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orders));

		return registrationMapper
				.listRegistrationToListRegistrationDTO(registrationsService.findAll(specification, pageable).toList());

	}

	

	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Registration> findById(@RequestParam(name = "classId") Integer classId,
			@RequestParam(name = "studentId") Integer studentId) {
		RegistrationId registrationsId = new RegistrationId(studentId, classId);
		return registrationsService.findByRegistrationsId(registrationsId);
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public Registration createRegistration(@RequestBody RegistrationDTO registrations) {
		return registrationsService.createRegistration(registrations);
	}

	@PutMapping("/")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Registration> updateRegistration(@RequestParam(name = "classId") Integer classId,
			@RequestParam(name = "studentId") Integer studentId, @RequestBody RegistrationDTO registrations) {
		RegistrationId registrationsId = new RegistrationId(studentId, classId);
		return registrationsService.updateRegistration(registrationsId, registrations);
	}

	@DeleteMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteRegistration(@RequestParam(name = "classId") Integer classId,
			@RequestParam(name = "studentId") Integer studentId) {
		RegistrationId registrationsId = new RegistrationId(studentId, classId);
		return registrationsService.deleteRegistration(registrationsId);

	}

}
