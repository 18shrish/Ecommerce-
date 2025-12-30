package com.test.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_seq_id")
    private Integer orderItemSeqId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnoreProperties({"orderItems", "hibernateLazyInitializer", "handler"})
    private OrderHeader order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "status", length = 30)
    private String status;

    
    
    public OrderItem() {
        // Default constructor
    }
    
    public OrderItem(OrderHeader order, Product product, Integer quantity, String status) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.status = status;
    }


    public Integer getOrderItemSeqId() {
        return orderItemSeqId;
    }

    public void setOrderItemSeqId(Integer orderItemSeqId) {
        this.orderItemSeqId = orderItemSeqId;
    }

    public OrderHeader getOrder() {
        return order;
    }

    public void setOrder(OrderHeader order) {
        this.order = order;
        // Ensure bidirectional relationship
        if (order != null && !order.getOrderItems().contains(this)) {
            order.getOrderItems().add(this);
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
    
   
    
    @Override
    public String toString() {
        return "OrderItem{" +
                "orderItemSeqId=" + orderItemSeqId +
                ", orderId=" + (order != null ? order.getOrderId() : null) +
                ", productId=" + (product != null ? product.getProductId() : null) +
                ", quantity=" + quantity +
                ", status='" + status + '\'' +
                '}';
    }
}