package com.manageuniversity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manageuniversity.entity.Registrations;
import com.manageuniversity.entity.RegistrationsId;

@Repository
public interface RegistrationsRepository extends JpaRepository<Registrations, RegistrationsId> {

}
