package com.backend.oilmanagement.controller;

import com.backend.oilmanagement.dto.SignUpDTO;
import com.backend.oilmanagement.entity.User;
import com.backend.oilmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody SignUpDTO requestBody) {

        UserDetailsService user = (UserDetailsService) userService.loadUserByUsername(requestBody.getUserName());

        if (user != null) {
            return new ResponseEntity<>("User already exists and verified.", HttpStatus.BAD_REQUEST);
        }

        userService.createUser(requestBody);
        return new ResponseEntity<>("User created.", HttpStatus.CREATED);
    }
}