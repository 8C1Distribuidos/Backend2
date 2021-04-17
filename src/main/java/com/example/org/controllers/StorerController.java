package com.example.org.controllers;

import com.example.org.model.Storer;
import com.example.org.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "storers")
public class StorerController  {
    protected final UserService userService;


    @Autowired
    public StorerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<Storer>> getStorers(){
        List<Storer> storers = userService.getStorers();
        return ResponseEntity.ok(storers);
    }

    @PostMapping()
    public ResponseEntity<Storer> addStorer(@RequestBody Storer storer){
        Storer storer2 = (Storer) userService.addNewUser(storer, "Almacenist");
        if(storer2 != null){
            return ResponseEntity.ok(storer2);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping()
    public ResponseEntity<Storer> updateStorer(@RequestBody Storer storer){
        Storer storer2 = (Storer) userService.updateUser(storer, "Almacenist");
        if(storer2 != null){
            return ResponseEntity.ok(storer2);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}