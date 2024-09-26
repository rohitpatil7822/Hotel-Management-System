package com.example.stayease.dto;


import com.example.stayease.entity.enums.Role;
import lombok.Data;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

    // Constructor
    public UserDto(String firstName, String lastName, String email , Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
    }
}
