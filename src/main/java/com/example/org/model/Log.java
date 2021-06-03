package com.example.org.model;

import com.example.org.services.Formatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log implements Serializable {
    private String date = Formatter.formatDate(LocalDateTime.now());
    private String description;
    private String identifier = "Usuarios";
    private String status;
    private String user;
}
