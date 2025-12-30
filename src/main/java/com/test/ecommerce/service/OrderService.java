package com.test.ecommerce.service;

import com.test.ecommerce.dto.*;
import com.test.ecommerce.entity.*;
import com.test.ecommerce.exception.ResourceNotFoundException;
import com.test.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderHeaderRepository orderHeaderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ContactMechRepository contactMechRepository;

    @Autowired
    private ProductRepository productRepository;

    // ================= CREATE ORDER =================
    public OrderResponse createOrder(OrderRequest orderRequest) {
        // Validate customer exists
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found with id: " + orderRequest.getCustomerId()));

        // Validate shipping contact exists
        ContactMech shippingContact = contactMechRepository.findById(orderRequest.getShippingContactMechId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Shipping contact not found with id: " + orderRequest.getShippingContactMechId()));

        // Validate billing contact exists
        ContactMech billingContact = contactMechRepository.findById(orderRequest.getBillingContactMechId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Billing contact not found with id: " + orderRequest.getBillingContactMechId()));

        // Create order header
        OrderHeader orderHeader = new OrderHeader();
        orderHeader.setOrderDate(orderRequest.getOrderDate());
        orderHeader.setCustomer(customer);
        orderHeader.setShippingContactMech(shippingContact);
        orderHeader.setBillingContactMech(billingContact);

        // Create order items using helper method
        for (OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id: " + itemRequest.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setStatus(itemRequest.getStatus());
            
            // Use helper method to maintain bidirectional relationship
            orderHeader.addOrderItem(orderItem);
        }

        OrderHeader savedOrder = orderHeaderRepository.save(orderHeader);
        return mapToOrderResponse(savedOrder);
    }

    // ================= GET ORDER =================
    public OrderResponse getOrder(Integer orderId) {
        OrderHeader order = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));

        // Items are loaded automatically due to cascade and fetch
        return mapToOrderResponse(order);
    }

    // ================= UPDATE ORDER =================
    public OrderResponse updateOrder(Integer orderId, OrderRequest orderRequest) {
        OrderHeader existingOrder = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));

        // Update customer if changed
        if (orderRequest.getCustomerId() != null) {
            Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Customer not found with id: " + orderRequest.getCustomerId()));
            existingOrder.setCustomer(customer);
        }

        // Update shipping contact if changed
        if (orderRequest.getShippingContactMechId() != null) {
            ContactMech shipping = contactMechRepository.findById(orderRequest.getShippingContactMechId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Shipping contact not found with id: " + orderRequest.getShippingContactMechId()));
            existingOrder.setShippingContactMech(shipping);
        }

        // Update billing contact if changed
        if (orderRequest.getBillingContactMechId() != null) {
            ContactMech billing = contactMechRepository.findById(orderRequest.getBillingContactMechId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Billing contact not found with id: " + orderRequest.getBillingContactMechId()));
            existingOrder.setBillingContactMech(billing);
        }

        // Update order date if provided
        if (orderRequest.getOrderDate() != null) {
            existingOrder.setOrderDate(orderRequest.getOrderDate());
        }

        // Handle order items update carefully to avoid orphan removal issue
        if (orderRequest.getOrderItems() != null && !orderRequest.getOrderItems().isEmpty()) {
            // Clear existing items
            existingOrder.clearOrderItems();
            
            // Add new items
            for (OrderItemRequest itemRequest : orderRequest.getOrderItems()) {
                Product product = productRepository.findById(itemRequest.getProductId())
                        .orElseThrow(() -> new ResourceNotFoundException(
                                "Product not found with id: " + itemRequest.getProductId()));

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(itemRequest.getQuantity());
                orderItem.setStatus(itemRequest.getStatus());
                
                // Use helper method
                existingOrder.addOrderItem(orderItem);
            }
        }

        OrderHeader updatedOrder = orderHeaderRepository.save(existingOrder);
        return mapToOrderResponse(updatedOrder);
    }

    // ================= DELETE ORDER =================
    public void deleteOrder(Integer orderId) {
        OrderHeader order = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));

        // Cascade.ALL + orphanRemoval=true should handle this automatically
        // But we'll do it manually to be safe
        order.clearOrderItems();
        orderHeaderRepository.delete(order);
    }

    // ================= ADD ORDER ITEM =================
    public OrderItemResponse addOrderItem(Integer orderId, OrderItemRequest itemRequest) {
        OrderHeader order = orderHeaderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order not found with id: " + orderId));

        Product product = productRepository.findById(itemRequest.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + itemRequest.getProductId()));

        OrderItem item = new OrderItem();
        item.setProduct(product);
        item.setQuantity(itemRequest.getQuantity());
        item.setStatus(itemRequest.getStatus());
        
        // Use helper method to maintain bidirectional relationship
        order.addOrderItem(item);

        // Save the order (cascade will save the item)
        orderHeaderRepository.save(order);
        
        // Return the newly created item
        OrderItem savedItem = order.getOrderItems().stream()
                .filter(i -> i.getProduct().getProductId().equals(product.getProductId()))
                .filter(i -> i.getQuantity().equals(itemRequest.getQuantity()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Failed to retrieve saved order item"));

        return mapToOrderItemResponse(savedItem);
    }

    // ================= UPDATE ORDER ITEM =================
    public OrderItemResponse updateOrderItem(Integer orderId, Integer orderItemSeqId, OrderItemRequest request) {
        OrderItem item = orderItemRepository.findById(orderItemSeqId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order item not found with id: " + orderItemSeqId));

        // Verify the item belongs to the specified order
        if (!item.getOrder().getOrderId().equals(orderId)) {
            throw new ResourceNotFoundException("Item does not belong to order " + orderId);
        }

        // Update product if changed
        if (request.getProductId() != null) {
            Product product = productRepository.findById(request.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id: " + request.getProductId()));
            item.setProduct(product);
        }

        // Update quantity if provided
        if (request.getQuantity() != null) {
            item.setQuantity(request.getQuantity());
        }

        // Update status if provided
        if (request.getStatus() != null) {
            item.setStatus(request.getStatus());
        }

        OrderItem updatedItem = orderItemRepository.save(item);
        return mapToOrderItemResponse(updatedItem);
    }

    // ================= DELETE ORDER ITEM =================
    public void deleteOrderItem(Integer orderId, Integer orderItemSeqId) {
        OrderItem item = orderItemRepository.findById(orderItemSeqId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Order item not found with id: " + orderItemSeqId));

        // Optional: verify order ID in DB
        if (!item.getOrder().getOrderId().equals(orderId)) {
            throw new ResourceNotFoundException("Item does not belong to order " + orderId);
        }

        // Delete directly
        orderItemRepository.delete(item);
    }


    // ================= MAPPERS =================
    private OrderResponse mapToOrderResponse(OrderHeader order) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(order.getOrderId());
        response.setOrderDate(order.getOrderDate());
        
        // Customer info
        if (order.getCustomer() != null) {
            CustomerResonse customer = new CustomerResonse();
            customer.setCustomerId(order.getCustomer().getCustomerId());
            customer.setFirstName(order.getCustomer().getFirstName());
            customer.setLastName(order.getCustomer().getLastName());
            response.setCustomer(customer);
        }
        
        // Shipping contact info
        if (order.getShippingContactMech() != null) {
            response.setShippingContactMechId(order.getShippingContactMech().getContactMechId());
        }
        
        // Billing contact info
        if (order.getBillingContactMech() != null) {
            response.setBillingContactMechId(order.getBillingContactMech().getContactMechId());
        }

        // Order items
        List<OrderItemResponse> itemResponses = new ArrayList<>();
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                itemResponses.add(mapToOrderItemResponse(item));
            }
        }
        response.setOrderItems(itemResponses);
        
        return response;
    }

    private OrderItemResponse mapToOrderItemResponse(OrderItem item) {
        OrderItemResponse response = new OrderItemResponse();
        response.setOrderItemSeqId(item.getOrderItemSeqId());
        response.setProductId(item.getProduct().getProductId());
        response.setProductName(item.getProduct().getProductName());
        response.setQuantity(item.getQuantity());
        response.setStatus(item.getStatus());
        return response;
    }
}