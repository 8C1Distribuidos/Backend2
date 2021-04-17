package com.example.org.model;

import lombok.Data;
import lombok.NonNull;
import org.apache.juli.logging.Log;

@Data
public class Login {
    protected String email;
    @NonNull
    protected String password;

    public Login(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Login() {

    }

}
