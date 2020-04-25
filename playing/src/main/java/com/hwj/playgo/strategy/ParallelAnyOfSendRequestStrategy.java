package com.hwj.playgo.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import com.hwj.playgo.service.HeadlinesNewsService;
import com.hwj.playgo.service.NewsTracker;
import com.hwj.playgo.service.TwitterNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author holder
 * @date 2020/04/25
 */
@Component("parallelAnyOfSendRequestStrategy")
public class ParallelAnyOfSendRequestStrategy implements RequestStrategy {

    @Autowired
    private ApplicationContext applicationContext;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
        Runtime.getRuntime().availableProcessors() * 2 ,
        10,
        TimeUnit.MINUTES,
        new LinkedBlockingDeque<>(5),
        new CallerRunsPolicy());

    @Override
    public int execute() {
        Map<String, NewsTracker> trackerList = applicationContext.getBeansOfType(NewsTracker.class);
        List<CompletableFuture<String>> list = new ArrayList<>(3);
        trackerList.entrySet().stream().forEach(e->{
            list.add(CompletableFuture.supplyAsync(new Supplier<String>() {
                @Override
                public String get() {
                    return e.getValue().track();
                }
            },threadPoolExecutor));
        });

        CompletableFuture<Object> future = CompletableFuture.anyOf(list.toArray(new CompletableFuture[list.size()]));

        try {
            System.out.println(future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
