package com.manageuniversity.mapper;

import java.util.List;

import com.manageuniversity.dto.UserDTO;
import com.manageuniversity.entity.User;

import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
	
	User userDTOToUser(UserDTO userDTO);
	

	UserDTO userToUserDTO(User user);
	
	List<UserDTO> listUserToListUserDTO(List<User> list);
	
	

}
