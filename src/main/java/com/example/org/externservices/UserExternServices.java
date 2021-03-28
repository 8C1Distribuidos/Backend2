package com.example.org.externservices;

import com.example.org.model.Login;
import com.example.org.model.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserExternServices {
    private static final String BD_URL = "http://localhost:3000/";

    /*public static User postLogin(Login login){
        Gson gson = new Gson();
        String jsonString = gson.toJson(login);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BD_URL + "login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            User returnUser = gson.fromJson(response.body(), Employee.class);
            System.out.println(response.body());
            return returnEmployee;
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static User postUser(User user){
        Gson gson = new Gson();
        String jsonString = gson.toJson(user);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BD_URL + "user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            User returnUser = gson.fromJson(response.body(), User.class);
            System.out.println(response.body());
            return returnUser;
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
