package com.example.StockManagement.Model;


import org.springframework.http.HttpStatus;

public class ResponseEntity<T> {
    private T body;
    private HttpStatus httpStatus;
    private String message;

    public ResponseEntity(T body, HttpStatus httpStatus, String message) {
        this.body = body;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
