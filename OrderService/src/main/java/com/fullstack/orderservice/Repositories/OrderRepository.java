package com.fullstack.orderservice.Repositories;

import com.fullstack.orderservice.DBAccessEntities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAll();
    Optional<Order> findByFirstName(String firstName);
    Optional<Order> findById(Integer id);

}
