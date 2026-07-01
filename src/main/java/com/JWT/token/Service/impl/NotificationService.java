package com.JWT.token.Service.impl;


import com.JWT.token.Aspect.LogExecution;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {

    @Async("taskExecutor")
    @LogExecution
    public void sendEmailNotification(String username) {
        System.out.println("[" + Thread.currentThread().getName() + "] Starting email transfer for: " + username);
        try {
            Thread.sleep(3000); // Simulate 3 seconds delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("[" + Thread.currentThread().getName() + "] Email sent successfully to: " + username);
    }


    @Async("taskExecutor")
    public CompletableFuture<String> generateReport(String reportName) {
        System.out.println("[" + Thread.currentThread().getName() + "] Generating report: " + reportName);
        try {
            Thread.sleep(2000); // Simulate 2 seconds delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return CompletableFuture.completedFuture("Report '" + reportName + "' Data Generated!");
    }
}
