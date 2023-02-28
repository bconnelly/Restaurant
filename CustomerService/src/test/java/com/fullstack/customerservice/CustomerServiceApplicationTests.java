package com.fullstack.customerservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CustomerServiceApplicationTests {

    @Autowired
    CustomerServiceApplication app = new CustomerServiceApplication();

    @Test
    void getCustomerByFirstNameTest(){
    }
}
