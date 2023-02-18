package com.fullstack.customerservice.DomainLogic;

import com.fullstack.customerservice.DBAccessEntities.Customer;
import com.fullstack.customerservice.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerLogic {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Boolean customerExists(String firstName){
        return customerRepository.existsByFirstName(firstName);
    }

    public Optional<Customer> getCustomerByFirstName(String firstName){
        return customerRepository.getCustomerByFirstName(firstName);
    }

    public Customer insertCustomer(String firstName, String address, Float cash, Integer tableNumber){
        Customer newCustomer = Customer.builder()
                .firstName(firstName)
                .address(address)
                .cash(cash)
                .tableNumber(tableNumber).build();
        return customerRepository.save(newCustomer);
    }

    public boolean bootByFirstName(String firstName){
        customerRepository.deleteByFirstName(firstName);
        return (!customerRepository.existsByFirstName(firstName));
    }

    public Optional<List<Customer>> getCustomersAtTable(Integer tableNumber){
        return customerRepository.getCustomersByTableNumber(tableNumber);
    }
}
