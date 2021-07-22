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

import com.manageuniversity.dto.PlanDTO;
import com.manageuniversity.entity.Plan;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.mapper.PlanMapperImpl;
import com.manageuniversity.repository.specification.PlanSpecification;
import com.manageuniversity.repository.specification.SearchCriteria;
import com.manageuniversity.repository.specification.SearchOperation;
import com.manageuniversity.service.PlanService;

@RestController
@RequestMapping("/api/v1/plans")
public class PlanController {
	@Autowired
	private PlanService plansService;

	private final PlanMapperImpl planMapper = new PlanMapperImpl();

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<PlanDTO> findAll(
			// id
			@RequestParam(name = "id.greaterThan", required = false) Integer gtId,
			@RequestParam(name = "id.greaterThanOrEqual", required = false) Integer gteId,
			@RequestParam(name = "id.lessThan", required = false) Integer ltId,
			@RequestParam(name = "id.lessThanOrEqual", required = false) Integer lteId,
			@RequestParam(name = "id.in", required = false) List<Integer> inId,
			@RequestParam(name = "id.notIn", required = false) List<Integer> notInId,
			// name
			@RequestParam(name = "name.equal", required = false) String name,
			@RequestParam(name = "name.notEqual", required = false) String notName,
			@RequestParam(name = "name.like", required = false) String likeName,
			@RequestParam(name = "name.in", required = false) List<String> inName,
			@RequestParam(name = "name.notIn", required = false) List<String> notInName,

			// courseId
			@RequestParam(name = "courseId.equal", required = false) Integer eqCourseId,
			@RequestParam(name = "courseId.notEqual", required = false) Integer notEqCourseId,
			@RequestParam(name = "courseId.greaterThan", required = false) Integer gtCourseId,
			@RequestParam(name = "courseId.greaterThanOrEqual", required = false) Integer gteCourseId,
			@RequestParam(name = "courseId.lessThan", required = false) Integer ltCourseId,
			@RequestParam(name = "courseId.lessThanOrEqual", required = false) Integer lteCourseId,
			@RequestParam(name = "courseId.in", required = false) List<Integer> inCourseId,
			@RequestParam(name = "courseId.notIn", required = false) List<Integer> notInCourseId,

			// courseName
			@RequestParam(name = "courseName.equal", required = false) String eqCourseName,
			@RequestParam(name = "courseName.notEqual", required = false) String notEqCourseName,
			@RequestParam(name = "courseName.like", required = false) String likeCourseName,
			@RequestParam(name = "courseName.in", required = false) List<String> inCourseName,
			@RequestParam(name = "courseName.notIn", required = false) List<String> notInCourseName,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request

	) {
		
			PlanSpecification specification = new PlanSpecification();
			Set<String> keySet = request.getParameterMap().keySet();
			for (String s : keySet) {
				if (!s.equals("page-number") && !s.equals("page-size") && !s.equals("sort-by")
						&& request.getParameterValues(s) != null) {
					String[] nameSplit = s.replace('.', ' ').split(" ");

					switch (nameSplit[1]) {
					case "equal":

						specification
								.add(new SearchCriteria(nameSplit[0], SearchOperation.EQUAL, request.getParameter(s)));
						break;
					case "notEqual":
						specification.add(
								new SearchCriteria(nameSplit[0], SearchOperation.NOT_EQUAL, request.getParameter(s)));
						break;
					case "greaterThan":
						specification.add(new SearchCriteria(nameSplit[0], SearchOperation.GREATER_THAN,
								request.getParameter(s)));
						break;
					case "greaterThanOrEqual":
						specification.add(new SearchCriteria(nameSplit[0], SearchOperation.GREATER_THAN_EQUAL,
								request.getParameter(s)));
						break;
					case "lessThan":
						specification.add(
								new SearchCriteria(nameSplit[0], SearchOperation.LESS_THAN, request.getParameter(s)));
						break;
					case "lessThanOrEqual":
						specification.add(new SearchCriteria(nameSplit[0], SearchOperation.LESS_THAN_EQUAL,
								request.getParameter(s)));
						break;
					case "like":
						specification
								.add(new SearchCriteria(nameSplit[0], SearchOperation.LIKE, request.getParameter(s)));
						break;
					case "in":
						specification.add(
								new SearchCriteria(nameSplit[0], SearchOperation.IN, request.getParameterMap().get(s)));
						break;
					case "notIn":
						specification.add(new SearchCriteria(nameSplit[0], SearchOperation.NOT_IN,
								request.getParameterMap().get(s)));
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

			return planMapper.listPlanToListPlanDTO(plansService.findAll(specification, pageable).toList());

		
	}

//	@GetMapping("/course-id/{courseId}")
//	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
//	public List<PlanDTO> findByCourseId(@PathVariable(name = "courseId", required =  true) Integer courseId){
//		return plansService.findByCourseId(courseId);
//	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Plan> findById(@PathVariable(name = "id", required = true) Integer id) {
		return plansService.findById(id);
	}

	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public Plan createPlan(@RequestBody PlanDTO plans) {
		return plansService.createPlan(plans);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public ResponseEntity<Plan> updatePlan(@PathVariable(name = "id") Integer id, @RequestBody PlanDTO plans) {
		return plansService.updatePlan(id, plans);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deletePlan(@PathVariable(name = "id") Integer id) {
		return plansService.deletePlan(id);
	}

}
