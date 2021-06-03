package com.example.org.model;

import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;

@Data
public class User extends NoRoleUser {
    protected Role role = new Role();

    public User(Login login) {
        super(login.getEmail(), login.getPassword());
    }

    public User() {
        super();
    }

    public User(NoRoleUser noRoleUser) {
        super(noRoleUser);
    }

    @Override
    public String toString() {
        return "Usuario " + "Nombre: " + this.firstName + " Apellidos " + this.paternalName + " " + this.maternalName + " Rol: " + this.role.getRole() + " Email: " + this.getEmail();
    }
}
