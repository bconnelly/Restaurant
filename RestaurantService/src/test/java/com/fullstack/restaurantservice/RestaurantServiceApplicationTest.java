package com.fullstack.restaurantservice;

import com.fullstack.restaurantservice.Utilities.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RestaurantServiceApplicationTest {

    @Autowired
    RestaurantServiceApplication app;
    @Test
    void passThrough() {
    }

    @Test
    void getOrdersForCustomer() {
    }

    @Test
    void getCustomersByDish() {
    }

    @Test
    void submitOrder() throws EntityNotFoundException {
//        app.submitOrder("name", "food", 1, 1.00f);
    }
}