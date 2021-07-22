package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.manageuniversity.dto.TeacherDTO;
import com.manageuniversity.entity.Teacher;
@Mapper
public interface TeacherMapper {
	
	Teacher teacherDTOToTeacher(TeacherDTO teachersDTO);
	
	TeacherDTO teacherToTeacherDTO(Teacher teachers);
	
	List<TeacherDTO> listTeacherToListTeacherDTO(List<Teacher> list);

}
