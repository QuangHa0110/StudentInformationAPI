package com.manageuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Exams;

@Repository
public interface ExamsRepository extends JpaRepository<Exams, Integer> {

}
