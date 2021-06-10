package com.example.org.controllers;

import com.example.org.exceptions.RequestException;
import com.example.org.model.Log;
import com.example.org.model.NoRoleUser;
import com.example.org.model.Storer;
import com.example.org.services.FileWritter;
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
    public ResponseEntity<List<Storer>> getStorers(@RequestParam String email){
        List<Storer> storers = userService.getStorers();
        Log log = new Log();
        log.setDescription("Obtencion del de los almacenista");
        log.setStatus("Correcto");
        log.setUser(email);
        FileWritter.Write(log);
        return ResponseEntity.ok(storers);
    }

    @PostMapping()
    @CrossOrigin()
    public ResponseEntity<Storer> addStorer(@RequestBody NoRoleUser storer, @RequestParam String email){
        System.out.println(storer);
        Storer storer2 = new Storer(storer);
        try {
            storer2 = (Storer) userService.addNewUser(storer2, "Almacenist");
        } catch (RequestException e) {
            return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
        }
        if(storer2 != null){
            Log log = new Log();
            log.setDescription("Creación del almacenista");
            log.setStatus("Correcto");
            log.setUser(email);
            FileWritter.Write(log);
            return ResponseEntity.ok(storer2);
        }
        Log log = new Log();
        log.setDescription("Intento de creación de almacenista");
        log.setStatus("Incorrecto");
        log.setUser(storer.getEmail());
        FileWritter.Write(log);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping()
    @CrossOrigin()
    public ResponseEntity<Storer> updateStorer(@RequestBody Storer storer, @RequestParam String email){
        System.out.println(storer);
        Storer storer2 = null;
        try {
            storer2 = (Storer) userService.updateUser(storer);
        } catch (RequestException e) {
            if(e.getStatusCode() == 504){
                Log log = new Log();
                log.setDescription("Base de datos perdida fallido");
                log.setStatus("Correcto");
                log.setUser(email);
                FileWritter.Write(log);
                return new ResponseEntity<>(HttpStatus.GATEWAY_TIMEOUT);
            }
        }
        if(storer2 != null){
            Log log = new Log();
            log.setDescription("Actualización del almacenista " + storer.toString() +   " a " + storer2.toString());
            log.setStatus("Correcto");
            log.setUser(email);
            FileWritter.Write(log);
            return ResponseEntity.ok(storer2);
        }
        Log log = new Log();
        log.setDescription("Intento de actualización de almacenista para" + storer.getEmail());
        log.setStatus("Incorrecto");
        log.setUser(email);
        FileWritter.Write(log);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
