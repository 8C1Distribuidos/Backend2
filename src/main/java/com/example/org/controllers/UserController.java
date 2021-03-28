package com.example.org.controllers;

import com.example.org.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.org.services.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        User user2= userService.addNewUser(user);
        return ResponseEntity.ok(user2);
    }
}
