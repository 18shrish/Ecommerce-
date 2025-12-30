package com.test.ecommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.ecommerce.entity.Customer;
import com.test.ecommerce.exception.ResourceNotFoundException;
import com.test.ecommerce.repository.CustomerRepository;

import java.util.List;

@Service
@Transactional
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    // Create new customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    
    // Get customer by ID
    public Customer getCustomer(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }
    
    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    
    // Update customer
    public Customer updateCustomer(Integer id, Customer customerDetails) {
        Customer customer = getCustomer(id);
        
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        
        return customerRepository.save(customer);
    }
    
    // Delete customer
    public void deleteCustomer(Integer id) {
        Customer customer = getCustomer(id);
        customerRepository.delete(customer);
    }
    
    // Check if customer exists
    public boolean customerExists(Integer id) {
        return customerRepository.existsById(id);
    }
}