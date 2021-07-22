package com.manageuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.dto.ClassDTO;
import com.manageuniversity.entity.Class;
import com.manageuniversity.entity.Course;
import com.manageuniversity.entity.Teacher;
import com.manageuniversity.exception.APIException;
import com.manageuniversity.exception.BadRequestException;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.mapper.ClassMapperImpl;
import com.manageuniversity.repository.ClassRepository;
import com.manageuniversity.repository.CourseRepository;
import com.manageuniversity.repository.TeacherRepository;
import com.manageuniversity.repository.specification.ClassSpecification;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassesService.
 */
/**
 * @author Quang Ha
 *
 */
@Service
public class ClassService {

	/** The classes repository. */
	@Autowired
	private ClassRepository classesRepository;

	@Autowired
	private TeacherRepository teachersRepository;

	@Autowired
	private CourseRepository coursesRepository;
//	
//	@Autowired
//	private ClassMapperImpl classMapper;
	
	private final  ClassMapperImpl classMapper = new ClassMapperImpl();


	
	//@Cacheable(cacheNames = "classesAll", key = "#specification")
	public Page<Class> findAll(ClassSpecification specification, Pageable pageable) {

		return classesRepository.findAll(specification, pageable);
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "classes", key = "#id")
	public ResponseEntity<Class> findById(Integer id) {
		Class classes = classesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Class no found with id: " + id));
		

		return ResponseEntity.ok().body(classes);
	}

	/**
	 * Creates the class.
	 *
	 * @param classes the classes
	 * @return the classes
	 */
	public Class createClass(ClassDTO classesDTO) {
	//	try {
			if(classesDTO == null) {
				throw new BadRequestException("Class no infomation");
			}
			Teacher teacher = teachersRepository.findById(classesDTO.getTeacherId()).orElseThrow(
					() -> new ResourceNotFoundException("Teacher no found with id: " + classesDTO.getTeacherId()));

			Course course = coursesRepository.findById(classesDTO.getCourseId()).orElseThrow(
					() -> new ResourceNotFoundException("Course no found with id: " + classesDTO.getCourseId()));

			Class classes = classMapper.classDTOToClass(classesDTO);
			classes.setTeacher(teacher);
			classes.setCourse(course);

			return classesRepository.save(classes);
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new APIException("Server error");
//		}

		
	}

	/**
	 * Update class.
	 *
	 * @param id      the id
	 * @param classes the classes
	 * @return the response entity
	 */
	@CachePut(cacheNames = "classes", key = "#id")
	public ResponseEntity<ClassDTO> updateClass(Integer id, ClassDTO classesDTO) {
		try {
			if(classesDTO == null) {
				throw new BadRequestException("Class no infomation");
			}
			else {
				Class classes2 = classesRepository.findById(id)
						.orElseThrow(() -> new ResourceNotFoundException("Class no found with id: " + id));

				if (classesDTO.getName() != null) {
					classes2.setName(classesDTO.getName());
				}
				if (classesDTO.getStatus() != null) {
					classes2.setStatus(classesDTO.getStatus());
				}
				if (classesDTO.getStartDate() != null) {
					classes2.setStartDate(classesDTO.getStartDate());
				}
				if (classesDTO.getEndDate() != null) {
					classes2.setEndDate(classesDTO.getEndDate());
				}
				if (classesDTO.getCourseId() != null) {
					Course courses = coursesRepository.findById(classesDTO.getCourseId()).orElseThrow(
							() -> new ResourceNotFoundException("Courses no found with id: " + classesDTO.getCourseId()));

					classes2.setCourse(courses);
				}
				if (classesDTO.getTeacherId() != null) {
					Teacher teachers = teachersRepository.findById(classesDTO.getTeacherId()).orElseThrow(
							() -> new ResourceNotFoundException("Teacher no found with id: " + classesDTO.getTeacherId()));

					classes2.setTeacher(teachers);
				}
				classesRepository.save(classes2);
				return ResponseEntity.ok().body(classMapper.classToClassDTO(classes2));
			}
		
		} catch (Exception e) {
			// TODO: handle exception
			throw new APIException("Server error");
		}
		
		
	}

	/**
	 * Delete class.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "classes", key = "#id")
	public ResponseEntity<String> deleteClass(Integer id) {
		Class classes = classesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Class no found with id: " + id));

		classesRepository.delete(classes);
		return ResponseEntity.ok().body("Class deleted with success");
	}



//	@Cacheable(cacheNames = "teacher-class", key = "#teacherId")
//	public List<ClassDTO> findByTeacherId(Integer teacherId) {
//		try {
//			Teacher teachers = teachersRepository.findById(teacherId)
//					.orElseThrow(() -> new ResourceNotFoundException("Teacher no found with id: " + teacherId));
//
//			return ClassesMapper.mapListClasses(teachers.getClasses());
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new APIException("Server error");
//		}
//		
//	}
//
//	@Cacheable(cacheNames = "course-class", key = "#courseId")
//	public List<ClassDTO> findByCourseId(Integer courseId) {
//		try {
//			Course courses = coursesRepository.findById(courseId)
//					.orElseThrow(() -> new ResourceNotFoundException("Course no found with id: " + courseId));
//			return ClassesMapper.mapListClasses(courses.getClasses());
//		} catch (Exception e) {
//			// TODO: handle exception
//			throw new APIException("Server error");
//		}
//		
//	}

}
