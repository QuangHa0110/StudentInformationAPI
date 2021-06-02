package com.manageuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Teachers;

@Repository
public interface TeachersRepository extends JpaRepository<Teachers, Integer> {

}
