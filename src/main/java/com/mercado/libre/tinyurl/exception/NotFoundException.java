package com.mercado.libre.tinyurl.exception;

public class NotFoundException extends RuntimeException {

    private String path;

    public NotFoundException(String message, String path) {
        super(message);
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
