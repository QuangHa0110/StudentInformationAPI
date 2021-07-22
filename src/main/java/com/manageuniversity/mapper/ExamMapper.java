package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.manageuniversity.dto.ExamDTO;
import com.manageuniversity.entity.Exam;

@Mapper
public interface ExamMapper {
	Exam examDTOToExam(ExamDTO examDTO);
	
	@Mapping(source = "course.id", target = "courseId")
	ExamDTO examToExamDTO(Exam exam);
	
	List<ExamDTO> listExamToListExamDTO(List<Exam> list);

}
