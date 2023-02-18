package com.fullstack.restaurantservice.DomainLogic;

import com.fullstack.restaurantservice.DataEntities.*;
import com.fullstack.restaurantservice.RestDataRetrieval.RestFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RestaurantLogic {

    public RestaurantLogic(){}

    @Autowired
    RestFetcher restFetcher;

    public CustomerRecord seatCustomer(String firstName, String address, Float cash){
        return restFetcher.seatCustomer(firstName, address, cash, getOpenTables().get(0).tableNumber());
    }

    public List<TableRecord> getOpenTables()  {
        List<TableRecord> allTables = restFetcher.getAllTables();
        List<CustomerRecord> allCustomers = restFetcher.getAllCustomers();

        if(allTables.isEmpty()) throw new RuntimeException("no tables retrieved");

//        for(CustomerRecord customer:allCustomers){
//            allTables.removeIf(n -> Objects.equals(n.tableNumber(), customer.tableNumber()));
//            allCustomers.removeIf(n -> Objects.equals(n.tableNumber(), customer.tableNumber()));
//        }
        List<TableRecord> allTablesCopy = allTables;
        for(TableRecord table:allTables){
            for(CustomerRecord customer:allCustomers){
                if(Objects.equals(customer.tableNumber(), table.tableNumber())){
//                    allTables.remove(table);
//                    allCustomers.remove(customer);
                    allTablesCopy.remove(table);
                    break;
                }
            }
        }

        return allTablesCopy;
    }

    public OrderRecord submitOrder(String firstName, String dish, Integer tableNumber, Float bill){

        Boolean customerExists = restFetcher.customerExists(firstName);
        Boolean hasSufficientCash = restFetcher.getCustomerByName(firstName).cash() > bill;

        if(customerExists && hasSufficientCash){
            return restFetcher.submitOrder(firstName, dish, tableNumber, bill);
        } else throw new RuntimeException("customer does not exists or has insufficient cash for order");
    }
}
