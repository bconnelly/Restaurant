package com.fullstack.tableservice;

import com.fullstack.tableservice.DBAccessEntities.Table;
import com.fullstack.tableservice.DomainLogic.TableLogic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@SpringBootApplication
public class TableServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TableServiceApplication.class, args);
    }

    @Autowired
    private TableLogic tableLogic;

    @GetMapping(path = "/getAllTables")
    public ResponseEntity<List<Table>> getAllTables(){
        log.debug("getAllTables requested");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(tableLogic.getAllTables());
        } catch(RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping(path = "/tableExists")
    public ResponseEntity<Boolean> tableExists(Integer tableNumber){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(tableLogic.tableExists(tableNumber));
        } catch(RuntimeException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
