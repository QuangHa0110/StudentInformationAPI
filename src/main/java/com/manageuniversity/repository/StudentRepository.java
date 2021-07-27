package com.manageuniversity.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer>, JpaSpecificationExecutor<Student> {

	List<Student> findByFullname(String fullname);

	Page<Student> findAll(Specification<Student> specification, Pageable pageable);

	Page<Student> findAll(Pageable pageable);

}
