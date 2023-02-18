package com.fullstack.tableservice.Repositories;

import com.fullstack.tableservice.DBAccessEntities.Table;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TableRepository extends JpaRepository<Table, Integer> {

    List<Table> findAll();

    boolean existsByTableNumber(Integer tableNumber);

}
