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
    public static final String BD_URL = "http://localhost:9081/";
    private static WebClient webClient = WebClient.create(BD_URL);

    public static Object putJ(String link, Object ob) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(ob);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return gson.fromJson(response.body(), ob.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteJ(String s) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(s))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();
        try {
            client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public <D> List<D> getJ(String link, Object ob){
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(link))
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
            Page3<D> userResponseEntity = new Gson().fromJson(response.body(), getObjectClass(ob));
            list = userResponseEntity.getContent();
            //productsList = new ArrayList<PaperProduct>(Arrays.asList(products));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Object postJ(String link, Object object){
        Gson gson = new Gson();
        String jsonString = gson.toJson(object);
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Object ob = gson.fromJson(response.body(), object.getClass());
            System.out.println(response.body());
            return ob;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Type getObjectClass(Object ob){
        if (User.class.equals(ob.getClass())) {
            return new TypeToken<Page3<User>>(){}.getType();
        }else if(Storer.class.equals(ob.getClass())){
            return new TypeToken<Page3<Storer>>(){}.getType();
        }
        return null;
    }
}
