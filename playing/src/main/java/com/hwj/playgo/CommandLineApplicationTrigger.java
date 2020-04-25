package com.hwj.playgo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.hwj.playgo.service.NewsTracker;
import com.hwj.playgo.strategy.RequestStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * @author holder
 * @date 2020/04/25
 */
@Component
public class CommandLineApplicationTrigger implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        Map<String, RequestStrategy> requestStrategies = applicationContext.getBeansOfType(RequestStrategy.class);
        requestStrategies.entrySet().stream().forEach(e -> {
            e.getValue().execute();
        });
    }
}
