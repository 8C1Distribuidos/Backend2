package com.example.org.controllers;


import com.example.org.model.Login;
import com.example.org.model.User;
import com.example.org.services.LoginService;
import com.example.org.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "login")
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<User> login(@RequestBody Login login){
        User user = loginService.validateLogin(login);
        return ResponseEntity.ok(user);
    }
}
