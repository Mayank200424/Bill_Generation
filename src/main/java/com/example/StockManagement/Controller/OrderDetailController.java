package com.example.StockManagement.Controller;

import com.example.StockManagement.Model.OrderDetail;
import com.example.StockManagement.Model.ResponseEntity;
import com.example.StockManagement.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orderDetail")
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;

    @PostMapping("save")
    public ResponseEntity<OrderDetail> saveOrder(@RequestBody OrderDetail orderDetail){
        return orderDetailService.save(orderDetail);
    }
}
