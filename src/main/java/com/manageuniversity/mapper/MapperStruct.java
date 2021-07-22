package com.manageuniversity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.manageuniversity.dto.ClassDTO;
import com.manageuniversity.entity.Class;

@Mapper
public interface MapperStruct {
	
	Class classDTOToClass(ClassDTO classDTO);
	
	@Mapping(source = "teacher.id", target = "teacherId")
	@Mapping(source = "course.id", target = "courseId")
	ClassDTO classToClassDTO(Class class1);
	

}
