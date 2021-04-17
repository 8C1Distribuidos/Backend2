package com.example.org.services;

import com.example.org.externservices.Request;
import com.example.org.model.Role;
import com.example.org.model.Storer;
import com.example.org.model.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {



    public User addNewUser(User user, String role) {
        try {
            List<Role> roles = Request.getJ("roles", Role[].class, false);
            for(Role r: roles){
                if(r.getRole().equals(role)){
                    user.setRole(r);
                    break;
                }
            }
            return (User) Request.postJ("users", user);
        } catch (Exception e) {
            return  null;
        }
    }

    public User updateUser(User user, String role) {
        List<Role> roles = Request.getJ("roles", Role[].class, false);
        for(Role r: roles){
            if(r.getRole().equals(role)){
                user.setRole(r);
                break;
            }
        }
        return (User) Request.putJ("users", user);
    }

    public boolean deleteUser(Integer id) {
        return Request.deleteJ("users", id);
    }

    public String recoverPassword(String email) {

        return null;
    }

    public List<Storer> getStorers() {
        Request<User> request = new Request<>();
        List<Storer> storers = new ArrayList<Storer>();
        List<Storer> returnedStorers = request.getJ("/users", Storer[].class, true);
        for(Storer storer :  returnedStorers){
            if(storer.getRole().getRole().equals("Almacenist")){
                storers.add(storer);
            }
        }
        return storers;
    }
}
