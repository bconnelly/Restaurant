package com.fullstack.restaurantservice.RestDataRetrieval;

import com.fullstack.restaurantservice.DataEntities.CustomerRecord;
import com.fullstack.restaurantservice.DataEntities.OrderRecord;
import com.fullstack.restaurantservice.DataEntities.TableRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class RestFetcher {

    public RestFetcher(){
        this.template = new RestTemplate();
    }
    private final RestTemplate template;

    @Value("${customers.get-all.endpoint}")
    private String customerGetAllUrl;
    @Value("${customers.host.url}")
    private String customersHost;
    @Value("${orders.host.url}")
    private String ordersHost;
    @Value("${tables.host.url}")
    private String tablesHost;
    @Value("${customers.get-by-name.endpoint}")
    private String customerGetByNameUrl;
    @Value("${customers.exists.endpoint}")
    private String customerExistsUrl;
    @Value("${customers.seat.endpoint}")
    private String seatCustomerUrl;
    @Value("${orders.submit.endpoint}")
    private String orderSubmitUrl;
    @Value("${tables.get-all.endpoint}")
    private String tableGetAllUrl;

    public List<CustomerRecord> getAllCustomers(){
        CustomerRecord[] customerRecords;
        //make sure env variables loaded
        if(customersHost == null || customerGetAllUrl == null)
            throw new RuntimeException("failed to load environment");
        try{
            customerRecords = template.getForObject(customersHost + customerGetAllUrl, CustomerRecord[].class);
            if(customerRecords != null) return new ArrayList<>(Arrays.asList(customerRecords));
            else throw new RuntimeException("all customer records not retrieved");
        } catch (RestClientException e){
            throw new RuntimeException(e);
        }
    }

    public CustomerRecord getCustomerByName(String firstName) throws RuntimeException{
        log.debug("name given: " + firstName);
        log.debug("necessary fields--- customersHost: " + customersHost + ", customerGetByNameUrl: " + customerGetByNameUrl);
        if(customersHost == null || customerGetByNameUrl == null) throw new RuntimeException("failed to load environment");
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(customersHost + customerGetByNameUrl)
                .queryParam("firstName", firstName).encode().toUriString();
        log.debug("Uri: " + urlTemplate);
        try{
            return template.getForObject(urlTemplate, CustomerRecord.class);
        } catch (HttpClientErrorException e){
            if(e.getStatusCode() == HttpStatus.NOT_FOUND) return null;
            else throw new RuntimeException(e);
        }
    }

    public Boolean customerExists(String firstName){
        if(customersHost == null || customerExistsUrl == null)  throw new RuntimeException("failed to load environment");
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(customersHost + customerExistsUrl)
                .queryParam("firstName", firstName).encode().toUriString();
        try{
            return template.getForObject(urlTemplate, Boolean.class);
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    public CustomerRecord seatCustomer(String firstName, String address, Float cash, Integer tableNumber){
        log.debug("at seatCustomer");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(customersHost + seatCustomerUrl)
                .queryParam("firstName", firstName)
                .queryParam("address", address)
                .queryParam("cash", cash)
                .queryParam("tableNumber", tableNumber).encode().toUriString();

        HttpEntity<String> request = new HttpEntity<>(headers);
        log.debug("before post in seatCustomer");
        log.debug("customersHost: " + customersHost);
        log.debug("seatCustomersUrl: " + seatCustomerUrl);
        log.debug("Uri: " + urlTemplate);
        return template.postForObject(urlTemplate, request, CustomerRecord.class);
    }

    public OrderRecord submitOrder(String firstName, String dish, Integer tableNumber, Float bill){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(ordersHost + orderSubmitUrl)
                .queryParam("firstName", firstName)
                .queryParam("dish", dish)
                .queryParam("tableNumber", tableNumber)
                .queryParam("bill", bill).encode().toUriString();

        HttpEntity<String> request = new HttpEntity<>(headers);
        return template.postForObject(urlTemplate, request, OrderRecord.class);
    }

    public List<TableRecord> getAllTables(){
        TableRecord[] tableRecordList;
        if(tablesHost == null || tableGetAllUrl == null) throw new RuntimeException("failed to load environment");
        log.debug("calling table service at " + tablesHost + " " + tableGetAllUrl);
        try{
            log.atDebug().addArgument(tablesHost).addArgument(tableGetAllUrl).setMessage("before call to table service. tablesHost: {}, tablesGetAllUrl: {}").log();
            tableRecordList = template.getForObject(tablesHost + tableGetAllUrl, TableRecord[].class);
            log.debug("after call to table service");
            if(tableRecordList != null) return new ArrayList<>(Arrays.asList(tableRecordList));
            else throw new RuntimeException("all table records not retrieved");
        } catch (RuntimeException e){
            log.debug("runtime exception in restfetcher.getAllTables");
            throw new RuntimeException(e);
        }
    }
}
