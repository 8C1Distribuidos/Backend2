package com.example.org.services;

import com.example.org.exceptions.RequestException;
import com.example.org.externservices.Request;
import com.example.org.model.Login;
import com.example.org.model.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.List;

@Service
public class LoginService {

    public User validateLogin(Login login) throws RequestException {
        User user = new User(login);
        user.setPassword(Encrypter.encode(login.getPassword()));
        try {
            user = (User)Request.postJ("users/verify", user);
            if(user == null){
                return null;
            }
            user.setPassword(null);
            return user;
        } catch (RequestException e) {
            throw e;
        }
    }
}
