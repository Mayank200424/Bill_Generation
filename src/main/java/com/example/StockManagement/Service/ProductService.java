package com.example.StockManagement.Service;

import com.example.StockManagement.Model.Product;
import com.example.StockManagement.Model.ResponseEntity;
import com.example.StockManagement.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<List<Product>> saveProduct(List<Product> product){
        ResponseEntity<List<Product>> responseEntity;
        try{
            responseEntity = new ResponseEntity<>(productRepository.saveAll(product), HttpStatus.OK,"Product Save SuccessFull");
        }catch (Exception e){
            responseEntity = new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR,"Failed");
        }
        return responseEntity;
    }
}
