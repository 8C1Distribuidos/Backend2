package com.example.org.services;

import com.example.org.externservices.Request;
import com.example.org.model.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class UserService {



    public User addNewUser(User user) {
        User returnedUser = (User) Request.postJ(Request.BD_URL+"users", user);
        return  returnedUser;//UserExternServices.postUser(user);
    }
}
