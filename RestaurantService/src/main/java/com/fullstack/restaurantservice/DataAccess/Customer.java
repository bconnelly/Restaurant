package com.fullstack.restaurantservice.DataAccess;

import lombok.*;
import org.springframework.stereotype.Repository;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Repository
public class Customer {

    private String firstName;

    private String address;

    private Float cash;

    private Integer tableNumber;

    public boolean equals(Customer customer) {
        return (customer.firstName.equals(this.firstName) &&
                customer.tableNumber.equals(this.tableNumber) &&
                customer.cash.equals(this.cash) &&
                customer.address.equals(this.address));
    }
}
