package com.fullstack.restaurantservice.DomainLogic;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
public class RestaurantLogicTest {

    @Autowired
    RestaurantLogic restaurantLogic;

    @Before
    void setup(){
//        restaurantLogic = new RestaurantLogic(customerServerGetAll, orderServer, tableServer);
    }

    @Test
    void seatCustomer(){
        System.out.println(restaurantLogic.seatCustomer("test", "test", 1.00f));
    }

    @Test
    void getOpenTablesTest() {
//        System.out.println(restaurantLogic.getOpenTables());
        System.out.println(restaurantLogic.getOpenTables());
    }
    @Test
    void submitOrderTest(){
//        System.out.println(restaurantLogic.submitOrder("chad", "food", 5, 1.23f));
    }

    @Test
    void customerExistsTest(){

    }

//    @Test
//    void getOpenTablesMockito() {
//        List<Table> tables = new ArrayList<>(){{
//            add(Table.builder().tableNumber(1).capacity(2).build());
//            add(Table.builder().tableNumber(2).capacity(4).build());
//            add(Table.builder().tableNumber(3).capacity(4).build());
//            add(Table.builder().tableNumber(4).capacity(6).build());
//            add(Table.builder().tableNumber(5).capacity(6).build());
//        }};
//
//        List<Customer> customers = new ArrayList<>(){{
//            add(Customer.builder().firstName("person1").address("example").cash(1.23f).tableNumber(1).build());
//            add(Customer.builder().firstName("person2").address("example").cash(10.00f).tableNumber(2).build());
//            add(Customer.builder().firstName("person3").address("example").cash(9.87f).tableNumber(2).build());
//            add(Customer.builder().firstName("person4").address("example").cash(20.00f).tableNumber(2).build());
//            add(Customer.builder().firstName("person5").address("example").cash(3.21f).tableNumber(1).build());
//        }};
//
//        when(tableWebClientMock.get()).thenReturn(tableRequestHeadersUriSpecMock);
//        when(tableRequestHeadersUriSpecMock.uri(anyString())).thenReturn(tableRequestHeadersSpecMock);
//        when(tableRequestBodyUriSpecMock.uri(anyString())).thenReturn(tableRequestBodySpecMock);
//        when(tableRequestBodySpecMock.header(any(), any())).thenReturn(tableRequestBodySpecMock);
//        when(tableRequestBodySpecMock.body(any())).thenReturn(tableRequestHeadersSpecMock);
//        when(tableRequestHeadersSpecMock.retrieve()).thenReturn(tableResponseSpecMock);
//        when(tableResponseSpecMock.bodyToMono
//                (ArgumentMatchers.<Class<List<Table>>>notNull())).thenReturn(Mono.just(tables));

////        when(customerWebClientMock.get()).thenReturn(customerRequestHeadersUriSpecMock);
////        when(customerRequestBodyUriSpecMock.uri(anyString())).thenReturn(customerRequestBodySpecMock);
////        when(customerRequestBodySpecMock.header(any(), any())).thenReturn(customerRequestBodySpecMock);
////        when(customerRequestBodySpecMock.body(any())).thenReturn(customerRequestHeadersSpecMock);
////        when(customerRequestHeadersSpecMock.retrieve()).thenReturn(customerResponseSpecMock);
////        when(customerResponseSpecMock.bodyToMono
////                (ArgumentMatchers.<Class<List<Customer>>>notNull())).thenReturn(Mono.just(customers));



//        restaurantLogic = new RestaurantLogic(customerWebClientMock, null, tableWebClientMock);
//        List<Table> returnedTables = restaurantLogic.getOpenTables();
//
//        verify(restaurantLogic).getOpenTables();
//
//        verify(tableWebClientMock).get();
//        verify(tableRequestHeadersUriSpecMock).uri(anyString());
//        verify(tableRequestBodySpecMock).body(any());
//        verify(tableRequestHeadersSpecMock).retrieve();
//        verify(tableResponseSpecMock).bodyToMono(Class.class);
//
//        verify(customerWebClientMock).get();
//        verify(customerRequestHeadersUriSpecMock).uri(anyString());
//        verify(customerRequestBodySpecMock).body(any());
//        verify(customerRequestHeadersSpecMock).retrieve();
//        verify(customerResponseSpecMock).bodyToMono(Class.class);
//    }
}