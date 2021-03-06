package com.example.org.controllers;


import com.example.org.exceptions.RequestException;
import com.example.org.model.Login;
import com.example.org.model.User;
import com.example.org.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "login")
public class LoginController  {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    @CrossOrigin()
    public ResponseEntity<User> login(@RequestBody Login login){
        System.out.println(login);
        User user = null;
        try {
            user = loginService.validateLogin(login);
        } catch (RequestException e) {
            if(e.getStatusCode() == 400){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }else if(e.getStatusCode() == 404){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        if(user != null){
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
