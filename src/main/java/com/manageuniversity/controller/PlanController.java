package com.manageuniversity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.manageuniversity.controller.request.GetParamRequest;
import com.manageuniversity.dto.PlanDTO;
import com.manageuniversity.entity.Plan;
import com.manageuniversity.mapper.PlanMapper;
import com.manageuniversity.repository.specification.PlanSpecification;
import com.manageuniversity.service.PlanService;

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
@RequestMapping("/api/v1/plans")
public class PlanController {

	private PlanService plansService;
	private PlanMapper planMapper;

	public PlanController(PlanService plansService, PlanMapper planMapper) {
		this.plansService = plansService;
		this.planMapper = planMapper;
	}

	@GetMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
	public List<PlanDTO> findAll(@RequestParam(required = false) List<List<String>> requestList,

			@RequestParam(name = "page-number", required = false, defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "page-size", required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(name = "sort-by", required = false, defaultValue = "+id") String[] sort,
			HttpServletRequest request

	) {
		if (requestList == null) {
			return planMapper.listPlanToListPlanDTO(
					plansService.findAll(GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		} else {
			PlanSpecification specification = new PlanSpecification(requestList,
					request.getParameterValues("requestList").length);

			return planMapper.listPlanToListPlanDTO(plansService
					.findAll(specification, GetParamRequest.getPageable(pageNumber, pageSize, sort)).toList());
		}
	}

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
