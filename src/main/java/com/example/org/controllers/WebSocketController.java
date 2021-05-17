package com.example.org.controllers;

import com.example.org.model.Greeting;
import com.example.org.model.User;
import com.example.org.model.UserResponse;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;

@Controller
public class WebSocketController {

    ArrayList<User> loggedUsers = new ArrayList<>();
    String ip;
    @MessageMapping("/login")
    @SendTo("/session/logout")
    public String greeting(String s) throws Exception {
        //ip = (String)s.getSessionAttributes().get("ip");
        System.out.println(s);
        return "Hola Miada";
    }
    /*
    @SubscribeMapping("/session/logout")
    public String sendMessage(){
        return "Hola Miada";
    }*/
}

