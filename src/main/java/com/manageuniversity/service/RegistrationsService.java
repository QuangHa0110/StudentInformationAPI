package com.manageuniversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.entity.Registrations;
import com.manageuniversity.entity.RegistrationsId;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.RegistrationsRepository;

// TODO: Auto-generated Javadoc
/**
 * The Class RegistrationsService.
 */
@Service
public class RegistrationsService {

	/** The registrations repository. */
	@Autowired
	private RegistrationsRepository registrationsRepository;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@Cacheable(cacheNames = "registrationsAll")
	public List<Registrations> findAll() {
		return registrationsRepository.findAll();
	}
	@Cacheable(cacheNames = "registrationsPage", key = "#pageNumber")
	public List<Registrations> findPaginated(int pageNumber, int pageSize){
		Sort sort = Sort.by("registerDay").descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
		Page<Registrations> pageResult = registrationsRepository.findAll(pageable);
		return pageResult.toList();
	}
	/**
	 * Find by registrations id.
	 *
	 * @param classId   the class id
	 * @param studentId the student id
	 * @return the response entity
	 */
	@Cacheable(cacheNames = "registrations", key = "#registrationsId")
	public ResponseEntity<Registrations> findByRegistrationsId(RegistrationsId registrationsId) {
		
		Registrations registrations = registrationsRepository.findById(registrationsId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Registration not found with class id: " + registrationsId.getClasses() + " student id : " + registrationsId.getStudents()));
		return ResponseEntity.ok().body(registrations);
	}

	/**
	 * Creates the registration.
	 *
	 * @param registrations the registrations
	 * @return the registrations
	 */
	
	public Registrations createRegistration(Registrations registrations) {
		return registrationsRepository.save(registrations);
	}

	/**
	 * Update registration.
	 *
	 * @param classId       the class id
	 * @param studentId     the student id
	 * @param registrations the registrations
	 * @return the response entity
	 */
	@CachePut(cacheNames = "registrations", key = "#registrationsId")
	public ResponseEntity<Registrations> updateRegistration(RegistrationsId registrationsId,
			Registrations registrations) {
		Registrations registrations2 = registrationsRepository.findById(registrationsId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Registration not found with class id: " + registrationsId.getClasses() + " student id : " + registrationsId.getStudents()));
		registrations.setStudents(registrations2.getStudents());
		registrations.setClasses(registrations2.getClasses());
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
	@CacheEvict(cacheNames = "registrations", key = "registrationsId")
	public ResponseEntity<String> deleteRegistration(RegistrationsId registrationsId) {
		Registrations registrations = registrationsRepository.findById(registrationsId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"Registration not found with class id: " + registrationsId.getClasses() + " student id : " + registrationsId.getStudents()));
		registrationsRepository.delete(registrations);
		return ResponseEntity.ok().body("Registration deleted successful");
	}
	@Cacheable(cacheNames = "registationsClassId", key = "#classId")
	public List<Registrations> findByClassId(Integer classId){
		return registrationsRepository.findByClassId(classId);
	}
	
	@Cacheable(cacheNames = "registationsStudentId", key = "#studentId")
	public List<Registrations> findByStudentId(Integer studentId){
		return registrationsRepository.findByStudentId(studentId);
	}

}
