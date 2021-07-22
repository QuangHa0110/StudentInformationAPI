package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.manageuniversity.dto.RegistrationDTO;
import com.manageuniversity.entity.Registration;

@Mapper
public interface RegistrationMapper {
	Registration registrationDTOToRegistration(RegistrationDTO registrationDTO);
	
	@Mapping(source = "student.id", target = "studentId")
	@Mapping(source = "classes.id", target = "classId")
	RegistrationDTO registrationToRegistrationDTO(Registration registration);
	
	List<RegistrationDTO> listRegistrationToListRegistrationDTO(List<Registration> list);

}
