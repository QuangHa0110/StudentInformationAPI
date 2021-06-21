package com.manageuniversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	@Cacheable(cacheNames = "studentsAll")
	public List<Students> findAll() {
		return studentsRepository.findAll();
	}


	/**
	 * Find by id.
	 *
	 * @param Id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "students", key = "#id")
	public ResponseEntity<Students> findById(Integer id) {

		Students students = studentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student no found on: " + id));
		return ResponseEntity.ok().body(students);
	}
	
	/**
	 * Find by full name.
	 *
	 * @param FullName the full name
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "studentsName", key = "#fullName")
	public List<Students> findByFullName(String fullName){
		List<Students> students = studentsRepository.findByFullName(fullName);
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
	@CachePut(cacheNames = "students", key = "#id")
	public ResponseEntity<Students> updateStudent(Integer id, Students students) {
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
	@CacheEvict(cacheNames = "students", key = "#id")
	public ResponseEntity<String> deleteStudent(Integer id) {
		Students students = studentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student no found on: " + id));

		studentsRepository.delete(students);

		return ResponseEntity.ok().body("Student deleted with success");
	}
	
	/**
	 * Find paginated.
	 *
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @return the list
	 */
	@Cacheable(cacheNames = "studentsPage", key = "#pageNumber")
	public List<Students> findPaginated(int pageNumber, int pageSize){
		Pageable  pageable = PageRequest.of(pageNumber, pageSize);
		Page<Students> pageResult = studentsRepository.findAll(pageable);
		return pageResult.toList();
	}

}
