package com.fullstack.customerservice;

import com.fullstack.customerservice.DBAccessEntities.Customer;
import com.fullstack.customerservice.DomainLogic.CustomerLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
api returns response entities with a customer or list of customers and an HTTP status.
customer is null in the case of an error
*/
@Slf4j
@EnableTransactionManagement
@RestController
@SpringBootApplication(scanBasePackages = "com.fullstack.customerservice")
public class CustomerServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) { SpringApplication.run(CustomerServiceApplication.class, args); }

	@Autowired
	private CustomerLogic customerLogic;

	@GetMapping(path = "/getAllCustomers")
	public ResponseEntity<List<Customer>> getAllCustomers(){
		try{
			return ResponseEntity.status(HttpStatus.OK).body(customerLogic.getAllCustomers());
		}catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping(path = "/getCustomerByFirstName")
	public ResponseEntity<Customer> getCustomerByFirstName(String firstName){
		try{
			log.debug("getCustomerByFirstName requested");
			Optional<Customer> customerReturned = customerLogic.getCustomerByFirstName(firstName);

			if(customerReturned.isPresent()) return ResponseEntity.status(HttpStatus.OK).body(customerReturned.get());
			else return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping(path = "/customerExists")
	public ResponseEntity<Boolean> customerExists(String firstName){
		try{
			return ResponseEntity.status(HttpStatus.OK).body(customerLogic.customerExists(firstName));
		} catch (RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping(path = "/insertCustomer")
	public ResponseEntity<Customer> insertCustomer(@RequestParam(value = "firstName")String firstName,
												   @RequestParam(value = "address")String address,
												   @RequestParam(value = "cash")Float cash,
												   @RequestParam(value = "tableNumber")Integer tableNumber){
		try{
			return ResponseEntity.status(HttpStatus.OK).body(customerLogic.insertCustomer(firstName, address, cash, tableNumber));
		} catch (RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@PostMapping(path = "/bootCustomer")
	public ResponseEntity<Customer> bootCustomer(@RequestParam(value = "firstName") String firstName){

		try{
			if(customerLogic.bootByFirstName(firstName)){
				return ResponseEntity.status(HttpStatus.OK).body(null);
			}else{
				throw new RuntimeException("Entity still exists after deletion attempt");
			}
		} catch (RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping(path = "/getCustomerAtTable")
	public ResponseEntity<List<Customer>> getCustomerAtTable(@RequestParam(value = "tableNumber") Integer tableNumber){
		Optional<List<Customer>> customersReturned;

		try{
			customersReturned = customerLogic.getCustomersAtTable(tableNumber);
			return customersReturned.map(customers -> ResponseEntity.status(HttpStatus.OK).body(customers)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));

		} catch (RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
