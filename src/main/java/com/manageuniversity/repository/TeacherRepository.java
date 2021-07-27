package com.manageuniversity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Teacher;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Integer>, JpaSpecificationExecutor<Teacher> {

	Page<Teacher> findAll(Specification<Teacher> specification, Pageable pageable);

	Page<Teacher> findAll(Pageable pageable);

}
