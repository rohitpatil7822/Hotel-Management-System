package com.example.stayease.controller;

import com.example.stayease.dto.UserDto;
import com.example.stayease.entity.User;
import com.example.stayease.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto>register(@RequestBody User user){

        return new ResponseEntity<>(userService.register(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        Map<String , Object> response = new HashMap<>();
        String token = userService.verify(user);

        response.put("JwtToken" , token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
