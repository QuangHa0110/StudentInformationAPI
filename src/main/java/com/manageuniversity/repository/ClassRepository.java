package com.manageuniversity.repository;

import com.manageuniversity.entity.Class;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Integer>, JpaSpecificationExecutor<Class> {
	@EntityGraph(attributePaths = { "teacher", "course" }, type = EntityGraphType.FETCH)
	public Page<Class> findAll(Specification<Class> spec, Pageable pageable);

	@EntityGraph(attributePaths = { "teacher", "course" }, type = EntityGraphType.FETCH)
	public Page<Class> findAll(Pageable pageable);

}
