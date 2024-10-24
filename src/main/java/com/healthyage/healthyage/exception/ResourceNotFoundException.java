package com.healthyage.healthyage.exception;

public class ResourceNotFoundException extends Exception {
    private String resource;

    public static ResourceNotFoundException createWith(String resource) {
        return new ResourceNotFoundException(resource);
    }

    private ResourceNotFoundException(String resource) {
        this.resource = resource;
    }

    @Override
    public String getMessage() {
        return "Resource '" + resource + "' not found";
    }
}