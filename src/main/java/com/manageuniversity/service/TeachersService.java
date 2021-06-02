package com.manageuniversity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.manageuniversity.entity.Teachers;
import com.manageuniversity.exception.ResourceNotFoundException;
import com.manageuniversity.repository.TeachersRepository;

@Service
public class TeachersService {
	@Autowired
	private TeachersRepository teachersRepository;

	public List<Teachers> findAll() {
		return teachersRepository.findAll();
	}

	public ResponseEntity<Teachers> findById(int id) {
		Teachers teachers = teachersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher no found with id: " + id));

		return ResponseEntity.ok().body(teachers);
	}

	public Teachers createTeacher(Teachers teachers) {
		return teachersRepository.save(teachers);

	}

	public ResponseEntity<Teachers> updateTeacher(int id, Teachers teachers) {
		Teachers teachers2 = teachersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Teacher no found with id: " + id));

		teachers.setId(teachers2.getId());
		teachersRepository.save(teachers);
		return ResponseEntity.ok().body(teachers);
	}
	
	public ResponseEntity<String> deleteTeacher(int id){
		Teachers teachers = teachersRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Teacher no found with id: "+id));
		teachersRepository.delete(teachers);
		
		return ResponseEntity.ok().body("Teacher deleted with success");
	}

}
