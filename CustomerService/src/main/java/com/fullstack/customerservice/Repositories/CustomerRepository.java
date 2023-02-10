package com.fullstack.customerservice.Repositories;

import com.fullstack.customerservice.DBAccessEntities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAll();

    Customer save(Customer customer);
    @Transactional
    void deleteByFirstName(String firstName);
    boolean existsByFirstName(String firstName);

    //only for debugging
    @Transactional
    void deleteById(Integer id);
    Optional<Customer> findById(Integer id);
}