package com.example.UserService.service.impl;

import com.example.UserService.dto.UserDto;
import com.example.UserService.entity.User;
import com.example.UserService.mapper.UserMapper;
import com.example.UserService.repository.UserRepository;
import com.example.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        User savedUser = userRepository.save(UserMapper.INSTANCE.mapUserDtoToUser(userDto));
        return UserMapper.INSTANCE.mapUserToUserDto(savedUser);
    }

    @Override
    public ResponseEntity<UserDto> getUserDetails(Integer userId) {
        Optional<User> fetchUser = userRepository.findById(userId);

        if (fetchUser.isPresent()){
            return new ResponseEntity<>(UserMapper.INSTANCE.mapUserToUserDto(fetchUser.get()), HttpStatus.OK);
        }

        return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
    }
}

















