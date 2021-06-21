package com.manageuniversity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Registrations;
import com.manageuniversity.entity.RegistrationsId;

@Repository
public interface RegistrationsRepository extends JpaRepository<Registrations, RegistrationsId> {
	
	@Query(value="FROM Registrations r WHERE r.classes.id = ?1 AND r.students.id =?2")
	Optional<Registrations> findByRegistrationsId(Integer classId, Integer studentId);
	
	@Query(value="FROM Registrations r WHERE r.students.id =?1")
	List<Registrations> findByStudentId(Integer id);
	
	@Query(value="FROM Registrations r WHERE r.classes.id=?1")
	List<Registrations> findByClassId(Integer id);
	  


}
