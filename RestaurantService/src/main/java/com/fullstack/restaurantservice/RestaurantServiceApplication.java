package com.fullstack.restaurantservice;

import com.fullstack.restaurantservice.DataEntities.CustomerRecord;
import com.fullstack.restaurantservice.DataEntities.OrderRecord;
import com.fullstack.restaurantservice.DataEntities.TableRecord;
import com.fullstack.restaurantservice.DomainLogic.RestaurantLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@SpringBootApplication(scanBasePackages = "com.fullstack.restaurantservice")
public class RestaurantServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantServiceApplication.class, args);
    }
    private static final Logger log = LoggerFactory.getLogger(RestaurantServiceApplication.class);

    @Autowired
    RestaurantLogic restaurantLogic;

    @GetMapping("/seatCustomer")
    public ResponseEntity<CustomerRecord> seatCustomer(@RequestParam(value = "firstName") String firstName,
                                                       @RequestParam(value = "address") String address,
                                                       @RequestParam(value = "cash") Float cash){

        try{
            CustomerRecord result = restaurantLogic.seatCustomer(firstName, address, cash);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/getOpenTables")
    public ResponseEntity<List<TableRecord>> getOpenTables() {
        try{
            return ResponseEntity.status(HttpStatus.OK).body(restaurantLogic.getOpenTables());
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(value = "/submitOrder")
    public ResponseEntity<OrderRecord> submitOrder(@RequestParam(value = "firstName")String firstName,
                                                   @RequestParam(value = "dish")String dish,
                                                   @RequestParam(value = "tableNumber")Integer tableNumber,
                                                   @RequestParam(value = "bill")Float bill){

        try{
            return ResponseEntity.status(HttpStatus.OK).body(restaurantLogic.submitOrder(firstName, dish, tableNumber, bill));
        } catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
