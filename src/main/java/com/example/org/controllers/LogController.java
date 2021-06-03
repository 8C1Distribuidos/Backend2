package com.example.org.controllers;


import com.example.org.model.Log;
import com.example.org.model.Storer;
import com.example.org.services.FileWritter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "log")
public class LogController {

    @GetMapping()
    @CrossOrigin()
    public ResponseEntity<List<Log>> getFullLog(){
        List<Log> logs = FileWritter.Read();
        return ResponseEntity.ok(logs);
    }
}
