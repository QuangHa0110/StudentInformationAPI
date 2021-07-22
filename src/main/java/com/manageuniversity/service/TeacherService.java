package com.manageuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.dto.TeacherDTO;
import com.manageuniversity.entity.Teacher;
import com.manageuniversity.exception.APIException;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.mapper.TeacherMapperImpl;
import com.manageuniversity.repository.TeacherRepository;
import com.manageuniversity.repository.specification.TeacherSpecification;

// TODO: Auto-generated Javadoc
/**
 * The Class TeachersService.
 */
@Service
public class TeacherService {

	/** The teachers repository. */
	@Autowired
	private TeacherRepository teachersRepository;
	
	private final TeacherMapperImpl teacherMapper= new TeacherMapperImpl();

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "teacherAll")
	public Page<Teacher> findAll(TeacherSpecification specification, Pageable pageable) {
		
		try {
			return teachersRepository.findAll(specification, pageable);
		} catch (Exception e) {
			throw new APIException("Server error");
		}

	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "teacher", key = "#id")
	public ResponseEntity<Teacher> findById(Integer id) {
		try {
			Teacher teachers = teachersRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Teacher no found with id: " + id));

			return ResponseEntity.ok().body(teachers);
		} catch (Exception e) {
			throw new APIException("Server error");
		}

	}

	/**
	 * Creates the teacher.
	 *
	 * @param teachers the teachers
	 * @return the teachers
	 */
	public Teacher createTeacher(TeacherDTO teachersDTO) {
		try {
			return teachersRepository.save(teacherMapper.teacherDTOToTeacher(teachersDTO));
		} catch (Exception e) {
			throw new APIException("Server error");
		}

	}

	/**
	 * Update teacher.
	 *
	 * @param id       the id
	 * @param teachers the teachers
	 * @return the response entity
	 */
	@CachePut(cacheNames = "teacher", key = "#id")
	public ResponseEntity<Teacher> updateTeacher(Integer id, TeacherDTO teachersDTO) {
		try {
			Teacher teachers = teachersRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Teacher no found with id: " + id));

			if (teachersDTO.getAddress() != null) {
				teachers.setAddress(teachersDTO.getAddress());
			}
			if (teachersDTO.getEmail() != null) {
				teachers.setEmail(teachersDTO.getAddress());
			}
			if (teachersDTO.getFullname() != null) {
				teachers.setFullname(teachersDTO.getFullname());
			}
			if (teachersDTO.getGrade() != null) {
				teachers.setGrade(teachersDTO.getGrade());

			}
			if (teachersDTO.getPhone() != null) {
				teachers.setPhone(teachersDTO.getPhone());
			}

			teachersRepository.save(teachers);
			return ResponseEntity.ok().body(teachers);
		} catch (Exception e) {
			// TODO: handle exception
			throw new APIException("Server error");
		}

	}

	/**
	 * Delete teacher.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "teachers", key = "#id")
	public ResponseEntity<String> deleteTeacher(Integer id) {
		try {
			Teacher teachers = teachersRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Teacher no found with id: " + id));
			teachersRepository.delete(teachers);

			return ResponseEntity.ok().body("Teacher deleted with success");
		} catch (Exception e) {
			throw new APIException("Server error");
		}

	}



}
