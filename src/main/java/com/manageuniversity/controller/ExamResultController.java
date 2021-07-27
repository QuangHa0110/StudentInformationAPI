package com.manageuniversity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.dto.ExamResultDTO;
import com.manageuniversity.entity.ExamResult;
import com.manageuniversity.mapper.ExamResultMapper;
import com.manageuniversity.repository.specification.ExamResultSpecification;
import com.manageuniversity.service.ExamResultService;

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
@RequestMapping("/api/v1/exam_result")
public class ExamResultController {

	private ExamResultService examResultsService;
	private ExamResultMapper examResultMapper;

	public ExamResultController(ExamResultService examResultsService, ExamResultMapper examResultMapper) {
		this.examResultsService = examResultsService;
		this.examResultMapper = examResultMapper;
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<ExamResultDTO> findAll(@RequestParam(required = false) List<List<String>> requestList,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request

	) {
		if (requestList == null) {
			return examResultMapper.listExamResultToListExamResultDTO(
					examResultsService.findAll(GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		} else {
			ExamResultSpecification specification = new ExamResultSpecification(requestList,
					request.getParameterValues("requestList").length);

			return examResultMapper.listExamResultToListExamResultDTO(examResultsService
					.findAll(specification, GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<ExamResult> findById(@PathVariable(name = "id") Integer id) {
		return examResultsService.findById(id);
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ExamResultDTO createExamResult(@RequestBody ExamResultDTO examResults) {
		return examResultsService.createExamResult(examResults);
	}

	@PutMapping("/{id}")
	// @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<ExamResultDTO> updateExamResult(@PathVariable(name = "id") Integer id,
			@RequestBody ExamResultDTO examResults) {
		return examResultsService.updateExamResult(id, examResults);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteExamResult(@PathVariable(name = "id") Integer id) {
		return examResultsService.deleteExamResult(id);
	}

}
