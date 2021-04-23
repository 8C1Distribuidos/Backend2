package com.example.org.externservices;

import com.example.org.model.Login;
import com.example.org.model.Page3;
import com.example.org.model.Storer;
import com.example.org.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class Request<D> {
    public static final String REST_URL = "http://localhost:9081/";

    public static Object putJ(String link, Object ob) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(ob);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REST_URL + link))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 400) {
                return null;
            }
            return gson.fromJson(response.body(), ob.getClass());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean deleteJ(String s, int id) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REST_URL + s + "?id=" + id))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 404) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static <D> List<D> getJ(String link, Class<D[]> classType, boolean isPage){
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(REST_URL + link))
                .build();
        List<D> list = null;
        try {
            HttpResponse<String> response = HttpClient.newBuilder()
                    .authenticator(new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    "user",
                                    "123".toCharArray()
                            );
                        }
                    }).build().send(request, HttpResponse.BodyHandlers.ofString());

            JsonParser jsonParser = new JsonParser();
            if (isPage){
                JsonObject body = (JsonObject) jsonParser.parse(response.body());
                D[] arrays = new Gson().fromJson(body.getAsJsonArray("content"), classType);
                list = new ArrayList<D>(Arrays.asList(arrays));
                Page3<D> userResponseEntity = new Gson().fromJson(response.body(), Page3.class);
                userResponseEntity.getTotalPages();
                list = new ArrayList<D>(Arrays.asList(arrays));
            }else {
                D[] arrays = new Gson().fromJson(response.body(), classType);
                list = new ArrayList<D>(Arrays.asList(arrays));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Object find(String link, int id, Class<?> dClass){
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(REST_URL +link+"/find?id=" + id))
                .build();
        try {
            HttpResponse<String> response = HttpClient.newBuilder()
                    .authenticator(new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(
                                    "user",
                                    "123".toCharArray()
                            );
                        }
                    }).build().send(request, HttpResponse.BodyHandlers.ofString());
            Gson gson = new Gson();
            return gson.fromJson(response.body(), dClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static Object postJ(String link, Object object) throws Exception {
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(REST_URL + link))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 400 || response.statusCode() == 404) {
                throw new Exception();
            }
            Object ob = gson.fromJson(response.body(), object.getClass());
            System.out.println(response.body());
            return ob;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
