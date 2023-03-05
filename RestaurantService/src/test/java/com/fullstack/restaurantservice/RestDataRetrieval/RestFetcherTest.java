package com.fullstack.restaurantservice.RestDataRetrieval;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import com.fullstack.restaurantservice.DataEntities.CustomerRecord;

import com.fullstack.restaurantservice.DataEntities.OrderRecord;
import com.fullstack.restaurantservice.DataEntities.TableRecord;
import com.fullstack.restaurantservice.Utilities.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class RestFetcherTest {

    @Mock
    RestTemplate template;

    @Autowired
    RestFetcher restFetcher;

    @BeforeEach
    void setup(){
        ReflectionTestUtils.setField(restFetcher, "template", template);
    }

    @Test
    void getAllCustomers() throws EntityNotFoundException {
        CustomerRecord[] expectedResponse = {CustomerRecord.builder().firstName("test person")
                        .address("test address").cash(12.34f).tableNumber(1).build(),
                CustomerRecord.builder().firstName("another person")
                        .address("another address").cash(32.10f).tableNumber(2).build()};

        when(template.getForObject(anyString(), eq(CustomerRecord[].class)))
                .thenReturn(expectedResponse);

        List<CustomerRecord> response = restFetcher.getAllCustomers();
        verify(template, times(1)).getForObject(anyString(), eq(CustomerRecord[].class));
        assertEquals(new ArrayList<>(Arrays.asList(expectedResponse)), response);
    }

    @Test
    void getCustomerByName() throws EntityNotFoundException {
        CustomerRecord expectedResponse = CustomerRecord.builder().firstName("test person")
                .address("test address").cash(9.87f).tableNumber(1).build();

        when(template.getForObject(anyString(), eq(CustomerRecord.class))).thenReturn(expectedResponse);

        CustomerRecord response = restFetcher.getCustomerByName("test person");

        verify(template, times(1)).getForObject(anyString(), eq(CustomerRecord.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    void customerExists() {
        Boolean expectedResponse = true;

        when(template.getForObject(anyString(), eq(Boolean.class))).thenReturn(expectedResponse);

        Boolean response = restFetcher.customerExists("test");

        verify(template, times(1)).getForObject(anyString(), eq(Boolean.class));
        assertEquals(expectedResponse, response);
    }

    @Test
    void seatCustomer() {
        CustomerRecord newCustomerRecord = CustomerRecord.builder()
                .firstName("test person").address("test address")
                .cash(54.32f).tableNumber(1).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        when(template.postForObject(anyString(), eq(request), eq(CustomerRecord.class))).thenReturn(newCustomerRecord);

        CustomerRecord returnedCustomer = restFetcher.seatCustomer(newCustomerRecord.firstName(),
                newCustomerRecord.address(), newCustomerRecord.cash(), newCustomerRecord.tableNumber());

        verify(template, times(1)).postForObject(anyString(), eq(request), eq(CustomerRecord.class));
        assertEquals(newCustomerRecord, returnedCustomer);
    }

    @Test
    void submitOrder() {
        OrderRecord newOrderRecord = OrderRecord.builder().firstName("test person")
                .dish("test dish").bill(1.23f).tableNumber(1).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> request = new HttpEntity<>(headers);

        when(template.postForObject(anyString(), eq(request), eq(OrderRecord.class))).thenReturn(newOrderRecord);

        OrderRecord returnedOrder = restFetcher.submitOrder(newOrderRecord.firstName(),
                newOrderRecord.dish(), newOrderRecord.tableNumber(), newOrderRecord.bill());

        verify(template).postForObject(anyString(), eq(request), eq(OrderRecord.class));
        assertEquals(newOrderRecord, returnedOrder);
    }

    @Test
    void getAllTables() throws EntityNotFoundException {
        TableRecord[] expectedTables = {TableRecord.builder().tableNumber(1).capacity(2).build(),
                                    TableRecord.builder().tableNumber(2).capacity(2).build(),
                                    TableRecord.builder().tableNumber(3).capacity(4).build()};

        when(template.getForObject(anyString(), eq(TableRecord[].class))).thenReturn(expectedTables);

        List<TableRecord> returnedTables = restFetcher.getAllTables();

        verify(template, times(1)).getForObject(anyString(), eq(TableRecord[].class));
        assertEquals(new ArrayList<>(Arrays.asList(expectedTables)), returnedTables);
    }

    @Test
    void bootCustomer() {
        CustomerRecord misbehavingCustomer = CustomerRecord.builder()
                .firstName("alice").address("123 whatever st").cash(32.10f).tableNumber(1).build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);

        when(template.postForObject(anyString(), eq(request), eq(CustomerRecord.class))).thenReturn(misbehavingCustomer);

        CustomerRecord returnedCustomer = restFetcher.bootCustomer("alice");

        verify(template, times(1)).postForObject(anyString(), eq(request), eq(CustomerRecord.class));
        assertEquals(misbehavingCustomer, returnedCustomer);
    }
}