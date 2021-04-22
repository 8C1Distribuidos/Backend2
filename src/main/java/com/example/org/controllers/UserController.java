package com.example.org.controllers;

import com.example.org.model.Email;
import com.example.org.model.User;
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
        User user2= userService.addNewUser(user, "Cliente");
        if(user2 != null){
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/recovery")
    @CrossOrigin()
    public ResponseEntity<String> recoverPassword(@RequestBody Email email){
        System.out.println(email);
        String email2 = userService.recoverPassword(email.getEmail());
        if(email2 == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(email2);
    }

    @PutMapping
    @CrossOrigin()
    public ResponseEntity<User> updateUser(@RequestBody User user){
        System.out.println(user);
        User user2 = userService.updateUser(user, "Cliente");
        if(user2 != null){
            return ResponseEntity.ok(user2);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    @CrossOrigin()
    public ResponseEntity<?> deleteUser(int id){
        System.out.println(id);
        if(userService.deleteUser(id)){
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
