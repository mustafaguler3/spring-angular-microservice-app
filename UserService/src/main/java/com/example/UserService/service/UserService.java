package com.example.UserService.service;

import com.example.UserService.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {

    UserDto addUser(UserDto userDto);
    ResponseEntity<UserDto> getUserDetails(Integer userId);

}
