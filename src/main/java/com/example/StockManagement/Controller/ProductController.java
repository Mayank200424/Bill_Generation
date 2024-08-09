package com.example.StockManagement.Controller;

import com.example.StockManagement.Model.Product;
import com.example.StockManagement.Model.ResponseEntity;
import com.example.StockManagement.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping("save")
    public ResponseEntity<List<Product>> save(@RequestBody List<Product> products){
        return productService.saveProduct(products);
    }
}
