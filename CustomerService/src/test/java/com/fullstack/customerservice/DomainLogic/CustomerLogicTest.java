package com.fullstack.customerservice.DomainLogic;

import com.fullstack.customerservice.DBAccessEntities.Customer;
import com.fullstack.customerservice.Repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerLogicTest {

    @Autowired
    private CustomerLogic customerLogic;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeAll
    void setup(){
        ReflectionTestUtils.setField(customerLogic, "customerRepository", customerRepository);
    }

    @Test
    void insertCustomerTest() {
        Customer customerToSave = Customer.builder().firstName("alice").address("test address1")
                .cash(12.34f).tableNumber(1).build();

        when(customerRepository.save(any(Customer.class))).thenReturn(customerToSave);

        Customer returnedCustomer = customerLogic.insertCustomer(
                "alice", "test address1", 12.34f, 1);

        assert(customerToSave.equals(returnedCustomer));
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

}