package com.test.ecommerce.dto;

public class OrderItemRequest {
    private Integer productId;
    private Integer quantity;
    private String status;
    
    // Getters and Setters
    public Integer getProductId() {
        return productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}