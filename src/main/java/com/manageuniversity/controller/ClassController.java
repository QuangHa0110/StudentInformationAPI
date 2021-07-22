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

import com.manageuniversity.dto.ClassDTO;
import com.manageuniversity.entity.Class;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.mapper.ClassMapperImpl;
import com.manageuniversity.repository.specification.ClassSpecification;
import com.manageuniversity.repository.specification.SearchCriteria;
import com.manageuniversity.repository.specification.SearchOperation;
import com.manageuniversity.service.ClassService;

@RestController
@RequestMapping("/api/v1/classes")

public class ClassController {

	@Autowired
	private ClassService classesService;

	private ClassMapperImpl classMapper = new ClassMapperImpl();

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<ClassDTO> findAll(
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
			// status
			@RequestParam(name = "status.equal", required = false) String status,
			@RequestParam(name = "status.notEqual", required = false) String notStatus,
			@RequestParam(name = "status.like", required = false) String likeStatus,
			@RequestParam(name = "status.in", required = false) List<String> inStatus,
			@RequestParam(name = "status.notIn", required = false) List<String> notInStatus,
			// startDate
			@RequestParam(name = "startDate.equal", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date eqStartDate,
			@RequestParam(name = "startDate.greaterThan", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date gtStartDate,
			@RequestParam(name = "startDate.greaterThanOrEqual", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date gteStartDate,
			@RequestParam(name = "startDate.lessThan", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ltStartDate,
			@RequestParam(name = "startDate.lessThanOrEqual", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lteStartDate,
			@RequestParam(name = "startDate.notEqual", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date notStartDate,
			@RequestParam(name = "startDate.in", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") List<Date> inStartDate,
			@RequestParam(name = "startDate.notIn", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") List<Date> notInStartDate,
			// endDate
			@RequestParam(name = "endDate.equal", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date eqEndDate,
			@RequestParam(name = "endDate.greaterThan", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date gtEndDate,
			@RequestParam(name = "endDate.greaterThanOrEqual", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date gteEndDate,
			@RequestParam(name = "endDate.lessThan", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ltEndDate,
			@RequestParam(name = "endDate.lessThanOrEqual", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date lteEndDate,
			@RequestParam(name = "endDate.notEqual", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date notEndDate,
			@RequestParam(name = "endDate.in", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") List<Date> inEndDate,
			@RequestParam(name = "endDate.notIn", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") List<Date> notInEndDate,
			// teacherId
			@RequestParam(name = "teacherId.equal", required = false) Integer eqTeacherId,
			@RequestParam(name = "teacherId.notEqual", required = false) Integer notEqTeacherId,
			@RequestParam(name = "teacherId.greaterThan", required = false) Integer gtTeacherId,
			@RequestParam(name = "teacherId.greaterThanOrEqual", required = false) Integer gteTeacherId,
			@RequestParam(name = "teacherId.lessThan", required = false) Integer ltTeacherId,
			@RequestParam(name = "teacherId.lessThanOrEqual", required = false) Integer lteTeacherId,
			@RequestParam(name = "teacherId.in", required = false) List<Integer> inTeacherId,
			@RequestParam(name = "teacherId.notIn", required = false) List<Integer> notInTeacherId,

			// teacherName
			@RequestParam(name = "teacherName.equal", required = false) String eqTeacherName,
			@RequestParam(name = "teacherName.notEqual", required = false) String notEqTeacherName,
			@RequestParam(name = "teacherName.like", required = false) String likeTeacherName,
			@RequestParam(name = "teacherName.in", required = false) List<String> inTeacherName,
			@RequestParam(name = "teacherName.notIn", required = false) List<String> notInTeacherName,
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

			// Pageable pageable
			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request

	) {
		ClassSpecification specification = new ClassSpecification();
		
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

		return classMapper.listClassToListClassDTO(classesService.findAll(specification, pageable).toList());

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
