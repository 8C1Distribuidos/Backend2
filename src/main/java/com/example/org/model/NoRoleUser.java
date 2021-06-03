package com.example.org.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoRoleUser extends Login{
    protected Integer id;
    protected String firstName = "Primer nombre";
    protected String paternalName  = "Apellido paterno";
    protected String maternalName = "Apellido materno";
    protected String photo;

    public NoRoleUser(String email, String password){
        this.email = email;
        this.password = password;
    }

    public NoRoleUser(NoRoleUser noRoleUser) {
        this.id = noRoleUser.getId();
        this.firstName = noRoleUser.getFirstName();
        this.paternalName = noRoleUser.getPaternalName();
        this.maternalName = noRoleUser.getMaternalName();
        this.photo = noRoleUser.getPhoto();
        this.password = noRoleUser.getPassword();
        this.email = noRoleUser.getEmail();
    }

}
