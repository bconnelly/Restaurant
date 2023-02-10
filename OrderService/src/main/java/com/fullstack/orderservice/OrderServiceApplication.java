package com.fullstack.orderservice;

import com.fullstack.orderservice.DBAccessEntities.Order;
import com.fullstack.orderservice.DomainLogic.OrderLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@EnableTransactionManagement
@RestController
@SpringBootApplication(scanBasePackages = "com.fullstack.orderservice")
public class OrderServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Autowired
	private OrderLogic orderLogic;

	@GetMapping(path = "/getAllOrders")
	public ResponseEntity<List<Order>> getAllOrders(){

		List<Order> orders;

		try{
			orders = orderLogic.getAllOrders();
		} catch (RuntimeException e){
			throw new RuntimeException("Failed to get orders from restaurant | " + e);
		}

		return new ResponseEntity(orders, HttpStatus.OK);
	}

	@PostMapping(path = "/submitOrder")
	public ResponseEntity<Order> submitOrder(@RequestParam(value = "firstName") String firstName,
									  @RequestParam(value = "tableNumber")Integer tableNumber,
									  @RequestParam(value = "dish") String dish,
									  @RequestParam(value = "bill") Float bill){

		Order order = Order.builder()
				.firstName(firstName)
				.tableNumber(tableNumber)
				.dish(dish)
				.bill(bill)
				.build();

		try{
			return ResponseEntity.status(HttpStatus.OK).body(orderLogic.submitOrder(order));
		}catch (RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
