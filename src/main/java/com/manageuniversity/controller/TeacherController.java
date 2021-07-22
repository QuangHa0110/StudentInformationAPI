package com.manageuniversity.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
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

import com.manageuniversity.dto.TeacherDTO;
import com.manageuniversity.entity.Teacher;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.mapper.TeacherMapperImpl;
import com.manageuniversity.repository.specification.SearchCriteria;
import com.manageuniversity.repository.specification.SearchOperation;
import com.manageuniversity.repository.specification.TeacherSpecification;
import com.manageuniversity.service.TeacherService;

@RestController
@RequestMapping("/api/v1/teachers")
public class TeacherController {
	@Autowired
	private TeacherService teachersService;

	private final TeacherMapperImpl teacherMapper = new TeacherMapperImpl();

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<TeacherDTO> findAlll(
			// id
			@RequestParam(name = "id.greaterThan", required = false) Integer gtId,
			@RequestParam(name = "id.greaterThanOrEqual", required = false) Integer gteId,
			@RequestParam(name = "id.lessThan", required = false) Integer ltId,
			@RequestParam(name = "id.lessThanOrEqual", required = false) Integer lteId,
			@RequestParam(name = "id.in", required = false) List<Integer> inId,
			@RequestParam(name = "id.notIn", required = false) List<Integer> notInId,

			// fullname
			@RequestParam(name = "fullname.equal", required = false) String eqFullname,
			@RequestParam(name = "fullname.notEqual", required = false) String notEqFullname,
			@RequestParam(name = "fullname.like", required = false) String likeFullname,
			@RequestParam(name = "fullname.in", required = false) List<String> inFullname,
			@RequestParam(name = "fullname.notIn", required = false) List<String> notInFullname,

			// email
			@RequestParam(name = "email.equal", required = false) String eqEmail,
			@RequestParam(name = "email.notEqual", required = false) String notEqEmail,
			@RequestParam(name = "email.like", required = false) String likeEmail,
			@RequestParam(name = "email.in", required = false) List<String> inEmail,
			@RequestParam(name = "email.notIn", required = false) List<String> notInEmail,

			// phone
			@RequestParam(name = "phone.equal", required = false) String eqPhone,
			@RequestParam(name = "phone.notEqual", required = false) String notEqPhone,
			@RequestParam(name = "phone.like", required = false) String likePhone,
			@RequestParam(name = "phone.in", required = false) List<String> inPhone,
			@RequestParam(name = "phone.notIn", required = false) List<String> notInPhone,

			// address
			@RequestParam(name = "address.equal", required = false) String eqAddress,
			@RequestParam(name = "address.notEqual", required = false) String notEqAddress,
			@RequestParam(name = "address.like", required = false) String likeAddress,
			@RequestParam(name = "address.in", required = false) List<String> inAddress,
			@RequestParam(name = "address.notIn", required = false) List<String> notInAddress,
			
			// grade
			@RequestParam(name = "grade.equal", required = false) String eqGrade,
			@RequestParam(name = "grade.notEqual", required = false) String notEqGrade,
			@RequestParam(name = "grade.like", required = false) String likeGrade,
			@RequestParam(name = "grade.in", required = false) List<String> inGrade,
			@RequestParam(name = "grade.notIn", required = false) List<String> notInGrade,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request

	) {

		TeacherSpecification specification = new TeacherSpecification();
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

		return teacherMapper.listTeacherToListTeacherDTO(teachersService.findAll(specification, pageable).toList());

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
