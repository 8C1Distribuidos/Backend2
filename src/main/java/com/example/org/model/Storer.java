package com.example.org.model;

import lombok.Data;

@Data
public class Storer extends User{

    public Storer(){
        super();
    }
    public Storer(NoRoleUser noRoleUser){
        super(noRoleUser);
    }

    @Override
    public String toString() {
        return "Usuario " + "Nombre: " + this.firstName + " Apellidos " + this.paternalName + " " + this.maternalName + " Rol: " + this.role.getRole();
    }
}
