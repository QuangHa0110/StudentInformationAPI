package com.manageuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Plans;

@Repository
public interface PlansRepository extends JpaRepository<Plans, Integer> {

}
