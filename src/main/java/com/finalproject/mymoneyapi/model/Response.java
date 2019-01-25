package com.finalproject.mymoneyapi.model;

import java.util.*;

public class Response<T> {

    private int status;
    private String message;
    private List<T> body;

    public Response() {
        this.body = new ArrayList<T>();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getBody() {
        return body;
    }

    public void setBody(List<T> body) {
        this.body = body;
    }

    public void addToBody(T object) {
        this.body.add(object);
    }
}
