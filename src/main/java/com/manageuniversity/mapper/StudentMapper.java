package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.manageuniversity.dto.StudentDTO;
import com.manageuniversity.entity.Student;

@Mapper
public interface StudentMapper {
	
	Student studentDTOToStudent(StudentDTO studentDTO);
	
	StudentDTO studentToStudentDTO(Student student);
	
	List<StudentDTO> listStudentToListStudentDTO(List<Student> list);

}
