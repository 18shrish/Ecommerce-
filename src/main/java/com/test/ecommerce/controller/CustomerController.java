package com.test.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.test.ecommerce.entity.Customer;
import com.test.ecommerce.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    
    @Autowired
    private CustomerService customerService; 
    
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Integer id) {
        Customer customer = customerService.getCustomer(id);
        return ResponseEntity.ok(customer);
    }
    
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Integer id, 
                                                   @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(updatedCustomer);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}