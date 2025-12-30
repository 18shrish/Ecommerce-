// ResourceNotFoundException.java
package com.test.ecommerce.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}