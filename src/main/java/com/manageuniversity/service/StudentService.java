package com.manageuniversity.service;

import com.manageuniversity.dto.StudentDTO;
import com.manageuniversity.entity.Student;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.mapper.StudentMapper;
import com.manageuniversity.repository.StudentRepository;
import com.manageuniversity.repository.specification.StudentSpecification;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentsService.
 */
@Service
public class StudentService {

	/** The students repository. */

	private StudentRepository studentsRepository;

	private final StudentMapper studentMapper;

	public StudentService(StudentRepository studentsRepository, StudentMapper studentMapper) {
		this.studentsRepository = studentsRepository;
		this.studentMapper = studentMapper;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "studentsAll")
	public Page<Student> findAll(StudentSpecification specification, Pageable pageable) {

		return studentsRepository.findAll(specification, pageable);
	}

	@Cacheable(cacheNames = "studentsAll")
	public Page<Student> findAll(Pageable pageable) {

		return studentsRepository.findAll(pageable);
	}



	/**
	 * Find by id.
	 *
	 * @param Id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "student", key = "#id")
	public ResponseEntity<Student> findById(Integer id) {

		Student students = studentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student no found on: " + id));
		return ResponseEntity.ok().body(students);
	}

	/**
	 * Adds the student.
	 *
	 * @param students the students
	 * @return the students
	 */
	public Student addStudent(StudentDTO studentsDTO) {
		Student students = studentMapper.studentDTOToStudent(studentsDTO);
		return studentsRepository.save(students);

	}

	/**
	 * Update student.
	 *
	 * @param id       the id
	 * @param students the students
	 * @return the response entity
	 */
	@CachePut(cacheNames = "student", key = "#id")
	public ResponseEntity<Student> updateStudent(Integer id, StudentDTO studentsDTO) {
		Student students = studentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student no found on: " + id));

		if (studentsDTO.getAddress() != null) {
			students.setAddress(studentsDTO.getAddress());
		}
		if (studentsDTO.getBirthday() != null) {
			students.setBirthday(studentsDTO.getBirthday());
		}
		if (studentsDTO.getCreateDate() != null) {
			students.setCreateDate(studentsDTO.getCreateDate());

		}
		if (studentsDTO.getEmail() != null) {
			students.setEmail(studentsDTO.getEmail());
		}
		if (studentsDTO.getFacebook() != null) {
			students.setFacebook(studentsDTO.getFacebook());
		}
		if (studentsDTO.getFullName() != null) {
			students.setFullname(studentsDTO.getFullName());
		}
		if (studentsDTO.getNote() != null) {
			students.setNote(studentsDTO.getNote());
		}

		studentsRepository.save(students);

		return ResponseEntity.ok().body(students);
	}

	/**
	 * Delete student.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "student", key = "#id")
	public ResponseEntity<String> deleteStudent(Integer id) {
		Student students = studentsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Student no found on: " + id));

		studentsRepository.delete(students);

		return ResponseEntity.ok().body("Student deleted with success");
	}

}
