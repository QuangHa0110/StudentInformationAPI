package com.manageuniversity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.dto.TeacherDTO;
import com.manageuniversity.entity.Teacher;
import com.manageuniversity.mapper.TeacherMapper;
import com.manageuniversity.repository.specification.TeacherSpecification;
import com.manageuniversity.service.TeacherService;

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
@RequestMapping("/api/v1/teachers")
public class TeacherController {

	private TeacherService teachersService;
	private TeacherMapper teacherMapper;

	public TeacherController(TeacherService teachersService, TeacherMapper teacherMapper) {
		this.teachersService = teachersService;
		this.teacherMapper = teacherMapper;
	}

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<TeacherDTO> findAlll(@RequestParam(required = false) List<List<String>> requestList,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request

	) {
		if (requestList == null) {
			return teacherMapper.listTeacherToListTeacherDTO(
					teachersService.findAll(GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		} else {
			TeacherSpecification specification = new TeacherSpecification(requestList,
					request.getParameterValues("requestList").length);

			return teacherMapper.listTeacherToListTeacherDTO(teachersService
					.findAll(specification, GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Teacher> findById(@PathVariable(name = "id", required = true) Integer id) {

		return teachersService.findById(id);
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public Teacher createTeacher(@RequestBody TeacherDTO teachers) {
		return teachersService.createTeacher(teachers);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Teacher> updateTeacher(@PathVariable(name = "id", required = true) Integer id,
			@RequestBody TeacherDTO teachers) {
		return teachersService.updateTeacher(id, teachers);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteTeacher(@PathVariable(name = "id", required = true) Integer id) {
		return teachersService.deleteTeacher(id);
	}

}
