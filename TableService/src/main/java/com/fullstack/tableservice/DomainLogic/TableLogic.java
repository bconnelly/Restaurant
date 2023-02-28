package com.fullstack.tableservice.DomainLogic;

import com.fullstack.tableservice.DBAccessEntities.Table;
import com.fullstack.tableservice.Repositories.TableRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TableLogic {

    @Autowired
    TableRepository tableRepository;

    public List<Table> getAllTables (){
        log.debug("at tableLogic.findAll");
        return tableRepository.findAll();
    }

    public Boolean tableExists(Integer tableNumber){
        return tableRepository.existsByTableNumber(tableNumber);
    }
}
