package com.example.org.controllers;


import com.example.org.exceptions.RequestException;
import com.example.org.model.Login;
import com.example.org.model.User;
import com.example.org.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "login")
public class LoginController  {

    ArrayList<User> loggedUsers  = new ArrayList();
    public Map<String, SseEmitter> emitters = new HashMap<>();

    @Autowired
    private LoginService loginService;


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
        for(User u : loggedUsers){
            if(u.getId() == user.getId()){
                return new ResponseEntity<>(user, HttpStatus.IM_USED);
            }
        }
        if(user != null){
            loggedUsers.add(user);
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @CrossOrigin()
    @RequestMapping(path = "/kick-user")
    public void kickUser(@RequestBody User user){
        SseEmitter sseEmitter = emitters.get(user.getId().toString());
        if(sseEmitter != null){
            try {
                sseEmitter.send(SseEmitter.event().name("logout").data("Bai puto"));
                loggedUsers.removeIf(p -> p.getId().equals(user.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @PostMapping
    @CrossOrigin()
    @RequestMapping(path = "/logout")
    public ResponseEntity<User> logout(@RequestBody User user){
        System.out.println("Closed sesion " + user.getId());
        loggedUsers.removeIf(p -> p.getId().equals(user.getId()));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin()
    @RequestMapping(value = "/subscribe", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@RequestParam String userID){
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sendInitEvent(sseEmitter);
        emitters.put(userID, sseEmitter);
        sseEmitter.onCompletion(() -> emitters.remove(sseEmitter));
        return sseEmitter;
    }

    private void sendInitEvent(SseEmitter sseEmitter){
        try {
            sseEmitter.send(SseEmitter.event().name("INIT"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
