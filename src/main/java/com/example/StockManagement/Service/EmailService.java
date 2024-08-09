package com.example.StockManagement.Service;

import com.example.StockManagement.Configuration.Admin;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    Admin admin;

    public void sendEmailReport() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String filePath = "src/main/resources/report/products_inventory_report_" + date + ".csv";

        helper.setTo(admin.getEmail());
        helper.setFrom(admin.getEmail());
        helper.setSubject("Daily Product and Inventory Report - " + date);
        helper.setText("Please find the attached daily report of products and inventory for " + date + ".");

        File file = new File(filePath);
        helper.addAttachment("products_inventory_report_" + date + ".csv", file);

        javaMailSender.send(mimeMessage);
    }

    public void senEmail(String emailTO, String subject, String message, String emailFrom) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setTo(emailTO);
        helper.setSubject(subject);
        helper.setText(message);
        helper.setFrom(emailFrom);

        javaMailSender.send(mimeMessage);
    }

}
