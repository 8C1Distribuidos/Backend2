package com.example.org.controllers;

import com.example.org.model.NoRoleUser;
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
    @CrossOrigin()
    public ResponseEntity<List<Storer>> getStorers(){
        List<Storer> storers = userService.getStorers();
        return ResponseEntity.ok(storers);
    }

    @PostMapping()
    @CrossOrigin()
    public ResponseEntity<Storer> addStorer(@RequestBody NoRoleUser storer){
        System.out.println(storer);
        Storer storer2 = new Storer(storer);
        storer2 = (Storer) userService.addNewUser(storer2, "Almacenist");
        if(storer2 != null){
            return ResponseEntity.ok(storer2);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping()
    @CrossOrigin()
    public ResponseEntity<Storer> updateStorer(@RequestBody Storer storer){
        System.out.println(storer);
        Storer storer2 = (Storer) userService.updateUser(storer);
        if(storer2 != null){
            return ResponseEntity.ok(storer2);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
