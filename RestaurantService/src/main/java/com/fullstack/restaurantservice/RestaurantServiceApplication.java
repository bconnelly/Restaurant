package com.fullstack.restaurantservice;

import com.fullstack.restaurantservice.DataEntities.CustomerRecord;
import com.fullstack.restaurantservice.DataEntities.OrderRecord;
import com.fullstack.restaurantservice.DataEntities.TableRecord;
import com.fullstack.restaurantservice.DomainLogic.RestaurantLogic;
import com.fullstack.restaurantservice.Utilities.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@SpringBootApplication(scanBasePackages = "com.fullstack.restaurantservice")
public class RestaurantServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(RestaurantServiceApplication.class);
    }

    @Autowired
    RestaurantLogic restaurantLogic;

    @GetMapping("/")
    public ResponseEntity<String> defaultLandingPage(){
        log.debug("default landing page requested");
        return ResponseEntity.status(HttpStatus.OK).body("default landing page");
    }

//  seat a new customer at their own table
//  returns the customer that was seated
//  returns 404 if no empty tables are found
    @PostMapping("/seatCustomer")
    public ResponseEntity<CustomerRecord> seatCustomer(@RequestParam("firstName") String firstName,
                                                       @RequestParam("address") String address,
                                                       @RequestParam("cash") Float cash) throws EntityNotFoundException {
        log.debug("seatCustomer requested");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantLogic.seatCustomer(firstName, address, cash));
    }

//  get list of unoccupied tables
//  returns 404
    @GetMapping("/getOpenTables")
    public ResponseEntity<List<TableRecord>> getOpenTables() throws EntityNotFoundException {
        log.debug("getOpenTables requested");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantLogic.getOpenTables());
    }

    //submit a new order
    // returns the order submitted
    // returns 404 if the customer isn't found in the restaurant
    @PostMapping("/submitOrder")
    public ResponseEntity<OrderRecord> submitOrder(@RequestParam("firstName")String firstName,
                                                   @RequestParam("dish")String dish,
                                                   @RequestParam("tableNumber")Integer tableNumber,
                                                   @RequestParam("bill")Float bill) throws EntityNotFoundException {
        log.debug("submitOrder requested");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantLogic.submitOrder(firstName, dish, tableNumber, bill));
    }

    @PostMapping("/bootCustomer")
    public ResponseEntity<CustomerRecord> bootCustomer(@RequestParam("firstName") String firstName) throws EntityNotFoundException {
        log.debug("bootCustomer requested");
        return ResponseEntity.status(HttpStatus.OK).body(restaurantLogic.bootCustomer(firstName));
    }
}

//testing github jenkins integration attempt #3