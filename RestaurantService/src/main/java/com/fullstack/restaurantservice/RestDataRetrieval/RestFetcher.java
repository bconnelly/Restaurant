package com.fullstack.restaurantservice.RestDataRetrieval;

import com.fullstack.restaurantservice.DataEntities.CustomerRecord;
import com.fullstack.restaurantservice.DataEntities.OrderRecord;
import com.fullstack.restaurantservice.DataEntities.TableRecord;
import com.fullstack.restaurantservice.Utilities.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class RestFetcher {

    public RestFetcher(){ this.template = new RestTemplate(); }
    private final RestTemplate template;

    @Value("${customers.host.url}")
    private String customersHost;
    @Value("${orders.host.url}")
    private String ordersHost;
    @Value("${tables.host.url}")
    private String tablesHost;
    @Value("${customers.get-all.endpoint}")
    private String customerGetAllUrl;
    @Value("${customers.get-by-name.endpoint}")
    private String customerGetByNameUrl;
    @Value("${customers.exists.endpoint}")
    private String customerExistsUrl;
    @Value("${customers.seat.endpoint}")
    private String customersSeatUrl;
    @Value("${customers.boot.endpoint}")
    private String customersBootUrl;
    @Value("${orders.submit.endpoint}")
    private String orderSubmitUrl;
    @Value("${tables.get-all.endpoint}")
    private String tableGetAllUrl;

    public List<CustomerRecord> getAllCustomers() throws EntityNotFoundException {
        if(customersHost == null || customerGetAllUrl == null) throw new RuntimeException("failed to load environment");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(customersHost + customerGetAllUrl).toUriString();

        CustomerRecord[] customerRecords = template.getForObject(urlTemplate, CustomerRecord[].class);
        if(customerRecords != null) return new ArrayList<>(Arrays.asList(customerRecords));
        else throw new EntityNotFoundException("customer records could not be retrieved");
    }

    public CustomerRecord getCustomerByName(String firstName) throws EntityNotFoundException {
        if(customersHost == null || customerGetByNameUrl == null) throw new RuntimeException("failed to load environment");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(customersHost + customerGetByNameUrl)
                .queryParam("firstName", firstName).toUriString();

        CustomerRecord customerRecord = template.getForObject(urlTemplate, CustomerRecord.class);
        if(customerRecord != null) return customerRecord;
        else throw new EntityNotFoundException("customer not found");
    }

    public Boolean customerExists(String firstName) {
        if(customersHost == null || customerExistsUrl == null)  throw new RuntimeException("failed to load environment");
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(customersHost + customerExistsUrl)
                .queryParam("firstName", firstName).toUriString();
        return template.getForObject(urlTemplate, Boolean.class);
    }

    public CustomerRecord seatCustomer(String firstName, String address, Float cash, Integer tableNumber) {
        if(customersHost == null || customersSeatUrl == null) throw new RuntimeException("failed to load environment");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(customersHost + customersSeatUrl)
                .queryParam("firstName", firstName)
                .queryParam("address", address)
                .queryParam("cash", cash)
                .queryParam("tableNumber", tableNumber).toUriString();

        HttpEntity<String> request = new HttpEntity<>(headers);
        return template.postForObject(urlTemplate, request, CustomerRecord.class);
    }

    public OrderRecord submitOrder(String firstName, String dish, Integer tableNumber, Float bill){
        if(ordersHost == null || orderSubmitUrl == null) throw new RuntimeException("failed to load environment");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(ordersHost + orderSubmitUrl)
                .queryParam("firstName", firstName)
                .queryParam("dish", dish)
                .queryParam("tableNumber", tableNumber)
                .queryParam("bill", bill).toUriString();

        return template.postForObject(urlTemplate, request, OrderRecord.class);
    }

    public List<TableRecord> getAllTables() throws EntityNotFoundException{
        if(tablesHost == null || tableGetAllUrl == null) throw new RuntimeException("failed to load environment");

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(tablesHost + tableGetAllUrl).toUriString();

        TableRecord[] tableRecordList = template.getForObject(urlTemplate, TableRecord[].class);

        if(tableRecordList != null) return new ArrayList<>(Arrays.asList(tableRecordList));
        else throw new EntityNotFoundException("no tables found");
    }

    public CustomerRecord bootCustomer(String misbehavingCustomer) {
        if(customersHost == null || customersBootUrl == null) throw new RuntimeException("failed to load environment");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);

        System.out.println(customersHost);
        System.out.println(customersBootUrl);
        System.out.println(misbehavingCustomer);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(customersHost + customersBootUrl)
                .queryParam("firstName", misbehavingCustomer).toUriString();

        return template.postForObject(urlTemplate, request, CustomerRecord.class);

    }
}
