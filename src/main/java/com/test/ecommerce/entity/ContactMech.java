package com.test.ecommerce.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "contact_mech")
public class ContactMech {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contactMechId;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;
    private String phoneNumber;
    private String email;
    
    
    public Integer getContactMechId() {
        return contactMechId;
    }
    
    public void setContactMechId(Integer contactMechId) {
        this.contactMechId = contactMechId;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String getStreetAddress() {
        return streetAddress;
    }
    
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
}