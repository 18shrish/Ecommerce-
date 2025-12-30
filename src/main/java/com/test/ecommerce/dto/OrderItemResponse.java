package com.test.ecommerce.dto;

public class OrderItemResponse {

    private Integer orderItemSeqId;
    private Integer productId;
    private String productName;
    private Integer quantity;
    private String status;

    public Integer getOrderItemSeqId() {
        return orderItemSeqId;
    }
    public void setOrderItemSeqId(Integer orderItemSeqId) {
        this.orderItemSeqId = orderItemSeqId;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
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
