package com.manageuniversity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.dto.ExamDTO;
import com.manageuniversity.entity.Exam;
import com.manageuniversity.mapper.ExamMapper;
import com.manageuniversity.repository.specification.ExamSpecification;
import com.manageuniversity.service.ExamService;

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
@RequestMapping("/api/v1/exams")
public class ExamController {

	private ExamService examsService;

	private ExamMapper examMapper;

	public ExamController(ExamService examsService, ExamMapper examMapper) {
		this.examsService = examsService;
		this.examMapper = examMapper;
	}

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<ExamDTO> findAll(@RequestParam(required = false) List<List<String>> requestList,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request

	) {
		if (requestList == null) {
			return examMapper.listExamToListExamDTO(
					examsService.findAll(GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		} else {
			ExamSpecification specification = new ExamSpecification(requestList,
					request.getParameterValues("requestList").length);

			return examMapper.listExamToListExamDTO(examsService
					.findAll(specification, GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		}

	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Exam> findById(@PathVariable(name = "id") Integer id) {
		return examsService.findById(id);
	}

	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ExamDTO createExam(@RequestBody ExamDTO exams) {
		return examsService.createExam(exams);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Exam> updateExam(@PathVariable(name = "id") Integer id, @RequestBody ExamDTO exams) {
		return examsService.updateExam(id, exams);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteExam(@PathVariable(name = "id") Integer id) {
		return examsService.deleteExam(id);
	}
}
