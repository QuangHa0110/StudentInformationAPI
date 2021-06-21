package com.manageuniversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.entity.Teachers;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.TeachersRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class TeachersService.
 */
@Service
public class TeachersService {
	
	/** The teachers repository. */
	@Autowired
	private TeachersRepository teachersRepository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "teacherAll")
	public List<Teachers> findAll() {
		return teachersRepository.findAll();
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "teachers", key = "#id")
	public ResponseEntity<Teachers> findById(Integer id) {
		Teachers teachers = teachersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher no found with id: " + id));

		return ResponseEntity.ok().body(teachers);
	}

	/**
	 * Creates the teacher.
	 *
	 * @param teachers the teachers
	 * @return the teachers
	 */
	public Teachers createTeacher(Teachers teachers) {
		return teachersRepository.save(teachers);

	}

	/**
	 * Update teacher.
	 *
	 * @param id the id
	 * @param teachers the teachers
	 * @return the response entity
	 */
	@CachePut(cacheNames = "teachers", key = "#id")
	public ResponseEntity<Teachers> updateTeacher(Integer id, Teachers teachers) {
		Teachers teachers2 = teachersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher no found with id: " + id));

		teachers.setId(teachers2.getId());
		teachersRepository.save(teachers);
		return ResponseEntity.ok().body(teachers);
	}
	
	/**
	 * Delete teacher.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "teachers", key = "#id")
	public ResponseEntity<String> deleteTeacher(Integer id){
		Teachers teachers = teachersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Teacher no found with id: "+id));
		teachersRepository.delete(teachers);
		
		return ResponseEntity.ok().body("Teacher deleted with success");
	}
	
	/**
	 * Find paginated.
	 *
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @return the list
	 */
	@Cacheable(cacheNames = "teachersPage", key = "#pageNumber")
	public List<Teachers> findPaginated(int pageNumber, int pageSize){
		Sort sort = Sort.by("fullName").ascending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Teachers> pageResult = teachersRepository.findAll(pageable);
		return pageResult.toList();
	}
	

}
