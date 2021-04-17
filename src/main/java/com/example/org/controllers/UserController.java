package com.example.org.controllers;

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
    public ResponseEntity<User> registerNewUser(@RequestBody User user){
        User user2= userService.addNewUser(user, "Cliente");
        if(user2 != null){
            return ResponseEntity.ok(user2);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/recovery")
    public ResponseEntity<String> recoverPassword(@RequestBody String email){
        String email2 = userService.recoverPassword(email);
        return ResponseEntity.ok(email2);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user){
        User user2 = userService.updateUser(user, "Cliente");
        if(user2 != null){
            return ResponseEntity.ok(user2);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(int id){
        if(userService.deleteUser(id)){
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
