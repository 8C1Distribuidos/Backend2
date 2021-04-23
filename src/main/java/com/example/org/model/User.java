package com.example.org.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User extends Login {
    protected Integer id;
    protected String firstName;
    protected String paternalName;
    protected String maternalName;
    protected String photo = "noPicture";
    protected Role role;

    public User(Login login) {
        super(login.getEmail(), login.getPassword());
    }

    public User() {
        super();
    }



}
