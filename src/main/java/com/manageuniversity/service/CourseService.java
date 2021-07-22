package com.manageuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.dto.CourseDTO;
import com.manageuniversity.entity.Course;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.mapper.CourseMapperImpl;
import com.manageuniversity.repository.CourseRepository;
import com.manageuniversity.repository.specification.CourseSpecification;

// TODO: Auto-generated Javadoc
/**
 * The Class CoursesService.
 */
@Service
public class CourseService {
	
	/** The courses repository. */
	@Autowired
	private CourseRepository coursesRepository;
	
	private final CourseMapperImpl courseMapper= new CourseMapperImpl();

	/**
	 * Find all.
	 *
	 * @param specification the specification
	 * @param pageable the pageable
	 * @return the list
	 */
	@Cacheable(cacheNames = "coursesAll")
	public Page<Course> findAll(CourseSpecification specification, Pageable pageable) {
	
		return coursesRepository.findAll(specification,pageable);
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "courses", key = "#id")
	public ResponseEntity<Course> findById(Integer id) {
		Course courses = coursesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
		return ResponseEntity.ok().body(courses);
	}

	/**
	 * Creates the course.
	 *
	 * @param coursesDTO the courses DTO
	 * @return the courses
	 */
	public Course createCourse(CourseDTO coursesDTO) {
		coursesDTO.setId(null);
		
		Course courses= courseMapper.courseDTOToCourse(coursesDTO);
		
		return coursesRepository.save(courses);
	}

	/**
	 * Update course.
	 *
	 * @param id the id
	 * @param coursesDTO the courses DTO
	 * @return the response entity
	 */
	@CachePut(cacheNames = "courses", key = "#id")
	public ResponseEntity<CourseDTO> updateCourse(Integer id, CourseDTO coursesDTO) {
		Course courses2 = coursesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
		
		if(coursesDTO.getName()!=null) {
			courses2.setName(coursesDTO.getName());
		}
		if(coursesDTO.getType()!=null) {
			courses2.setType(coursesDTO.getType());
			
		}
		if(coursesDTO.getCreateDate()!=null){
			courses2.setCreateDate(coursesDTO.getCreateDate());
		}
		coursesRepository.save(courses2);
		return ResponseEntity.ok().body(courseMapper.courseToCourseDTO(courses2));
	}
	
	/**
	 * Delete course.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "courses", key = "id")
	public ResponseEntity<String> deleteCourse(Integer id){
		Course courses = coursesRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Course not found with id: "+ id));
		
		coursesRepository.delete(courses);
		return ResponseEntity.ok().body("Course deleted with success");
	}
	

	

}
