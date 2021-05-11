package com.example.org.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestException extends Exception{
    private int statusCode;

    public RequestException(String message, int statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
