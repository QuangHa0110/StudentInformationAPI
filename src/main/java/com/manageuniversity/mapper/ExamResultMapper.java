package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.manageuniversity.dto.ExamResultDTO;
import com.manageuniversity.entity.ExamResult;

@Mapper
public interface ExamResultMapper {
	
	ExamResult examResultDTOToExamResult(ExamResultDTO examResultDTO);
	
	@Mapping(source = "student.id", target = "studentId")
	@Mapping(source = "exam.id", target = "examId")
	@Mapping(source = "classes.id", target = "classId")
	ExamResultDTO examResultToExamResultDTO(ExamResult examResult);
	
	List<ExamResultDTO> listExamResultToListExamResultDTO(List<ExamResult> list);
	

}
