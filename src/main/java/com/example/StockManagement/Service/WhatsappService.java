package com.example.StockManagement.Service;

import com.example.StockManagement.Configuration.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WhatsappService {
    @Autowired
    TwilioConfiguration twilioConfiguration;

    public void SendWhatsapp(String customerNo,String message){
        String customerNumber = "+" + customerNo;

        Message.creator(
                new PhoneNumber("whatsapp:+918849866832"),
                new PhoneNumber("whatsapp:+14155238886"),
                message
        ).create();

        System.out.println("WhatsApp message sent to customer ID: " + customerNumber + " with message: " + message);
    }
}
