package com.manageuniversity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.dto.RegistrationDTO;
import com.manageuniversity.entity.Registration;
import com.manageuniversity.entity.RegistrationId;
import com.manageuniversity.mapper.RegistrationMapper;
import com.manageuniversity.repository.specification.RegistrationSpecification;
import com.manageuniversity.service.RegistrationService;

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

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController {

	private RegistrationService registrationsService;
	private RegistrationMapper registrationMapper;

	public RegistrationController(RegistrationService registrationsService, RegistrationMapper registrationMapper) {
		this.registrationsService = registrationsService;
		this.registrationMapper = registrationMapper;
	}

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<RegistrationDTO> findAll(@RequestParam(required = false) List<List<String>> requestList,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false) String[] sort, HttpServletRequest request) {
		if (requestList == null) {
			return registrationMapper.listRegistrationToListRegistrationDTO(
					registrationsService.findAll(GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		} else {
			RegistrationSpecification specification = new RegistrationSpecification(requestList,
					request.getParameterValues("requestList").length);

			return registrationMapper.listRegistrationToListRegistrationDTO(registrationsService
					.findAll(specification, GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		}

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
