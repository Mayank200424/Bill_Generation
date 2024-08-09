package com.example.StockManagement.Service;

import com.example.StockManagement.Model.Customer;
import com.example.StockManagement.Model.ResponseEntity;
import com.example.StockManagement.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    CustomerRepository customerRepository;

    public ResponseEntity<Customer> saveCustomer(Customer customer){
        ResponseEntity<Customer> responseEntity;
        try{
            responseEntity = new ResponseEntity<>(customerRepository.save(customer), HttpStatus.OK,"Customer Save SuccessFull");
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR,"Failed");
        }
        return responseEntity;
    }
}
