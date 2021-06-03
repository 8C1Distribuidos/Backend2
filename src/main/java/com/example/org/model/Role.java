package com.example.org.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Role {
    private Integer id;
    private String role = "Indefinido";
}
