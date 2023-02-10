package com.fullstack.customerservice.DomainLogic;

import com.fullstack.customerservice.DBAccessEntities.Customer;
import com.fullstack.customerservice.Repositories.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerLogic {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer seatNewCustomer(Customer customer){ return customerRepository.save(customer); }

    public boolean bootByFirstName(String firstName){
        customerRepository.deleteByFirstName(firstName);
        return (!customerRepository.existsByFirstName(firstName));
    }

    public List<Customer> getCustomersAtTable(Integer tableNumber){
        List<Customer> result = new ArrayList<>();
        List<Customer> allCustomers;

        try{
            allCustomers = customerRepository.findAll();
        } catch (RuntimeException e){
            throw new RuntimeException("Failed to get full list of customers");
        }

        for(Customer customer:allCustomers){
            if(customer.getTableNumber().equals(tableNumber)){
                result.add(customer);
            }
        }

        return  result;
    }
}
