package com.manageuniversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.entity.Classes;
import com.manageuniversity.entity.Courses;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.ClassesRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassesService.
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
	public List<Classes> findAll() {
		return classesRepository.findAll();
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	public ResponseEntity<Classes> findById(int id) {
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
		return classesRepository.save(classes);
	}

	/**
	 * Update class.
	 *
	 * @param id      the id
	 * @param classes the classes
	 * @return the response entity
	 */
	public ResponseEntity<Classes> updateClass(int id, Classes classes) {
		
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
	public ResponseEntity<String> deleteClass(int id) {
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
	public List<Classes> findByCourseId(int courseId) {

		return classesRepository.getListClassByCourseId(courseId);

	}
	
	/**
	 * Find by course name.
	 *
	 * @param course_name the course name
	 * @return the list
	 */
	public List<Classes> findByCourseName(String courseName){
		return classesRepository.getListClassByCourseName(courseName);
	}
	
	/**
	 * Find by teacher name.
	 *
	 * @param teacherName the teacher name
	 * @return the list
	 */
	public List<Classes> findByTeacherName(String teacherName){
		return classesRepository.getListClassByTeacherName(teacherName);
	}

}
