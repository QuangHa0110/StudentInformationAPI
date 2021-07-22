package com.manageuniversity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.dto.ExamDTO;
import com.manageuniversity.entity.Course;
import com.manageuniversity.entity.Exam;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.mapper.ExamMapperImpl;
import com.manageuniversity.repository.CourseRepository;
import com.manageuniversity.repository.ExamRepository;
import com.manageuniversity.repository.specification.ExamSpecification;

// TODO: Auto-generated Javadoc
/**
 * The Class ExamsService.
 */
@Service
public class ExamService {

	/** The exams repository. */
	@Autowired
	private ExamRepository examsRepository;

	@Autowired
	private CourseRepository coursesRepository;
	
	private final ExamMapperImpl examMapper= new ExamMapperImpl();

	/**
	 * Find all.
	 *
	 * @param specification the specification
	 * @param pageable      the pageable
	 * @return the list
	 */
	@Cacheable(cacheNames = "examsAll")
	public Page<Exam> findAll(ExamSpecification specification, Pageable pageable) {
		return examsRepository.findAll(specification, pageable);
	}


	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "exams", key = "#id")
	public ResponseEntity<Exam> findById(Integer id) {
		Exam exams = examsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));
		return ResponseEntity.ok().body(exams);
	}

	/**
	 * Creates the exam.
	 *
	 * @param exams the exams
	 * @return the exams
	 */
	public ExamDTO createExam(ExamDTO examsDTO) {
		examsDTO.setId(null);
		Course course = coursesRepository.findById(examsDTO.getCourseId())
				.orElseThrow(() -> new ResourceNotFoundException("Course no found with id: " + examsDTO.getCourseId()));

		Exam exam = examMapper.examDTOToExam(examsDTO);
		exam.setCourse(course);

		return examMapper.examToExamDTO(examsRepository.save(exam));
	}

	/**
	 * Update exam.
	 *
	 * @param id    the id
	 * @param exams the exams
	 * @return the response entity
	 */
	@CachePut(cacheNames = "exams", key = "#id")
	public ResponseEntity<Exam> updateExam(Integer id, ExamDTO examsDTO) {
		Exam exams = examsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));

		if (examsDTO.getName() != null) {
			exams.setName(examsDTO.getName());
		}
		if (examsDTO.getCourseId() != null) {
			Course courses = coursesRepository.findById(examsDTO.getCourseId()).orElseThrow(
					() -> new ResourceNotFoundException("Course no found with id: " + examsDTO.getCourseId()));
			exams.setCourse(courses);
		}

		examsRepository.save(exams);

		return ResponseEntity.ok().body(exams);
	}

	/**
	 * Delete exam.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "exams", key = "#id")
	public ResponseEntity<String> deleteExam(Integer id) {
		Exam exams = examsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam not found with id: " + id));

		examsRepository.delete(exams);
		return ResponseEntity.ok().body("Exam deleted successful");
	}

}
