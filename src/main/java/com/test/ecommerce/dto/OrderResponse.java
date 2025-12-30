package com.test.ecommerce.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderResponse {
    
    private Integer orderId;
    private LocalDate orderDate;
    private CustomerResonse customer;
    private Integer customerId;
    private Integer shippingContactMechId;
    private Integer billingContactMechId;
    private List<OrderItemResponse> orderItems = new ArrayList<>();
    

    public OrderResponse() {
        // Default constructor
    }
    
    public OrderResponse(Integer orderId, LocalDate orderDate, CustomerResonse customer, 
                        Integer shippingContactMechId, Integer billingContactMechId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.shippingContactMechId = shippingContactMechId;
        this.billingContactMechId = billingContactMechId;
    }
    
    public Integer getOrderId() {
        return orderId;
    }
    
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    
    public LocalDate getOrderDate() {
        return orderDate;
    }
    
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    
    public CustomerResonse getCustomer() {
        return customer;
    }
    
    public void setCustomer(CustomerResonse customer) {
        this.customer = customer;
        if (customer != null) {
            this.customerId = customer.getCustomerId();
        }
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
    
    public List<OrderItemResponse> getOrderItems() {
        return orderItems;
    }
    
    public void setOrderItems(List<OrderItemResponse> orderItems) {
        if (orderItems != null) {
            this.orderItems = orderItems;
        }
    }
    
    public void addOrderItem(OrderItemResponse item) {
        if (item != null) {
            this.orderItems.add(item);
        }
    }
    
    public void removeOrderItem(OrderItemResponse item) {
        if (item != null) {
            this.orderItems.remove(item);
        }
    }
    
    @Override
    public String toString() {
        return "OrderResponse{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", customer=" + (customer != null ? customer.toString() : "null") +
                ", customerId=" + customerId +
                ", shippingContactMechId=" + shippingContactMechId +
                ", billingContactMechId=" + billingContactMechId +
                ", orderItemsCount=" + orderItems.size() +
                '}';
    }
}