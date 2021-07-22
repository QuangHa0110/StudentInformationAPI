package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.manageuniversity.dto.ClassDTO;
import com.manageuniversity.entity.Class;


@Mapper
public interface ClassMapper {
	
	Class classDTOToClass(ClassDTO classDTO);

	@Mapping(source = "teacher.id", target = "teacherId")
	@Mapping(source = "course.id", target = "courseId")
	ClassDTO classToClassDTO(Class class1);
	
	//List<Class> listClassDTOToListClass(List<ClassDTO> list);
	
	List<ClassDTO> listClassToListClassDTO(List<Class> list);
	
//	Teacher mapTeacherId(Integer teacherId);
//	
//	Course mapCourseId(Integer courseId);

}
