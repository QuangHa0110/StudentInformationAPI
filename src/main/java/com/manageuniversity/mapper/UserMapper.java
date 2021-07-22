package com.manageuniversity.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.manageuniversity.dto.UserDTO;
import com.manageuniversity.entity.User;

@Mapper
public interface UserMapper {
	
	User userDTOToUser(UserDTO userDTO);
	
	@Mapping(source = "role.name", target = "roleName")
	UserDTO userToUserDTO(User user);
	
	List<UserDTO> listUserToListUserDTO(List<User> list);
	
	

}
