package com.example.org.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User extends Login {
    protected Integer id;
    @NonNull
    protected String firstName;
    @NonNull
    protected String paternalName;
    @NonNull
    protected String maternalName;
    protected String photo = "noPicture";
    protected Role role;



    public User(String email, String password) {
        super(email, password);
    }

    public User() {
        super();
    }



}
