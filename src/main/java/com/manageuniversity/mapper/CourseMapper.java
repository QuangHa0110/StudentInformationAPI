package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.manageuniversity.dto.CourseDTO;
import com.manageuniversity.entity.Course;

@Mapper
public interface CourseMapper {
	Course courseDTOToCourse(CourseDTO courseDTO);
	
	
	CourseDTO courseToCourseDTO(Course course);
	
	
	List<CourseDTO> listCourseToListCourseDTO(List<Course> list);

}
