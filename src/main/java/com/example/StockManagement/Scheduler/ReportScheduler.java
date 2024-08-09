package com.example.StockManagement.Scheduler;

import com.example.StockManagement.Service.EmailService;
import com.example.StockManagement.Service.ReportService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ReportScheduler {
    @Autowired
    ReportService reportService;

    @Autowired
    EmailService emailService;

    @Scheduled(cron = "0 0 22 * * *")
    public void sendReport() {
        try {
            reportService.generateReport();
            emailService.sendEmailReport();
        } catch (IOException | jakarta.mail.MessagingException e) {
            e.printStackTrace();
        }
    }
}
