package com.example.demo;

import jakarta.persistence.Embeddable;
import lombok.Data;
@Data
@Embeddable
public class Address {
    private String city;
    private String street;
    private String pincode;
}
