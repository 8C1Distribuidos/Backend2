package com.example.org.controllers;


import com.example.org.exceptions.RequestException;
import com.example.org.model.Log;
import com.example.org.model.Login;
import com.example.org.model.User;
import com.example.org.services.FileWritter;
import com.example.org.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                Log log = new Log();
                log.setDescription("Intento de inicio de sesión de "+  login.getEmail() + " fallido");
                log.setStatus("Correcto");
                log.setUser(login.getEmail());
                FileWritter.Write(log);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }else if(e.getStatusCode() == 404){
                Log log = new Log();
                log.setDescription("Intento de inicio de sesión de "+  login.getEmail() + " fallido");
                log.setStatus("Correcto");
                log.setUser(login.getEmail());
                FileWritter.Write(log);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Log log = new Log();
            log.setDescription("Intento de inicio de sesión de "+  login.getEmail() + " fallido");
            log.setStatus("Correcto");
            log.setUser(login.getEmail());
            FileWritter.Write(log);
        }
        for(User u : loggedUsers){
            if(u.getId() == user.getId()){
                Log log = new Log();
                log.setDescription("Intento de inicio de sesión de "+  login.getEmail() + " fallido" + "usuario en uso");
                log.setStatus("Correcto");
                log.setUser(login.getEmail());
                FileWritter.Write(log);
                return new ResponseEntity<>(user, HttpStatus.IM_USED);
            }
        }
        if(user != null){
            loggedUsers.add(user);
            Log log = new Log();
            log.setDescription("Inicio de sesión de "+  user.toString());
            log.setStatus("Correcto");
            log.setUser(user.getEmail());
            FileWritter.Write(log);
            return ResponseEntity.ok(user);
        }
        Log log = new Log();
        log.setDescription("Inicio de sesión de "+  login.getEmail());
        log.setStatus("Incorrecto");
        log.setUser(login.getEmail());
        FileWritter.Write(log);
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
                Log log = new Log();
                log.setDescription("Expulsión del usuario "+  user.toString());
                log.setStatus("Correcto");
                log.setUser(user.getEmail());
                FileWritter.Write(log);
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
        Log log = new Log();
        log.setDescription("Cierre de inicio de sesión del usuario "+  user.toString());
        log.setStatus("Correcto");
        log.setUser(user.getEmail());
        FileWritter.Write(log);
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
