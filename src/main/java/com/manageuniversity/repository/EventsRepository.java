package com.manageuniversity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Events;

@Repository
public interface EventsRepository extends JpaRepository<Events, Integer> {
	@Query(value = "FROM Events e WHERE e.classes.id = ?1")
	List<Events> getListEventsByClassId(int classId);
	
	List<Events> findByHappenDate(String happenDate);

}
