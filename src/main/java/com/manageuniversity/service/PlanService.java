package com.manageuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.dto.PlanDTO;
import com.manageuniversity.entity.Course;
import com.manageuniversity.entity.Plan;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.mapper.PlanMapperImpl;
import com.manageuniversity.repository.CourseRepository;
import com.manageuniversity.repository.PlanRepository;
import com.manageuniversity.repository.specification.PlanSpecification;

// TODO: Auto-generated Javadoc
/**
 * The Class PlansService.
 */
@Service
public class PlanService {

	/** The plans repository. */
	@Autowired
	private PlanRepository plansRepository;

	/** The courses repository. */
	@Autowired
	private CourseRepository coursesRepository;

	private final PlanMapperImpl planMapper = new PlanMapperImpl();

	/**
	 * Find all.
	 *
	 * @param specification the specification
	 * @param pageable      the pageable
	 * @return the list
	 */
	@Cacheable(cacheNames = "plansAll")
	public Page<Plan> findAll(PlanSpecification specification, Pageable pageable) {

		return plansRepository.findAll(specification, pageable);
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "plans", key = "#id")
	public ResponseEntity<Plan> findById(Integer id) {
		Plan plans = plansRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
		return ResponseEntity.ok().body(plans);
	}

	/**
	 * Creates the plan.
	 *
	 * @param plansDTO the plans DTO
	 * @return the plans
	 */
	public Plan createPlan(PlanDTO plansDTO) {
		plansDTO.setId(null);

		Course course = coursesRepository.findById(plansDTO.getCourseId())
				.orElseThrow(() -> new ResourceNotFoundException("Course no found with id: " + plansDTO.getCourseId()));

		Plan plan = planMapper.planDTOToPlan(plansDTO);
		plan.setCourse(course);
		return plansRepository.save(plan);
	}

	/**
	 * Update plan.
	 *
	 * @param id       the id
	 * @param plansDTO the plans DTO
	 * @return the response entity
	 */
	@CachePut(cacheNames = "plans", key = "#id")
	public ResponseEntity<Plan> updatePlan(Integer id, PlanDTO plansDTO) {
		Plan plans = plansRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));

		if (plansDTO.getCourseId() != null) {
			Course courses = coursesRepository.findById(plansDTO.getCourseId()).orElseThrow(
					() -> new ResourceNotFoundException("Course no found with id: " + plansDTO.getCourseId()));
			plans.setCourse(courses);

		}
		if (plansDTO.getName() != null) {
			plans.setName(plansDTO.getName());
		}

		plansRepository.save(plans);
		return ResponseEntity.ok().body(plans);

	}

	/**
	 * Delete plan.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "plans", key = "#id")
	public ResponseEntity<String> deletePlan(Integer id) {
		Plan plans = plansRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Plan not found with id: " + id));
		plansRepository.delete(plans);
		return ResponseEntity.ok().body("Plan deleted successful");
	}

}
