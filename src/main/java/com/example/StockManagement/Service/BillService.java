package com.example.StockManagement.Service;

import com.example.StockManagement.Model.*;
import com.example.StockManagement.Repository.BillRepository;
import com.example.StockManagement.Repository.CustomerRepository;
import com.example.StockManagement.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BillService {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    public Bill generateBill(OrderDetail orderDetail){
        double totalAmount = 0.0;

        for(OrderItem orderItem : orderDetail.getOrderItems()){
            Product product = productRepository.findById(orderItem.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with ID: " + orderItem.getProductId()));

            double productPrice = product.getProductPrice();
            double gstRate = product.getGst();
            double itemTotal = orderItem.getQuantity() * productPrice;
            double gstAmount = itemTotal * (gstRate / 100);
            totalAmount += itemTotal + gstAmount;

        }
        Bill bill = new Bill();
        bill.setOrderId(orderDetail.getOrderId());
        bill.setDate(LocalDate.now());
        bill.setTotalAmount(totalAmount);
        bill.setCustomerId(orderDetail.getCustomerId());

        Customer customer = customerRepository.findById(orderDetail.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + orderDetail.getCustomerId()));
        bill.setCustomerName(customer.getCustomerName());
        bill.setCustomerEmail(customer.getEmail());
        bill.setCustomerMobileNo(customer.getMobileNo());

        return bill;

    }
}
