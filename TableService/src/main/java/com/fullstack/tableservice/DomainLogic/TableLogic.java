package com.fullstack.tableservice.DomainLogic;

import com.fullstack.tableservice.DBAccessEntities.Table;
import com.fullstack.tableservice.Repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableLogic {

    @Autowired
    TableRepository tableRepository;

    public List<Table> getAllTables (){
        return tableRepository.findAll();
    }

}
