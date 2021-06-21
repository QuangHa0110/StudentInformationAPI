package com.manageuniversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.entity.Plans;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.PlansRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class PlansService.
 */
@Service
public class PlansService {
	
	/** The plans repository. */
	@Autowired
	private PlansRepository plansRepository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "plansAll")
	public List<Plans> findAll(){
		return plansRepository.findAll();
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "plans", key = "#id")
	public ResponseEntity<Plans> findById(Integer id){
		Plans plans = plansRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Plan not found with id: "+ id));
		return ResponseEntity.ok().body(plans);
	}
	
	/**
	 * Creates the plan.
	 *
	 * @param plans the plans
	 * @return the plans
	 */
	public Plans createPlan(Plans plans) {
		return plansRepository.save(plans);
	}
	
	/**
	 * Update plan.
	 *
	 * @param id the id
	 * @param plans the plans
	 * @return the response entity
	 */
	@CachePut(cacheNames = "plans", key = "#id")	
	public ResponseEntity<Plans> updatePlan(Integer id, Plans plans){
		Plans plans2 = plansRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Plan not found with id: "+ id));
		
		plans.setId(plans2.getId());
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
	public ResponseEntity<String> deletePlan(Integer id){
		Plans plans = plansRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Plan not found with id: "+ id));
		plansRepository.delete(plans);
		return ResponseEntity.ok().body("Plan deleted successful");
	}
	
	/**
	 * Find paginated.
	 *
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @return the list
	 */
	@Cacheable(cacheNames = "plansPage", key = "#pageNumber")
	public List<Plans> findPaginated(int pageNumber, int pageSize){
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Plans> pageResult = plansRepository.findAll(pageable);
		return pageResult.toList();
	} 
	

}
