package com.manageuniversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.manageuniversity.entity.Students;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.StudentsRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentsService.
 */
@Service
public class StudentsService {
	
	/** The students repository. */
	@Autowired
	private StudentsRepository studentsRepository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	public List<Students> findAll() {
		return studentsRepository.findAll();
	}


	/**
	 * Find by id.
	 *
	 * @param Id the id
	 * @return the response entity
	 */
	public ResponseEntity<Students> findById(int Id) {

		Students students = studentsRepository.findById(Id)
				.orElseThrow(() -> new ResourceNotFoundException("Student no found on: " + Id));
		return ResponseEntity.ok().body(students);
	}
	
	/**
	 * Find by full name.
	 *
	 * @param FullName the full name
	 * @return the response entity
	 */
	public List<Students> findByFullName(String FullName){
		List<Students> students = studentsRepository.findByFullName(FullName);
		return	students;
	}

	/**
	 * Adds the student.
	 *
	 * @param students the students
	 * @return the students
	 */
	public Students addStudent(Students students) {
		return studentsRepository.save(students);

	}

	/**
	 * Update student.
	 *
	 * @param id the id
	 * @param students the students
	 * @return the response entity
	 */
	public ResponseEntity<Students> updateStudent(int id, Students students) {
		Students students2 = studentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student no found on: " + id));

		students.setId(students2.getId());

		studentsRepository.save(students);

		return ResponseEntity.ok().body(students);
	}

	/**
	 * Delete student.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	public ResponseEntity<String> deleteStudent(int id) {
		Students students = studentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student no found on: " + id));

		studentsRepository.delete(students);

		return ResponseEntity.ok().body("Student deleted with success");
	}

}
