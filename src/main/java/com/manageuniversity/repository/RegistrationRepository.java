package com.manageuniversity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Registration;
import com.manageuniversity.entity.RegistrationId;

@Repository
public interface RegistrationRepository
		extends JpaRepository<Registration, RegistrationId>, JpaSpecificationExecutor<Registration> {

	@Query(value = "FROM Registration r WHERE r.classes.id = ?1 AND r.student.id =?2")
	Optional<Registration> findByRegistrationsId(Integer classId, Integer studentId);

	@Query(value = "FROM Registration r WHERE r.student.id =?1")
	List<Registration> findByStudentId(Integer id);

	@Query(value = "FROM Registration r WHERE r.classes.id=?1")
	List<Registration> findByClassId(Integer id);

	@EntityGraph(attributePaths = { "student", "classes" }, type = EntityGraphType.FETCH)
	Page<Registration> findAll(Specification<Registration> specification, Pageable pageable);

	@EntityGraph(attributePaths = { "student", "classes" }, type = EntityGraphType.FETCH)
	Page<Registration> findAll(Pageable pageable);

}
