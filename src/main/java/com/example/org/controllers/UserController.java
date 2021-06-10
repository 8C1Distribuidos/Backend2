package com.example.org.controllers;

import com.example.org.exceptions.RequestException;
import com.example.org.model.Email;
import com.example.org.model.Log;
import com.example.org.model.User;
import com.example.org.services.FileWritter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.org.services.UserService;

@RestController
@RequestMapping(path = "users")
public class UserController {
    protected final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @CrossOrigin()
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        System.out.println(user);
        User user2= null;
        try {
            user2 = userService.addNewUser(user, "Cliente");
        } catch (RequestException e) {
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
        }
        if(user2 != null){
            Log log = new Log();
            log.setDescription("Creación de usuario " + user2.toString());
            log.setStatus("Correcto");
            log.setUser(user.getEmail());
            FileWritter.Write(log);
            return ResponseEntity.ok().build();
        }
        Log log = new Log();
        log.setDescription("Intento de creación de usuario " + user.toString());
        log.setStatus("Incorrecto");
        log.setUser(user.getEmail());
        FileWritter.Write(log);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find")
    public ResponseEntity<User> findUser(int id) {
        User user2 = null;
        try {
            user2 = userService.find(id);
        } catch (RequestException e) {
            if(e.getStatusCode() == 504){
                Log log = new Log();
                log.setDescription("Base de datos perdida");
                log.setStatus("Incorrecto");
                log.setUser("Indefinido");
                FileWritter.Write(log);
                return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
            }
        }
        if(user2 != null){
            user2.setPassword(null);
            return ResponseEntity.ok(user2);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/recovery")
    @CrossOrigin()
    public ResponseEntity<String> recoverPassword(@RequestBody Email email){
        System.out.println(email);
        String email2 = userService.recoverPassword(email.getEmail());
        if(email2 == null){
            Log log = new Log();
            log.setDescription("Intendo de recuperación de contraseña para " + email2);
            log.setStatus("Incorrecto");
            log.setUser(email.getEmail());
            FileWritter.Write(log);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Log log = new Log();
        log.setDescription("Recuperación de contraseña para " + email2);
        log.setStatus("Correcto");
        log.setUser(email2);
        FileWritter.Write(log);
        return ResponseEntity.ok(email2);
    }

    @PutMapping
    @CrossOrigin()
    public ResponseEntity<User> updateUser(@RequestBody User user){
        System.out.println(user);
        User user2 = null;
        try {
            user2 = userService.updateUser(user);
        } catch (RequestException e) {
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
        }
        if(user2 != null){
            Log log = new Log();
            log.setDescription("Actualización del usuario  " + user.toString() + " a " + user2.toString());
            log.setStatus("Correcto");
            log.setUser(user2.getEmail());
            FileWritter.Write(log);
            return ResponseEntity.ok(user2);
        }
        Log log = new Log();
        log.setDescription("Intento de actualización de   " + user.toString());
        log.setStatus("Incorrecto");
        log.setUser(user.getEmail());
        FileWritter.Write(log);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    @CrossOrigin()
    public ResponseEntity<?> deleteUser(@RequestParam String email,@RequestParam Integer id){
        System.out.println(id);
        if(userService.deleteUser(id)){
            Log log = new Log();
            log.setDescription("Eliminación del usuario id " + id);
            log.setStatus("Correcto");
            log.setUser(email);
            FileWritter.Write(log);
            return ResponseEntity.ok().build();
        }
        Log log = new Log();
        log.setDescription("Intento de elimiación del usuario id " + id);
        log.setStatus("Incorrecto");
        log.setUser(email);
        FileWritter.Write(log);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
