package com.example.UserService.controller;

import com.example.UserService.dto.UserDto;
import com.example.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto){
        UserDto user = userService.addUser(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto> getUserDetailsById(@PathVariable Integer userId){
        return userService.getUserDetails(userId);
    }
}



















