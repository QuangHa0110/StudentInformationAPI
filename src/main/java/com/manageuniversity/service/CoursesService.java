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

import com.manageuniversity.entity.Courses;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.CoursesRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class CoursesService.
 */
@Service
public class CoursesService {
	
	/** The courses repository. */
	@Autowired
	private CoursesRepository coursesRepository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "coursesAll")
	public List<Courses> findAll() {
		return coursesRepository.findAll();
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "courses", key = "#id")
	public ResponseEntity<Courses> findById(Integer id) {
		Courses courses = coursesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
		return ResponseEntity.ok().body(courses);
	}

	/**
	 * Creates the course.
	 *
	 * @param courses the courses
	 * @return the courses
	 */
	public Courses createCourse(Courses courses) {
		return coursesRepository.save(courses);
	}

	/**
	 * Update course.
	 *
	 * @param id the id
	 * @param courses the courses
	 * @return the response entity
	 */
	@CachePut(cacheNames = "courses", key = "#id")
	public ResponseEntity<Courses> updateCourse(Integer id, Courses courses) {
		Courses courses2 = coursesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
		courses.setId(courses2.getId());
		coursesRepository.save(courses);
		return ResponseEntity.ok().body(courses);
	}
	
	/**
	 * Delete course.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "courses", key = "id")
	public ResponseEntity<String> deleteCourse(Integer id){
		Courses courses = coursesRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Course not found with id: "+ id));
		
		coursesRepository.delete(courses);
		return ResponseEntity.ok().body("Course deleted with success");
	}
	
	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the list
	 */
	@Cacheable(cacheNames = "coursesName", key = "#name")
	public List<Courses> findByName(String name){
		return coursesRepository.findByName(name);
	}
	
	/**
	 * Find by type.
	 *
	 * @param type the type
	 * @return the list
	 */
	@Cacheable(cacheNames = "coursesType", key = "#type")
	public List<Courses> findByType(String type){
		return coursesRepository.findByType(type);
	}
	
	/**
	 * Find paginated.
	 *
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @return the list
	 */
	@Cacheable(cacheNames = "coursesPage", key = "#pageNumber")
	public List<Courses> findPaginated(int pageNumber, int pageSize){
		Pageable paging = PageRequest.of(pageNumber, pageSize);
		Page<Courses> pageResutl = coursesRepository.findAll(paging);
		return pageResutl.toList();
	}
	

}
