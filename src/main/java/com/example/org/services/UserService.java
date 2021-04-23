package com.example.org.services;

import com.example.org.externservices.Request;
import com.example.org.model.Role;
import com.example.org.model.Storer;
import com.example.org.model.User;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
            String encryptation = Encrypter.encode(user.getPassword());
            user.setPassword(encryptation);
            return (User) Request.postJ("users?pageSize=55555", user);
        } catch (Exception e) {
            return  null;
        }
    }

    public User updateUser(User user, String role) {
        user.setPassword(Encrypter.encode(user.getPassword()));
        List<Role> roles = Request.getJ("roles", Role[].class, false);
        for(Role r: roles){
            if(r.getRole().equals(role)){
                user.setRole(r);
                break;
            }
        }
        User user1 = (User) Request.putJ("users", user);
        user1.setPassword(null);
        return user1;
    }

    public boolean deleteUser(Integer id) {
        return Request.deleteJ("users", id);
    }

    public String recoverPassword(String email) {
        MailService mailService = null;
        List<User> returnedUsers = Request.getJ("users?pageSize=55555", User[].class, true);
        User user = null;
        for (User u: returnedUsers){
            if(u.getEmail().equals(email)){
                user = u;
                break;
            }
        }
        if(user == null){
            return null;
        }
        String newPassword = "";
        String banc = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random r = new Random();
        for (int i = 0; i < 16; i++)
        {
            int n = r.nextInt(banc.length());
            char a = banc.charAt(n);
            newPassword += a;
        }
        String encryptedPassword = Encrypter.encode(newPassword);
        try {
            mailService = new MailService();
            mailService.draftEmail(email,
                    "Recuperación de Contraseña",
                    "Su nueva contraseña es: " + newPassword);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        try {
            mailService.sendEmail();
            user.setPassword(encryptedPassword);
            Request.putJ("users", user);
            return "si";
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Storer> getStorers() {
        Request<User> request = new Request<>();
        List<Storer> storers = new ArrayList<Storer>();
        List<Storer> returnedStorers = request.getJ("users?size=55555", Storer[].class, true);
        for(Storer storer :  returnedStorers){
            if(storer.getRole().getRole().equals("Almacenist")){
                storers.add(storer);
            }
        }
        return storers;
    }
}
