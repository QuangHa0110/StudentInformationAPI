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

import com.manageuniversity.entity.Classes;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.ClassesRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassesService.
 */
/**
 * @author Quang Ha
 *
 */
@Service
public class ClassesService {

	/** The classes repository. */
	@Autowired
	private ClassesRepository classesRepository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "classesAll")
	public List<Classes> findAll() {
		return classesRepository.findAll();
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "classes", key = "#id")
	public ResponseEntity<Classes> findById(Integer id) {
		Classes classes = classesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Class no found with id: " + id));
		return ResponseEntity.ok().body(classes);
	}

	/**
	 * Creates the class.
	 *
	 * @param classes the classes
	 * @return the classes
	 */
	public Classes createClass(Classes classes) {
		classes.setId(null);
		return classesRepository.save(classes);
	}

	/**
	 * Update class.
	 *
	 * @param id      the id
	 * @param classes the classes
	 * @return the response entity
	 */
	@CachePut(cacheNames = "classes", key = "#id")
	public ResponseEntity<Classes> updateClass(Integer id, Classes classes) {

		Classes classes2 = classesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Class no found with id: " + id));
		classes.setId(classes2.getId());
		classesRepository.save(classes);

		return ResponseEntity.ok().body(classes);
	}

	/**
	 * Delete class.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "classes", key = "#id")
	public ResponseEntity<String> deleteClass(Integer id) {
		Classes classes = classesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Class no found with id: " + id));

		classesRepository.delete(classes);
		return ResponseEntity.ok().body("Class deleted with success");
	}

	/**
	 * Find by course id.
	 *
	 * @param courseId the course id
	 * @return the list
	 */
	@Cacheable(cacheNames = "classCourseId", key = "#courseId")
	public List<Classes> findByCourseId(Integer courseId) {

		return classesRepository.getListClassByCourseId(courseId);

	}

	/**
	 * Find by course name.
	 *
	 * @param courseName the course name
	 * @return the list
	 */
	@Cacheable(cacheNames = "classCourseName", key = "#courseName")
	public List<Classes> findByCourseName(String courseName) {
		return classesRepository.getListClassByCourseName(courseName);
	}

	/**
	 * Find by teacher name.
	 *
	 * @param teacherName the teacher name
	 * @return the list
	 */
	@Cacheable(cacheNames = "classTeacherName", key = "#teacherName")
	public List<Classes> findByTeacherName(String teacherName) {
		return classesRepository.getListClassByTeacherName(teacherName);
	}

	/**
	 * Find paginated.
	 *
	 * @param pageNo   the page no
	 * @param pageSize the page size
	 * @return the list
	 */
	@Cacheable(cacheNames = "classesPage", key = "#pageNo")
	public List<Classes> findPaginated(int pageNo, int pageSize) {
		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<Classes> pageResult = classesRepository.findAll(paging);
		return pageResult.toList();
	}

}
