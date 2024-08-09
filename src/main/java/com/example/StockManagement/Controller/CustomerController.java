package com.example.StockManagement.Controller;

import com.example.StockManagement.Model.Customer;
import com.example.StockManagement.Model.ResponseEntity;
import com.example.StockManagement.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping("save")
    public ResponseEntity<Customer> save(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }
}
