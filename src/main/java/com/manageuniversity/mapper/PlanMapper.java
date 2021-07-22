package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.manageuniversity.dto.PlanDTO;
import com.manageuniversity.entity.Plan;

@Mapper
public interface PlanMapper {
	
	Plan planDTOToPlan(PlanDTO planDTO);
	
	@Mapping(source = "course.id", target = "courseId")
	PlanDTO planToPlanDTO(Plan plan);
	
	
	List<PlanDTO> listPlanToListPlanDTO(List<Plan> list);
	
	

}
