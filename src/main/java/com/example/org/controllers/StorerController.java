package com.example.org.controllers;

import com.example.org.model.Storer;
import com.example.org.model.User;
import com.example.org.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "storer")
public class StorerController extends UserController{

    @Autowired
    public StorerController(UserService userService) {
        super(userService);
    }

    @GetMapping
    public ResponseEntity<List<Storer>> getStorers(@RequestBody Storer storer){
        List<Storer> storers = userService.getStorers();
        return ResponseEntity.ok(storers);
    }

    @PostMapping
    public ResponseEntity<Storer> addStorer(@RequestBody Storer storer){
        Storer storer2 = (Storer) userService.addNewUser(storer);
        return ResponseEntity.ok(storer2);
    }

    @PutMapping
    public ResponseEntity<Storer> updateUser(@RequestBody Storer storer){
        Storer storer2 = (Storer) userService.updateUser(storer);
        return ResponseEntity.ok(storer2);
    }
}
