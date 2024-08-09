package com.example.StockManagement.Service;

import com.example.StockManagement.Model.*;
import com.example.StockManagement.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OrderDetailService {
    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    BillService billService;

    @Autowired
    SmsService smsService;

    @Autowired
    WhatsappService whatsappService;

    @Autowired
    AlertService alertService;

    private static final int INVENTORY_THRESHOLD = 10;

    public ResponseEntity<OrderDetail> save(OrderDetail orderDetail){
        try{
            for(OrderItem orderItem : orderDetail.getOrderItems()){
                Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(() -> new RuntimeException("Product not found with ID: " + orderItem.getProductId()));
                if (product.getInventory() < orderItem.getQuantity()){
                    return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST, "Insufficient inventory for product ID: " + orderItem.getProductId());
                }
            }
            Customer customer = customerRepository.findById(orderDetail.getCustomerId()).orElseThrow(() -> new RuntimeException("Customer Not Found With Id : " + orderDetail.getCustomerId()));
            String customerPhoneNumber = String.valueOf(customer.getMobileNo());

            boolean paymentSuccess = Payment();
            if(!paymentSuccess){
                String paymentFailed = String.format(
                        "⚠️ Hello %s! Unfortunately, your order could not be processed due to a payment failure. Please try again or contact support for assistance. We're here to help! 🙏",
                        customer.getCustomerName()
                );
                smsService.SendSMS(customerPhoneNumber,paymentFailed);
                whatsappService.SendWhatsapp(customerPhoneNumber,paymentFailed);
                return new ResponseEntity<>(null,HttpStatus.PAYMENT_REQUIRED,"payment failed");
            }

            OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
            orderItemRepository.saveAll(orderDetail.getOrderItems());
            Bill bill = billService.generateBill(savedOrderDetail);
            billRepository.save(bill);

            String paymentSuccessMessage = String.format(
                    "🎉 Hello %s! 🛒 Your order (ID: %s) has been successfully placed! 💳 Total payment: ₹%.2f. Thank you for choosing us! 🙏 We'll notify you once it's on its way. 🚚✨",
                    customer.getCustomerName(),
                    orderDetail.getOrderId(),
                    bill.getTotalAmount()
            );

            smsService.SendSMS(customerPhoneNumber,paymentSuccessMessage);
            whatsappService.SendWhatsapp(customerPhoneNumber,paymentSuccessMessage);

            for (OrderItem orderItem : savedOrderDetail.getOrderItems()) {
                Product product = productRepository.findById(orderItem.getProductId()).orElseThrow(() -> new RuntimeException("Product not found with ID: " + orderItem.getProductId()));
                product.setInventory(product.getInventory() - orderItem.getQuantity());
                productRepository.save(product);

                if (product.getInventory() < INVENTORY_THRESHOLD) {
                    alertService.sendAlert(product.getProductId(), product.getProductName(), product.getInventory());
                }
            }

            return new ResponseEntity<>(savedOrderDetail, HttpStatus.OK, "Order placed successfully");
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR, "failed to place");
        }


    }
    public boolean Payment(){
        Random random = new Random();
        return random.nextInt(4) != 0;
    }
}
