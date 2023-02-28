package com.fullstack.orderservice;

import com.fullstack.orderservice.DBAccessEntities.Order;
import com.fullstack.orderservice.DomainLogic.OrderLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EnableTransactionManagement
@RestController
@SpringBootApplication(scanBasePackages = "com.fullstack.orderservice")
public class OrderServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Autowired
	private OrderLogic orderLogic;

	@GetMapping(path = "/")
	public ResponseEntity<String> defaultEndpoint(){
		return ResponseEntity.status(HttpStatus.OK).body("Some values:" +
				"\nCUSTOMERS_GET_ALL: " + System.getenv("CUSTOMERS_GET_ALL") +
				"\nJDBC_USER: " + System.getenv("JDBC_USER") +
				"\nORDERS_SUBMIT: " + System.getenv("ORDERS_SUBMIT") +
				"\nCUSTOMERS_SERVICE_SERVICE_HOST: " + System.getenv("CUSTOMERS_SERVICE_SERVICE_HOST") +
				"\nCUSTOMERS_SERVICE_SERVICE_PORT: " + System.getenv("CUSTOMERS_SERVICE_SERVICE_PORT") +
				"\nPATH: " + System.getenv("Path"));
	}

	@GetMapping(path = "/getAllOrders")
	public ResponseEntity<List<Order>> getAllOrders(){

		List<Order> orders;

		try{
			orders = orderLogic.getAllOrders();
			if(orders.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
			else return ResponseEntity.status(HttpStatus.OK).body(orders);
		} catch (RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping(path = "/insertOrder")
	public ResponseEntity<Order> insertOrder(@RequestParam(value = "firstName") String firstName,
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
			return ResponseEntity.status(HttpStatus.OK).body(orderLogic.insertOrder(order));
		}catch (RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/getOrdersByFirstName")
	public ResponseEntity<Order> getOrdersByFirstName(@RequestParam(value = "firstName") String firstName){

		try{
			Optional<Order> returnedOrder = orderLogic.getOrderByFirstName(firstName);
			return returnedOrder.map(order -> ResponseEntity.status(HttpStatus.OK).body(order)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

		} catch (RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
