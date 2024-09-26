package com.example.stayease.service;

import com.example.stayease.dto.UserDto;
import com.example.stayease.entity.User;

import java.util.List;

public interface UserService{

    public UserDto register (User user);

    public String verify(User user);

    public User getUserById(Long id);

}
