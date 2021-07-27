package com.manageuniversity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.dto.CourseDTO;
import com.manageuniversity.entity.Course;
import com.manageuniversity.mapper.CourseMapper;
import com.manageuniversity.repository.specification.CourseSpecification;
import com.manageuniversity.service.CourseService;

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
@RequestMapping("/api/v1/courses")
public class CourseController {

	private CourseService coursesService;
	private CourseMapper courseMapper;

	public CourseController(CourseService coursesService, CourseMapper courseMapper) {
		this.coursesService = coursesService;
		this.courseMapper = courseMapper;
	}

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<CourseDTO> findAll(@RequestParam(required = false) List<List<String>> requestList,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request

	) {
		if (requestList == null) {
			return courseMapper.listCourseToListCourseDTO(
					coursesService.findAll(GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		} else {
			CourseSpecification specification = new CourseSpecification(requestList,
					request.getParameterValues("requestList").length);

			return courseMapper.listCourseToListCourseDTO(coursesService
					.findAll(specification, GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Course> findById(@PathVariable(name = "id") Integer id) {
		return coursesService.findById(id);
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public Course createCourse(@RequestBody CourseDTO coursesDTO) {
		return coursesService.createCourse(coursesDTO);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<CourseDTO> updateCourse(@PathVariable(name = "id") Integer id,
			@RequestBody CourseDTO coursesDTO) {
		return coursesService.updateCourse(id, coursesDTO);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCourse(@PathVariable(name = "id") Integer id) {
		return coursesService.deleteCourse(id);
	}

}
