package com.fullstack.restaurantservice.DomainLogic;

import com.fullstack.restaurantservice.DataEntities.CustomerRecord;
import com.fullstack.restaurantservice.DataEntities.OrderRecord;
import com.fullstack.restaurantservice.DataEntities.TableRecord;
import com.fullstack.restaurantservice.RestDataRetrieval.RestFetcher;
import com.fullstack.restaurantservice.Utilities.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class RestaurantLogicTest {
    @Mock
    RestFetcher fetcherMock;

    @Autowired
    RestaurantLogic restaurantLogic;

    List<TableRecord> expectedAllTables = new ArrayList<>(Arrays.asList(
            TableRecord.builder().tableNumber(1).capacity(4).build(),
            TableRecord.builder().tableNumber(2).capacity(4).build(),
            TableRecord.builder().tableNumber(3).capacity(6).build()));

    List<CustomerRecord> expectedAllCustomers = new ArrayList<>(Arrays.asList(
            CustomerRecord.builder().firstName("alice").address("test address1").tableNumber(1).cash(1.00f).build(),
            CustomerRecord.builder().firstName("bob").address("test address2").tableNumber(2).cash(15.25f).build(),
            CustomerRecord.builder().firstName("chuck").address("test address3").tableNumber(2).cash(100.01f).build()
    ));

    @BeforeEach
    void setup() throws EntityNotFoundException {

        ReflectionTestUtils.setField(restaurantLogic, "restFetcher", fetcherMock);

        when(fetcherMock.getAllTables()).thenReturn(expectedAllTables);
        when(fetcherMock.getAllCustomers()).thenReturn(expectedAllCustomers);
    }

    @Test
    void seatCustomer() throws EntityNotFoundException {
        clearInvocations(fetcherMock);

        CustomerRecord expectedSeatedCustomer = CustomerRecord.builder()
                .firstName("dick").address("test address4").cash(9.87f).tableNumber(3).build();

        when(fetcherMock.seatCustomer("dick", "test address4", 9.87f, 3))
                .thenReturn(expectedSeatedCustomer);
        CustomerRecord returnedCustomer = restaurantLogic.seatCustomer("dick", "test address4", 9.87f);

        assert(expectedSeatedCustomer.equals(returnedCustomer));
        verify(fetcherMock, times(1)).getAllTables();
        verify(fetcherMock, times(1)).getAllCustomers();
        verify(fetcherMock, times(1)).seatCustomer(anyString(), anyString(), anyFloat(), anyInt());
    }

    @Test
    void getOpenTablesTest() throws EntityNotFoundException {
        clearInvocations(fetcherMock);

        List<TableRecord> expectedOpenTables = new ArrayList<>(Arrays.asList(
                TableRecord.builder().tableNumber(3).capacity(6).build()));

        List<TableRecord> returnedTables = restaurantLogic.getOpenTables();

        assert(expectedOpenTables.equals(returnedTables));
        verify(fetcherMock, times(1)).getAllTables();
        verify(fetcherMock, times(1)).getAllCustomers();
    }

    @Test
    void submitOrderTest() throws EntityNotFoundException {
        clearInvocations(fetcherMock);

        OrderRecord expectedRecord = OrderRecord.builder()
                .firstName("alice").dish("food").bill(10.00f).tableNumber(1).build();

        CustomerRecord customer = CustomerRecord.builder().firstName("alice").address("test address1")
                .cash(12.00f).tableNumber(1).build();

        when(fetcherMock.getCustomerByName("alice")).thenReturn(customer);
        when(fetcherMock.customerExists("alice")).thenReturn(true);
        when(fetcherMock.submitOrder("alice", "food", 1, 10.00f)).thenReturn(expectedRecord);

        OrderRecord returnedOrder;
        try {
            returnedOrder = restaurantLogic.submitOrder("alice", "food", 1, 10.00f);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }

        assert(expectedRecord.equals(returnedOrder));
        verify(fetcherMock, times(1)).getCustomerByName(anyString());
        verify(fetcherMock, times(1)).customerExists(anyString());
        verify(fetcherMock, times(1)).submitOrder(anyString(), anyString(), anyInt(), anyFloat());
    }
}