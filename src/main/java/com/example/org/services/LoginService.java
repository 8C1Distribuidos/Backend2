package com.example.org.services;

import com.example.org.externservices.Request;
import com.example.org.model.Login;
import com.example.org.model.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.List;

@Service
public class LoginService {

    public User validateLogin(Login login){
        Request<User> request = new Request<>();
        List<User> users = request.getJ(Request.BD_URL + "/users", new User());
        for(User user: users ){
            if(user.getEmail().equals(login.getEmail()) && user.getPassword().equals(login.getPassword())){
                return user;
            }
        }
        System.out.println(users.get(0).getFirstName());
        return null;
    }
}
