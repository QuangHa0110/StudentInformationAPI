package com.manageuniversity.service;

import com.manageuniversity.dto.ExamResultDTO;
import com.manageuniversity.entity.Class;
import com.manageuniversity.entity.Exam;
import com.manageuniversity.entity.ExamResult;
import com.manageuniversity.entity.Student;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.mapper.ExamResultMapper;
import com.manageuniversity.repository.ClassRepository;
import com.manageuniversity.repository.ExamRepository;
import com.manageuniversity.repository.ExamResultRepository;
import com.manageuniversity.repository.StudentRepository;
import com.manageuniversity.repository.specification.ExamResultSpecification;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class ExamResultsService.
 */
@Service
public class ExamResultService {

	/** The exam results repository. */

	private ExamResultRepository examResultsRepository;

	private ClassRepository classesRepository;

	private StudentRepository studentsRepository;

	private ExamRepository examsRepository;

	private final ExamResultMapper examResultMapper;

	public ExamResultService(ExamResultRepository examResultsRepository, ClassRepository classesRepository,
			StudentRepository studentsRepository, ExamRepository examsRepository, ExamResultMapper examResultMapper) {
		this.examResultsRepository = examResultsRepository;
		this.classesRepository = classesRepository;
		this.studentsRepository = studentsRepository;
		this.examsRepository = examsRepository;
		this.examResultMapper = examResultMapper;
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "examResultsAll")
	public Page<ExamResult> findAll(ExamResultSpecification specification, Pageable pageable) {
		return examResultsRepository.findAll(specification, pageable);
	}

	@Cacheable(cacheNames = "examResultsAll")
	public Page<ExamResult> findAll(Pageable pageable) {
		return examResultsRepository.findAll(pageable);
	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "examResult", key = "#id")
	public ResponseEntity<ExamResult> findById(Integer id) {
		ExamResult examResults = examResultsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam result not found with id: " + id));

		return ResponseEntity.ok().body(examResults);
	}

	/**
	 * Creates the exam result.
	 *
	 * @param examResults the exam results
	 * @return the exam results
	 */
	public ExamResultDTO createExamResult(ExamResultDTO examResultsDTO) {
		examResultsDTO.setId(null);
		Class classes = classesRepository.findById(examResultsDTO.getClassId()).orElseThrow(
				() -> new ResourceNotFoundException("Class no found with id: " + examResultsDTO.getClassId()));

		Student students = studentsRepository.findById(examResultsDTO.getStudentId()).orElseThrow(
				() -> new ResourceNotFoundException("Student no found with id: " + examResultsDTO.getStudentId()));

		Exam exam = examsRepository.findById(examResultsDTO.getExamId()).orElseThrow(
				() -> new ResourceNotFoundException("Exam no found with id: " + examResultsDTO.getExamId()));
		ExamResult examResults = examResultMapper.examResultDTOToExamResult(examResultsDTO);
		examResults.setClasses(classes);
		examResults.setStudent(students);
		examResults.setExam(exam);

		return examResultMapper.examResultToExamResultDTO(examResultsRepository.save(examResults));
	}

	/**
	 * Update exam result.
	 *
	 * @param id          the id
	 * @param examResults the exam results
	 * @return the response entity
	 */
	@CachePut(cacheNames = "examResult", key = "#id")
	public ResponseEntity<ExamResultDTO> updateExamResult(Integer id, ExamResultDTO examResultsDTO) {
		ExamResult examResults = examResultsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam result not found with id: " + id));

		if (examResultsDTO.getNote() != null) {
			examResults.setNote(examResultsDTO.getNote());

		}
		if (examResultsDTO.getResultDate() != null) {
			examResults.setResultDate(examResultsDTO.getResultDate());
		}
		if (examResultsDTO.getScore() != null) {
			examResults.setScore(examResultsDTO.getScore());
		}
		if (examResultsDTO.getClassId() != null) {
			Class classes = classesRepository.findById(examResultsDTO.getClassId()).orElseThrow(
					() -> new ResourceNotFoundException("Class no found with id: " + examResultsDTO.getClassId()));

			examResults.setClasses(classes);
		}
		if (examResultsDTO.getStudentId() != null) {
			Student students = studentsRepository.findById(examResultsDTO.getStudentId()).orElseThrow(
					() -> new ResourceNotFoundException("Student no found with id: " + examResultsDTO.getStudentId()));
			examResults.setStudent(students);
		}
		if (examResultsDTO.getExamId() != null) {
			Exam exams = examsRepository.findById(examResultsDTO.getExamId()).orElseThrow(
					() -> new ResourceNotFoundException("Exam no found with id: " + examResultsDTO.getExamId()));
			examResults.setExam(exams);
		}

		examResultsRepository.save(examResults);

		return ResponseEntity.ok().body(examResultMapper.examResultToExamResultDTO(examResults));
	}

	/**
	 * Delete exam result.
	 *
	 * @param id the id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "examResult", key = "#id")
	public ResponseEntity<String> deleteExamResult(Integer id) {
		ExamResult examResults = examResultsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Exam result not found with id: " + id));
		examResultsRepository.delete(examResults);
		return ResponseEntity.ok().body("Exam result deleted successful");
	}

}
