package com.example.stayease.service.implementation;

import com.example.stayease.config.jwtService.JwtService;
import com.example.stayease.entity.enums.Role;
import com.example.stayease.exceptions.ResourceAlreadyExistsException;
import com.example.stayease.exceptions.ResourceNotFoundException;
import com.example.stayease.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.stayease.dto.UserDto;
import com.example.stayease.entity.User;
import com.example.stayease.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public UserDto register(User user) {

        if (userRepository.existsByUserName(user.getUserName())){

            throw new ResourceAlreadyExistsException(HttpStatus.CONFLICT,"Email: "+user.getUserName()+" is already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole() != null ? user.getRole() : Role.CUSTOMER);
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser.getFirstName(),savedUser.getLastName(),savedUser.getUserName(),savedUser.getRole());
    }

    @Override
    public String verify(User user) {

        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

        if (authentication.isAuthenticated()) {
            User userDetails = userRepository.findByUserName(user.getUserName())
                    .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"User not found with username: " + user.getUserName()));
            return jwtService.generateToken(userDetails);
        }

        throw new IllegalStateException("Invalid email or password");
    }


    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND,"User not found with id: "+id));
    }




}
