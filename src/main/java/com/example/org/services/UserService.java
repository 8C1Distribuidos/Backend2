package com.example.org.services;

import com.example.org.externservices.Request;
import com.example.org.model.Storer;
import com.example.org.model.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {



    public User addNewUser(User user) {
        return (User) Request.postJ(Request.BD_URL+"users", user);
    }

    public User updateUser(User user) {
        return (User) Request.putJ(Request.BD_URL+"users", user);
    }

    public void deleteUser(Integer id) {
        Request.deleteJ(Request.BD_URL+"users?id="+ String.valueOf(id));
    }

    public String recoverPassword(String email) {

        return null;
    }

    public List<Storer> getStorers() {
        Request<User> request = new Request<>();
        List<Storer> storers = new ArrayList<Storer>();
        List<Storer> returnedStorers = request.getJ(Request.BD_URL + "/users", new Storer());
        for(Storer storer :  returnedStorers){
            if(storer.getRole().equals("Almacenista")){
                storers.add(storer);
            }
        }
        return storers;
    }
}
