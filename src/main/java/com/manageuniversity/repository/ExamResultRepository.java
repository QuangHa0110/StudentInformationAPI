package com.manageuniversity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.ExamResult;

@Repository
public interface ExamResultRepository extends JpaRepository<ExamResult, Integer>, JpaSpecificationExecutor<ExamResult> {
	@EntityGraph(attributePaths = { "student", "exam", "classes" }, type = EntityGraphType.FETCH)
	public Page<ExamResult> findAll(Specification<ExamResult> specification, Pageable pageable);

	@EntityGraph(attributePaths = { "student", "exam", "classes" }, type = EntityGraphType.FETCH)
	public Page<ExamResult> findAll(Pageable pageable);

}
