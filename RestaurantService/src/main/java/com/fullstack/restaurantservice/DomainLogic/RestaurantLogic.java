package com.fullstack.restaurantservice.DomainLogic;

import com.fullstack.restaurantservice.DataEntities.*;
import com.fullstack.restaurantservice.RestDataRetrieval.RestFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class RestaurantLogic {

    public RestaurantLogic(){}

    @Autowired
    private RestFetcher restFetcher;

    public CustomerRecord seatCustomer(String firstName, String address, Float cash){
        List<TableRecord> openTables = getOpenTables();

        if(openTables == null || openTables.isEmpty()) return null;

        return restFetcher.seatCustomer(firstName, address, cash, openTables.get(0).tableNumber());
    }

    public List<TableRecord> getOpenTables()  {

        List<TableRecord> allTables = restFetcher.getAllTables();
        List<CustomerRecord> allCustomers = restFetcher.getAllCustomers();

        if(allTables.isEmpty()) return null;

        for(CustomerRecord customer:allCustomers){
            allTables.removeIf(n -> Objects.equals(n.tableNumber(), customer.tableNumber()));
        }

        return allTables;
    }

    public OrderRecord submitOrder(String firstName, String dish, Integer tableNumber, Float bill){
        if(!restFetcher.customerExists(firstName)) return null;

        CustomerRecord customer = restFetcher.getCustomerByName(firstName);

        if(customer.cash() < bill) throw new RuntimeException("customer has insufficient funds");

        return restFetcher.submitOrder(firstName, dish, tableNumber, bill);
    }
}
