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

import com.manageuniversity.entity.ExamResults;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.ExamResultsRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class ExamResultsService.
 */
@Service
public class ExamResultsService {
	
	/** The exam results repository. */
	@Autowired
	private ExamResultsRepository examResultsRepository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "examResultsAll")
	public List<ExamResults> findAll() {
		return examResultsRepository.findAll();
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "examResult", key = "#id")
	public ResponseEntity<ExamResults> findById(Integer id) {
		ExamResults examResults = examResultsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam result not found with id: " + id));

		return ResponseEntity.ok().body(examResults);
	}

	/**
	 * Creates the exam result.
	 *
	 * @param examResults the exam results
	 * @return the exam results
	 */
	public ExamResults createExamResult(ExamResults examResults) {
		return examResultsRepository.save(examResults);
	}

	/**
	 * Update exam result.
	 *
	 * @param id the id
	 * @param examResults the exam results
	 * @return the response entity
	 */
	@CachePut(cacheNames = "examResult", key = "#id")
	public ResponseEntity<ExamResults> updateExamResult(Integer id, ExamResults examResults) {
		ExamResults examResults2 = examResultsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam result not found with id: " + id));
		
		examResults.setId(examResults2.getId());
		examResultsRepository.save(examResults);
		
		return ResponseEntity.ok().body(examResults);
	}
	
	/**
	 * Delete exam result.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "examResult", key = "#id")
	public ResponseEntity<String> deleteExamResult(Integer id){
		ExamResults examResults = examResultsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam result not found with id: " + id));
		examResultsRepository.delete(examResults);
		return ResponseEntity.ok().body("Exam result deleted successful");
	}
	
	/**
	 * Find paginated.
	 *
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @return the list
	 */
	@Cacheable(cacheNames = "examResultsPage", key = "#pageNumber")
	public List<ExamResults> findPaginated(int pageNumber, int pageSize){
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<ExamResults> pageResult= examResultsRepository.findAll(paging);
		return pageResult.toList();
	}
	
	

}
