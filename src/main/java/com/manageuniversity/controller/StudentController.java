package com.manageuniversity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.dto.StudentDTO;
import com.manageuniversity.entity.Student;
import com.manageuniversity.mapper.StudentMapper;
import com.manageuniversity.repository.specification.StudentSpecification;
import com.manageuniversity.service.StudentService;

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
@RequestMapping("/api/v1/students")
public class StudentController {

	private StudentService studentsService;
	private StudentMapper studentMapper;

	public StudentController(StudentService studentsService, StudentMapper studentMapper) {
		this.studentsService = studentsService;
		this.studentMapper = studentMapper;
	}

	@GetMapping("")
	@PreAuthorize("hasPermission('read_all_student')")
	public List<StudentDTO> findAll(@RequestParam(required = false) List<List<String>> requestList,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request

	) {
		if (requestList == null) {
			return studentMapper.listStudentToListStudentDTO(
					studentsService.findAll(GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());

		} else {
			StudentSpecification specification = new StudentSpecification(requestList,
					request.getParameterValues("requestList").length);

			return studentMapper.listStudentToListStudentDTO(studentsService
					.findAll(specification, GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Student> findById(@PathVariable(name = "id") Integer id) {
		return studentsService.findById(id);

	}

	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public Student createStudent(@RequestBody StudentDTO students) {
		return studentsService.addStudent(students);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Student> updateStudent(@PathVariable(name = "id") Integer id,
			@RequestBody StudentDTO students) {

		return studentsService.updateStudent(id, students);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteStudent(@PathVariable(name = "id") Integer id) {
		return studentsService.deleteStudent(id);
	}

}
