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

import com.manageuniversity.dto.ExamResultDTO;
import com.manageuniversity.entity.ExamResult;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.mapper.ExamResultMapperImpl;
import com.manageuniversity.repository.specification.ExamResultSpecification;
import com.manageuniversity.repository.specification.SearchCriteria;
import com.manageuniversity.repository.specification.SearchOperation;
import com.manageuniversity.service.ExamResultService;

@RestController
@RequestMapping("/api/v1/exam_result")
public class ExamResultController {
	@Autowired
	private ExamResultService examResultsService;

	private final ExamResultMapperImpl examMapper = new ExamResultMapperImpl();

	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<ExamResultDTO> findAll(
			// id
			@RequestParam(name = "id.greaterThan", required = false) Integer gtId,
			@RequestParam(name = "id.greaterThanOrEqual", required = false) Integer gteId,
			@RequestParam(name = "id.lessThan", required = false) Integer ltId,
			@RequestParam(name = "id.lessThanOrEqual", required = false) Integer lteId,
			@RequestParam(name = "id.in", required = false) List<Integer> inId,
			@RequestParam(name = "id.notIn", required = false) List<Integer> notInId,

			// score
			@RequestParam(name = "score.equal", required = false) Integer eqScore,
			@RequestParam(name = "score.notEqual", required = false) Integer notEqScore,
			@RequestParam(name = "score.greaterThan", required = false) Integer gtScore,
			@RequestParam(name = "score.greaterThanOrEqual", required = false) Integer gteScore,
			@RequestParam(name = "score.lessThan", required = false) Integer ltScore,
			@RequestParam(name = "score.lessThanOrEqual", required = false) Integer lteScore,
			@RequestParam(name = "score.in", required = false) List<Integer> inScore,
			@RequestParam(name = "score.notIn", required = false) List<Integer> notInScore,

			// resultDate
			@RequestParam(name = "resultDate.equal", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date eqResultDate,
			@RequestParam(name = "resultDate.greaterThan", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date gtResultDate,
			@RequestParam(name = "resultDate.greaterThanOrEqual", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date gteResultDate,
			@RequestParam(name = "resultDate.lessThan", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ltResultDate,
			@RequestParam(name = "resultDate.lessThanOrEqual", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lteResultDate,
			@RequestParam(name = "resultDate.notEqual", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date notEqResultDate,
			@RequestParam(name = "resultDate.in", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") List<Date> inResultDate,
			@RequestParam(name = "resultDate.notIn", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") List<Date> notInResultDate,

			// note
			@RequestParam(name = "note.equal", required = false) String eqNote,
			@RequestParam(name = "note.notEqual", required = false) String notEqNote,
			@RequestParam(name = "note.like", required = false) String likeNote,
			@RequestParam(name = "note.in", required = false) List<String> inNote,
			@RequestParam(name = "note.notIn", required = false) List<String> notInNote,

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

			// examId
			@RequestParam(name = "examId.equal", required = false) Integer eqExamId,
			@RequestParam(name = "examId.notEqual", required = false) Integer notEqExamId,
			@RequestParam(name = "examId.greaterThan", required = false) Integer gtExamId,
			@RequestParam(name = "examId.greaterThanOrEqual", required = false) Integer gteExamId,
			@RequestParam(name = "examId.lessThan", required = false) Integer ltExamId,
			@RequestParam(name = "examId.lessThanOrEqual", required = false) Integer lteExamId,
			@RequestParam(name = "examId.in", required = false) List<Integer> inExamId,
			@RequestParam(name = "examId.notIn", required = false) List<Integer> notInExamId,

			// examName
			@RequestParam(name = "examName.equal", required = false) String eqExamName,
			@RequestParam(name = "examName.notEqual", required = false) String notEqExamName,
			@RequestParam(name = "examName.like", required = false) String likeExamName,
			@RequestParam(name = "examName.in", required = false) List<String> inExamName,
			@RequestParam(name = "examName.notIn", required = false) List<String> notInExamName,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request
			
			) {

		ExamResultSpecification specification = new ExamResultSpecification();
		Set<String> keySet = request.getParameterMap().keySet();
		for (String s : keySet) {
			
			
			if(!s.equals("page-number") && !s.equals("page-size")&& !s.equals("sort-by") && request.getParameterValues(s)!= null ) {
				String[] nameSplit = s.replace('.', ' ').split(" ");
			
				switch (nameSplit[1]) {
				case "equal":
				
					specification.add(new SearchCriteria(nameSplit[0], SearchOperation.EQUAL, request.getParameter(s)));
					break;
				case "notEqual":
					specification.add(new SearchCriteria(nameSplit[0], SearchOperation.NOT_EQUAL, request.getParameter(s)));
					break;
				case "greaterThan":
					specification
							.add(new SearchCriteria(nameSplit[0], SearchOperation.GREATER_THAN, request.getParameter(s)));
					break;
				case "greaterThanOrEqual":
					specification.add(
							new SearchCriteria(nameSplit[0], SearchOperation.GREATER_THAN_EQUAL, request.getParameter(s)));
					break;
				case "lessThan":
					specification.add(new SearchCriteria(nameSplit[0], SearchOperation.LESS_THAN, request.getParameter(s)));
					break;
				case "lessThanOrEqual":
					specification.add(
							new SearchCriteria(nameSplit[0], SearchOperation.LESS_THAN_EQUAL, request.getParameter(s)));
					break;
				case "like":
					specification.add(new SearchCriteria(nameSplit[0], SearchOperation.LIKE, request.getParameter(s)));
					break;
				case "in":
					specification.add(new SearchCriteria(nameSplit[0], SearchOperation.IN, request.getParameterMap().get(s)));
					break;
				case "notIn":
					specification.add(new SearchCriteria(nameSplit[0], SearchOperation.NOT_IN, request.getParameterMap().get(s)));
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

		return examMapper
				.listExamResultToListExamResultDTO(examResultsService.findAll(specification, pageable).toList());

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
