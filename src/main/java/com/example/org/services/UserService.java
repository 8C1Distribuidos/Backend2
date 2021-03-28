package com.example.org.services;

import com.example.org.externservices.UserExternServices;
import com.example.org.model.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

@Service
public class UserService {



    public User addNewUser(User user) {
        return  UserExternServices.postUser(user);
    }
}
