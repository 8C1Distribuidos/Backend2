package com.example.org.controllers;


import com.example.org.model.Login;
import com.example.org.model.User;
import com.example.org.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "login")
public class LoginController  {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<User> login(@RequestBody Login login){
        User user = loginService.validateLogin(login);
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
