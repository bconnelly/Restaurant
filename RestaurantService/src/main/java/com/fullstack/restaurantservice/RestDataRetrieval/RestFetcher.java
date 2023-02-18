package com.fullstack.restaurantservice.RestDataRetrieval;

import com.fullstack.restaurantservice.DataEntities.CustomerRecord;
import com.fullstack.restaurantservice.DataEntities.OrderRecord;
import com.fullstack.restaurantservice.DataEntities.TableRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class RestFetcher {

    public RestFetcher(){
        template = new RestTemplate();
    }

    RestTemplate template;

    @Value("${host.url}")
    private String host;
    @Value("${customer.get-all.url}")
    private String customerGetAllUrl;
    @Value("${customer.get-by-name.url}")
    private String customerGetByNameUrl;
    @Value("${customer.exists.url}")
    private String customerExistsUrl;
    @Value("${customer.seat.url}")
    private String seatCustomerUrl;
    @Value("${order.submit.url}")
    private String orderSubmitUrl;
    @Value("${table.get-all.url}")
    private String tableGetAllUrl;

    public void setTemplate(RestTemplate template) {
        this.template = template;
    }

    public List<CustomerRecord> getAllCustomers(){
        CustomerRecord[] customerRecords;
        //make sure env variables loaded
        if(host == null || customerGetAllUrl == null)
            throw new RuntimeException("failed to load environment");
        try{
            customerRecords = template.getForObject(host + customerGetAllUrl, CustomerRecord[].class);
            if(customerRecords != null) return new ArrayList<>(Arrays.asList(customerRecords));
            else throw new RuntimeException("all customer records not retrieved");
        } catch (RestClientException e){
            throw new RuntimeException(e);
        }
    }

    public CustomerRecord getCustomerByName(String firstName){
        if(host == null || customerGetByNameUrl == null) throw new RuntimeException("failed to load environment");
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(host + customerGetByNameUrl)
                .queryParam("firstName", firstName).encode().toUriString();

        try{
            return template.getForObject(urlTemplate, CustomerRecord.class);
        } catch (RestClientException e){
            throw new RuntimeException(e);
        }
    }

    public Boolean customerExists(String firstName){
        if(host == null || customerExistsUrl == null)  throw new RuntimeException("failed to load environment");
        String urlTemplate = UriComponentsBuilder.fromHttpUrl(host + customerExistsUrl)
                .queryParam("firstName", firstName).encode().toUriString();
        try{
            return template.getForObject(urlTemplate, Boolean.class);
        } catch (RestClientException e){
            throw new RuntimeException(e);
        }
    }

    public CustomerRecord seatCustomer(String firstName, String address, Float cash, Integer tableNumber){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(host + seatCustomerUrl)
                .queryParam("firstName", firstName)
                .queryParam("address", address)
                .queryParam("cash", cash)
                .queryParam("tableNumber", tableNumber).encode().toUriString();

        HttpEntity<String> request = new HttpEntity<>(headers);
        return template.postForObject(urlTemplate, request, CustomerRecord.class);
    }

    public OrderRecord submitOrder(String firstName, String dish, Integer tableNumber, Float bill){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String urlTemplate = UriComponentsBuilder.fromHttpUrl(host + orderSubmitUrl)
                .queryParam("firstName", firstName)
                .queryParam("dish", dish)
                .queryParam("tableNumber", tableNumber)
                .queryParam("bill", bill).encode().toUriString();

        HttpEntity<String> request = new HttpEntity<>(headers);
        return template.postForObject(urlTemplate, request, OrderRecord.class);
    }

    public List<TableRecord> getAllTables(){
        TableRecord[] tableRecordList;
        if(host == null || tableGetAllUrl == null) throw new RuntimeException("failed to load environment");
        try{
            tableRecordList = template.getForObject(host + tableGetAllUrl, TableRecord[].class);
            if(tableRecordList != null) return new ArrayList<>(Arrays.asList(tableRecordList));
            else throw new RuntimeException("all table records not retrieved");
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
    }
}
