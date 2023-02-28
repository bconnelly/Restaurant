package com.fullstack.restaurantservice;

import com.fullstack.restaurantservice.DataEntities.CustomerRecord;
import com.fullstack.restaurantservice.DataEntities.OrderRecord;
import com.fullstack.restaurantservice.DataEntities.TableRecord;
import com.fullstack.restaurantservice.DomainLogic.RestaurantLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
//  returns 404 if no tables are open
    @PostMapping("/seatCustomer")
    public ResponseEntity<CustomerRecord> seatCustomer(@RequestParam(value = "firstName") String firstName,
                                                       @RequestParam(value = "address") String address,
                                                       @RequestParam(value = "cash") Float cash){
        log.debug("seatCustomer requested");
        try{
            CustomerRecord result = restaurantLogic.seatCustomer(firstName, address, cash);
            if(result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (RuntimeException e){
            log.debug(e.getCause() + ", " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @GetMapping("/getOpenTables")
    public ResponseEntity<List<TableRecord>> getOpenTables() {

        try{
            List<TableRecord> tables = restaurantLogic.getOpenTables();
            if(tables == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            return ResponseEntity.status(HttpStatus.OK).body(tables);
        } catch (RuntimeException e){
            log.debug(e.getCause() + ", " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //submit a new order
    // returns the order submitted
    // returns 404 if the customer isn't found in the restaurant
    @PostMapping(value = "/submitOrder")
    public ResponseEntity<OrderRecord> submitOrder(@RequestParam(value = "firstName")String firstName,
                                                   @RequestParam(value = "dish")String dish,
                                                   @RequestParam(value = "tableNumber")Integer tableNumber,
                                                   @RequestParam(value = "bill")Float bill){
        log.debug("submitOrder requested");

        try{
            OrderRecord result = restaurantLogic.submitOrder(firstName, dish, tableNumber, bill);
            if(result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
             else return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (RuntimeException e){
            log.debug(e.getCause() + ", " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
