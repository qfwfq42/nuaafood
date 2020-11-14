package com.glc.loginregister;

import com.glc.loginregister.service.OrderService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class ScheduledTasks {
    private OrderService orderService;

    public ScheduledTasks(OrderService orderService) {
        this.orderService = orderService;
    }

    @Scheduled(fixedRate = 30000)
    public void updateOrderState(){
        orderService.updateOrderState();
        orderService.updateApplyState();
    }
}
