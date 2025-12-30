package com.test.ecommerce.exception;

public class ErrorResponse {
    private int statusCode;
    private String message;
    private long timestamp;
    
    // Constructor with 2 parameters
    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }
    
    // Constructor with 3 parameters
    public ErrorResponse(int statusCode, String message, long timestamp) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    // No-argument constructor
    public ErrorResponse() {
        this.timestamp = System.currentTimeMillis();
    }
    
    // Getters and Setters
    public int getStatusCode() {
        return statusCode;
    }
    
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}