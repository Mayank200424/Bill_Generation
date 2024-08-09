package com.example.StockManagement.Service;

import com.example.StockManagement.Configuration.Admin;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlertService {
    @Autowired
    SmsService smsService;

    @Autowired
    WhatsappService whatsappService;

    @Autowired
    EmailService emailService;

    @Autowired
    Admin admin;

    public void sendAlert(long productId, String productName, int inventory) {
        String message = String.format(
                "⚠️ Alert: The inventory for product ID %d (Product Name: %s) is below the threshold. 🚨 Remaining Stock: %d. Please restock soon!",
                productId,
                productName,
                inventory
        );

        //send alert message
        smsService.SendSMS(admin.getAdminMobileNo(), message);
        whatsappService.SendWhatsapp(admin.getAdminMobileNo(),message);

        //send email
        try {
            String emailSubject = "🚨 Inventory Alert: Low Stock for ***" + productName + "***";
            String emailTO = admin.getEmail();
            String emailFrom = admin.getEmail();
            emailService.senEmail(emailTO, emailSubject, message, emailFrom);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
