package com.fullstack.customerservice.DBAccessEntities;

import lombok.*;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "address")
    private String address;
    @Column(name = "cash")
    private Float cash;
    @Column(name = "table_number")
    private Integer tableNumber;

    public String toString(){
        return "[first_name: " + firstName +
                ", address: " + address +
                ", cash: " + cash +
                ", table_number: " + tableNumber + "]";
    }

    public boolean equals(Customer customer) {
        return (customer.firstName.equals(this.firstName) &&
                customer.tableNumber.equals(this.tableNumber) &&
                customer.cash.equals(this.cash) &&
                customer.address.equals(this.address));
    }
}