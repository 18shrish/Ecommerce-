package com.test.ecommerce.dto;

import java.time.LocalDate;
import java.util.List;

public class OrderRequest {
    private LocalDate orderDate;
    private Integer customerId;
    private Integer shippingContactMechId;
    private Integer billingContactMechId;
    private List<OrderItemRequest> orderItems;
    
    // Getters and Setters
    public LocalDate getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    
    public Integer getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    
    public Integer getShippingContactMechId() {
        return shippingContactMechId;
    }
    
    public void setShippingContactMechId(Integer shippingContactMechId) {
        this.shippingContactMechId = shippingContactMechId;
    }
    
    public Integer getBillingContactMechId() {
        return billingContactMechId;
    }
    
    public void setBillingContactMechId(Integer billingContactMechId) {
        this.billingContactMechId = billingContactMechId;
    }
    
    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }
}