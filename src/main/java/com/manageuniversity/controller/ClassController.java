package com.manageuniversity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.dto.ClassDTO;
import com.manageuniversity.entity.Class;
import com.manageuniversity.mapper.ClassMapper;
import com.manageuniversity.repository.specification.ClassSpecification;
import com.manageuniversity.service.ClassService;

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

import io.swagger.annotations.ApiImplicitParam;

@RestController
@RequestMapping("/api/v1/classes")

public class ClassController {

	private final ClassService classesService;

	private final ClassMapper classMapper;

	public ClassController(final ClassService classService, ClassMapper classMapper) {
		this.classesService = classService;
		this.classMapper = classMapper;

	}

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	@ApiImplicitParam(allowMultiple = true, name = "requestList")
	public List<ClassDTO> findAll(

			@RequestParam(required = false) List<List<String>> requestList,

			// Pageable pageable
			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,

			HttpServletRequest request

	) {
		if (requestList == null) {
			return classMapper.listClassToListClassDTO(
					classesService.findAll(GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		} else {
			ClassSpecification specification = new ClassSpecification(requestList,
					request.getParameterValues("requestList").length);

			return classMapper.listClassToListClassDTO(classesService
					.findAll(specification, GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Class> findById(@PathVariable(name = "id", required = true) Integer id) {
		return classesService.findById(id);
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public Class createClass(@RequestBody ClassDTO classesDTO) {
		classesDTO.setId(null);

		return classesService.createClass(classesDTO);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<ClassDTO> updateClass(@PathVariable(name = "id", required = true) Integer id,
			@RequestBody ClassDTO classesDTO) {
		classesDTO.setId(null);

		return classesService.updateClass(id, classesDTO);

	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteClass(@PathVariable(name = "id", required = true) Integer id) {
		return classesService.deleteClass(id);
	}

}
