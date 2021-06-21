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

import com.manageuniversity.entity.Exams;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.ExamsRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class ExamsService.
 */
@Service
public class ExamsService {
	
	/** The exams repository. */
	@Autowired
	private ExamsRepository examsRepository;
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "examsAll")
	public List<Exams> findAll(){
		return examsRepository.findAll();
	}
	
	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "exams", key = "#id")
	public ResponseEntity<Exams> findById(Integer id){
		Exams exams = examsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Exam not found with id: "+ id));
		return ResponseEntity.ok().body(exams);
	}
	
	/**
	 * Creates the exam.
	 *
	 * @param exams the exams
	 * @return the exams
	 */
	public Exams createExam(Exams exams) {
		return examsRepository.save(exams);
	}
	
	/**
	 * Update exam.
	 *
	 * @param id the id
	 * @param exams the exams
	 * @return the response entity
	 */
	@CachePut(cacheNames = "exams", key = "#id")
	public ResponseEntity<Exams> updateExam(Integer id, Exams exams){
		Exams exams2 = examsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Exam not found with id: "+ id));
		
		exams.setId(exams2.getId());
		examsRepository.save(exams);
		
		return ResponseEntity.ok().body(exams);
	}
	
	/**
	 * Delete exam.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "exams", key = "#id")
	public ResponseEntity<String> deleteExam(Integer id){
		Exams exams = examsRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Exam not found with id: "+ id));
		
		examsRepository.delete(exams);
		return ResponseEntity.ok().body("Exam deleted successful");
	}
	
	/**
	 * Find paginated.
	 *
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @return the list
	 */
	@Cacheable(cacheNames = "examsPage", key = "#pageNumber")
	public List<Exams> findPaginated(int pageNumber, int pageSize){
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Exams> pageResult = examsRepository.findAll(pageable);
		return pageResult.toList();
	}

}
