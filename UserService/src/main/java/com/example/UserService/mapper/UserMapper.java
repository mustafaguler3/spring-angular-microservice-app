package com.example.UserService.mapper;

import com.example.UserService.dto.UserDto;
import com.example.UserService.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapUserDtoToUser(UserDto userDto);

    UserDto mapUserToUserDto(User user);
}
















