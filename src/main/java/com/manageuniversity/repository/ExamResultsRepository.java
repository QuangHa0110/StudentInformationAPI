package com.manageuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.ExamResults;

@Repository
public interface ExamResultsRepository extends JpaRepository<ExamResults, Integer> {

}
