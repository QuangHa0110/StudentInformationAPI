package com.manageuniversity.service;

import com.manageuniversity.dto.RegistrationDTO;
import com.manageuniversity.entity.Class;
import com.manageuniversity.entity.Registration;
import com.manageuniversity.entity.RegistrationId;
import com.manageuniversity.entity.Student;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.mapper.RegistrationMapper;
import com.manageuniversity.repository.ClassRepository;
import com.manageuniversity.repository.RegistrationRepository;
import com.manageuniversity.repository.StudentRepository;
import com.manageuniversity.repository.specification.RegistrationSpecification;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class RegistrationsService.
 */
@Service
public class RegistrationService {

	/** The registrations repository. */

	private RegistrationRepository registrationsRepository;

	private ClassRepository classesRepository;

	private StudentRepository studentsRepository;

	private final RegistrationMapper registrationMapper;

	public RegistrationService(RegistrationRepository registrationsRepository, ClassRepository classesRepository,
			StudentRepository studentsRepository, RegistrationMapper registrationMapper) {
		this.registrationsRepository = registrationsRepository;
		this.classesRepository = classesRepository;
		this.studentsRepository = studentsRepository;
		this.registrationMapper = registrationMapper;
	}

	/*
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "registrationsAll")
	public Page<Registration> findAll(RegistrationSpecification specification, Pageable pageable) {
		return registrationsRepository.findAll(specification, pageable);
	}
	@Cacheable(cacheNames = "registrationsAll")
	public Page<Registration> findAll( Pageable pageable) {
		return registrationsRepository.findAll( pageable);
	}

	/**
	 * Find by registrations id.
	 *
	 * @param classId   the class id
	 * @param studentId the student id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "registration", key = "#registrationsId")
	public ResponseEntity<Registration> findByRegistrationsId(RegistrationId registrationsId) {

		Registration registrations = registrationsRepository.findById(registrationsId)
				.orElseThrow(() -> new ResourceNotFoundException("Registration not found with class id: "
						+ registrationsId.getClasses() + " student id : " + registrationsId.getStudent()));
		return ResponseEntity.ok().body(registrations);
	}

	/**
	 * Creates the registration.
	 *
	 * @param registrations the registrations
	 * @return the registrations
	 */

	public Registration createRegistration(RegistrationDTO registrationsDTO) {
		Class classes = classesRepository.findById(registrationsDTO.getClassId()).orElseThrow(
				() -> new ResourceNotFoundException("Class no found with id: " + registrationsDTO.getClassId()));

		Student student = studentsRepository.findById(registrationsDTO.getStudentId()).orElseThrow(
				() -> new ResourceNotFoundException("Student no found with id: " + registrationsDTO.getStudentId()));

		Registration registration = registrationMapper.registrationDTOToRegistration(registrationsDTO);
		registration.setClasses(classes);
		registration.setStudent(student);
		return registrationsRepository.save(registration);
	}

	/**
	 * Update registration.
	 *
	 * @param classId       the class id
	 * @param studentId     the student id
	 * @param registrations the registrations
	 * @return the response entity
	 */
	@CachePut(cacheNames = "registration", key = "#registrationsId")
	public ResponseEntity<Registration> updateRegistration(RegistrationId registrationsId,
			RegistrationDTO registrationsDTO) {
		Registration registrations = registrationsRepository.findById(registrationsId)
				.orElseThrow(() -> new ResourceNotFoundException("Registration not found with class id: "
						+ registrationsId.getClasses() + " student id : " + registrationsId.getStudent()));

		if (registrationsDTO.getCreateDate() != null) {
			registrations.setCreateDate(registrationsDTO.getCreateDate());
		}
		if (registrationsDTO.getRegisterDate() != null) {
			registrations.setRegisterDay(registrationsDTO.getRegisterDate());
		}
		if (registrationsDTO.getStatus() != null) {
			registrations.setStatus(registrationsDTO.getStatus());
		}
		if (registrationsDTO.getClassId() != null) {
			Class classes = classesRepository.findById(registrationsDTO.getClassId()).orElseThrow(
					() -> new ResourceNotFoundException("Class no found with id: " + registrationsDTO.getClassId()));

			registrations.setClasses(classes);

		}
		if (registrationsDTO.getStudentId() != null) {
			Student students = studentsRepository.findById(registrationsDTO.getStudentId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Student no found with id: " + registrationsDTO.getStudentId()));
			registrations.setStudent(students);
		}

		registrationsRepository.save(registrations);

		return ResponseEntity.ok().body(registrations);

	}

	/**
	 * Delete registration.
	 *
	 * @param classId   the class id
	 * @param studentId the student id
	 * @return the response entity
	 */
	@CacheEvict(cacheNames = "registration", key = "registrationsId")
	public ResponseEntity<String> deleteRegistration(RegistrationId registrationsId) {
		Registration registrations = registrationsRepository.findById(registrationsId)
				.orElseThrow(() -> new ResourceNotFoundException("Registration not found with class id: "
						+ registrationsId.getClasses() + " student id : " + registrationsId.getStudent()));
		registrationsRepository.delete(registrations);
		return ResponseEntity.ok().body("Registration deleted successful");
	}

}
