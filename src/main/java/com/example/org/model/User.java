package com.example.org.model;

import java.time.LocalDate;

public class User extends Login {
    Integer id;
    String firstName;
    String paternalName;
    String maternalName;
    String photo;
    String birthDate;

    public User(String email, String password) {
        super(email, password);
    }

    public User() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPaternalName() {
        return paternalName;
    }

    public void setPaternalName(String paternalName) {
        this.paternalName = paternalName;
    }

    public String getMaternalName() {
        return maternalName;
    }

    public void setMaternalName(String maternalName) {
        this.maternalName = maternalName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public LocalDate getBirthDate() {
        return LocalDate.parse(this.birthDate);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }


}
