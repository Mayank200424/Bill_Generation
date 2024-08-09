package com.example.StockManagement.Service;

import com.example.StockManagement.Configuration.TwilioConfiguration;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {
    @Autowired
    TwilioConfiguration twilioConfiguration;

    public void SendSMS(String customerNo,String message){
        String customerNumber = "+91" + customerNo;

        Message.creator(
                new PhoneNumber(customerNumber),
                new PhoneNumber(twilioConfiguration.getTrialNumber()),
                message
        ).create();

        System.out.println("sms sent to customer : " + customerNumber + " with message " + message);
    }

}
