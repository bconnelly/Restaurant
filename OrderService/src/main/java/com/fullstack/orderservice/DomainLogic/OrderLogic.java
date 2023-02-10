package com.fullstack.orderservice.DomainLogic;

import com.fullstack.orderservice.DBAccessEntities.Order;
import com.fullstack.orderservice.Repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLogic {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Order submitOrder(Order order){
        return orderRepository.save(order);
    }
}