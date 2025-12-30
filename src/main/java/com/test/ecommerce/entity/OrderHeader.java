package com.test.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_header")
public class OrderHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_date")
    private LocalDate orderDate;  

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_contact_mech_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ContactMech shippingContactMech;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_contact_mech_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private ContactMech billingContactMech;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JsonIgnoreProperties("order")
    private List<OrderItem> orderItems = new ArrayList<>();

    
    
    public OrderHeader() {
        // Default constructor
    }
    
    public OrderHeader(LocalDate orderDate, Customer customer, 
                       ContactMech shippingContactMech, ContactMech billingContactMech) {
        this.orderDate = orderDate;
        this.customer = customer;
        this.shippingContactMech = shippingContactMech;
        this.billingContactMech = billingContactMech;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ContactMech getShippingContactMech() {
        return shippingContactMech;
    }

    public void setShippingContactMech(ContactMech shippingContactMech) {
        this.shippingContactMech = shippingContactMech;
    }

    public ContactMech getBillingContactMech() {
        return billingContactMech;
    }

    public void setBillingContactMech(ContactMech billingContactMech) {
        this.billingContactMech = billingContactMech;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    // FIXED: Proper setter that maintains bidirectional relationship
    public void setOrderItems(List<OrderItem> orderItems) {
        if (this.orderItems == null) {
            this.orderItems = new ArrayList<>();
        }
        
        // Clear existing items
        this.orderItems.clear();
        
        // Add new items with proper bidirectional relationship
        if (orderItems != null) {
            for (OrderItem item : orderItems) {
                addOrderItem(item);
            }
        }
    }
    
   
    
    public void addOrderItem(OrderItem orderItem) {
        if (orderItem != null) {
            orderItems.add(orderItem);
            orderItem.setOrder(this);
        }
    }
    
    public void removeOrderItem(OrderItem orderItem) {
        if (orderItem != null) {
            orderItems.remove(orderItem);
            orderItem.setOrder(null);
        }
    }
    
    public void clearOrderItems() {
        for (OrderItem item : new ArrayList<>(orderItems)) {
            removeOrderItem(item);
        }
    }

    @Override
    public String toString() {
        return "OrderHeader{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", customerId=" + (customer != null ? customer.getCustomerId() : null) +
                ", shippingContactMechId=" + (shippingContactMech != null ? shippingContactMech.getContactMechId() : null) +
                ", billingContactMechId=" + (billingContactMech != null ? billingContactMech.getContactMechId() : null) +
                ", orderItemsCount=" + (orderItems != null ? orderItems.size() : 0) +
                '}';
    }
}