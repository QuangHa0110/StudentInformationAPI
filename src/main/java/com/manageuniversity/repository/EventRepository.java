package com.manageuniversity.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
	@Query(value = "FROM Event e WHERE e.classes.id = ?1")
	List<Event> getListEventsByClassId(int classId);
	
	List<Event> findByHappenDate(Date happenDate);
	
	@Query(value = "FROM Event e WHERE e.classes.name = ?1")
	List<Event> getListEventsByClassName(String className);
	
	@EntityGraph(attributePaths = {"classes"}, type=EntityGraphType.FETCH)
	public Page<Event> findAll(Specification<Event> spec, Pageable pageable);

}
