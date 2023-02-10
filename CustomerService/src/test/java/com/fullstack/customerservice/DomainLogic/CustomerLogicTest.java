package com.fullstack.customerservice.DomainLogic;

import com.fullstack.customerservice.DBAccessEntities.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class CustomerLogicTest {

    @Autowired
    private CustomerLogic customerLogic;

    @Test
    void getAllCustomers() {
        List<Customer> customers = customerLogic.getAllCustomers();
        assert(customers.get(0).getFirstName().equals("alice"));
        assert(customers.get(1).getFirstName().equals("bob"));
        assert(customers.get(2).getFirstName().equals("chuck"));
        assert(customers.get(3).getFirstName().equals("dave"));
        assert(customers.get(4).getFirstName().equals("ed"));
    }

    @Test
    void seatNewCustomer() {
        Customer newCustomer = Customer.builder().
                firstName("person4")
                .tableNumber(10)
                .cash(12.34f)
                .address("address4").build();
        Customer customer = customerLogic.seatNewCustomer(newCustomer);
        assert(newCustomer.equals(customer));
    }

    @Test
    void bootByFirstName() {
        //failure and checking for entry throws exception
        assert(customerLogic.bootByFirstName("alice"));
    }
}