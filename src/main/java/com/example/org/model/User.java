package com.example.org.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User extends NoRoleUser {
    protected Role role;

    public User(Login login) {
        super(login.getEmail(), login.getPassword());
    }

    public User() {
        super();
    }

    public User(NoRoleUser noRoleUser) {
        super(noRoleUser);
    }

}
