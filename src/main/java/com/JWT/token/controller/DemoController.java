package com.JWT.token.controller;


import com.JWT.token.Aspect.LogExecution;
import com.JWT.token.Service.impl.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class DemoController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notify")
    public String triggerNotification(@RequestParam String name) {
        System.out.println("[" + Thread.currentThread().getName() + "] Controller received request.");

        notificationService.sendEmailNotification(name);

        System.out.println("[" + Thread.currentThread().getName() + "] Controller is returning response immediately.");
        return "Notification process started in the background for " + name;
    }

    @GetMapping("/report")
    @LogExecution
    public String triggerReport(@RequestParam String name) throws ExecutionException, InterruptedException {
        System.out.println("[" + Thread.currentThread().getName() + "] Controller received report request.");

        CompletableFuture<String> futureResult = notificationService.generateReport(name);

        // You can do other tasks here while the report is being generated!
        System.out.println("[" + Thread.currentThread().getName() + "] Doing other operations in controller...");

        // .get() blocks until the async task is done
        String result = futureResult.get();

        return "Result: " + result;
    }


}
