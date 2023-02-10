package com.fullstack.orderservice.DBAccessEntities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.persistence.Table;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
    private Integer id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "dish")
    private String dish;
    @Column(name = "table_number")
    private Integer tableNumber;
    @Column(name = "bill")
    private Float bill;

    public String toString(){
        return "[first_name: " + firstName +
                ", dish: " + dish +
                ", table_number: " +
                ", bill: " + bill + "]";
    }

    public boolean equals(Order order){
        return (order.firstName.equals(this.firstName) &&
                order.dish.equals(this.dish) &&
                order.tableNumber.equals(this.tableNumber) &&
                order.bill.equals(this.bill));
    }
}
