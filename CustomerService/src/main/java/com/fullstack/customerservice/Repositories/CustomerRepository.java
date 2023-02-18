package com.fullstack.customerservice.Repositories;

import com.fullstack.customerservice.DBAccessEntities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    List<Customer> findAll();
    Optional<List<Customer>> getCustomersByTableNumber(Integer tableNumber);
    Optional<Customer> getCustomerByFirstName(String firstName);
    @Transactional
    void deleteByFirstName(String firstName);
    boolean existsByFirstName(String firstName);
}