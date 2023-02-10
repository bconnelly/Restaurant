package com.fullstack.customerservice;

import com.fullstack.customerservice.DBAccessEntities.Customer;
import com.fullstack.customerservice.DomainLogic.CustomerLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
api returns response entities with a customer or list of customers and an HTTP status.
customer is null in the case of an error
*/
@EnableTransactionManagement
@RestController
@SpringBootApplication(scanBasePackages = "com.fullstack.customerservice")
public class CustomerServiceApplication extends SpringBootServletInitializer {

	public static void main(String[] args) { SpringApplication.run(CustomerServiceApplication.class, args); }

	@Autowired
	private CustomerLogic customerLogic;

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
//		return builder.sources(CustomerServiceApplication.class);
//	}

	@GetMapping(path = "/getAllCustomers")
	public ResponseEntity<List<Customer>> getAllCustomers(){

		try{
			return ResponseEntity.status(HttpStatus.OK).body(customerLogic.getAllCustomers());
		}catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@PostMapping(path = "/seatCustomer")
	public ResponseEntity<Customer> seatCustomer(@RequestParam(value = "firstName") String firstName,
									   @RequestParam(value = "address") String address,
									   @RequestParam(value = "tableNumber") Integer tableNumber,
									   @RequestParam(value = "cash") Float cash){


		Customer newCustomer = new Customer();
			newCustomer.setFirstName(firstName);
			newCustomer.setAddress(address);
			newCustomer.setTableNumber(tableNumber);
			newCustomer.setCash(cash);
		try{
			return ResponseEntity.status(HttpStatus.OK).body(customerLogic.seatNewCustomer(newCustomer));
		}catch(RuntimeException e){
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
		}catch(RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping(path = "/getCustomerAtTable")
	public ResponseEntity<List<Customer>> getCustomerAtTable(@RequestParam(value = "tableNumber") Integer tableNumber){
		List<Customer> customers;

		try{
			customers = customerLogic.getCustomersAtTable(tableNumber);
		} catch (RuntimeException e){
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(customers);
	}

}
