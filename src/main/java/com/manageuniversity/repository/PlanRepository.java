package com.manageuniversity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Plan;

@Repository
public interface PlanRepository extends JpaRepository<Plan, Integer>, JpaSpecificationExecutor<Plan> {
	@EntityGraph(attributePaths = { "course" }, type = EntityGraphType.FETCH)
	Page<Plan> findAll(Specification<Plan> specification, Pageable pageable);

	@EntityGraph(attributePaths = { "course" }, type = EntityGraphType.FETCH)
	Page<Plan> findAll(Pageable pageable);

}
