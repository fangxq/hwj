package com.hwj.playgo.strategy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

import com.hwj.playgo.service.BaiduNewsService;
import com.hwj.playgo.service.HeadlinesNewsService;
import com.hwj.playgo.service.TwitterNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author holder
 * @date 2020/04/25
 */
@Component("thenApplySendRequestStrategy")
public class ThenApplySendRequestStrategy implements RequestStrategy {

    /**
     * order return
     *
     *
     */
    @Autowired
    private BaiduNewsService baiduNewsService;

    @Autowired
    private TwitterNewsService twitterNewsService;

    @Autowired
    private HeadlinesNewsService headlinesNewsService;

    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
        Runtime.getRuntime().availableProcessors() * 2 ,
        10,
        TimeUnit.MINUTES,
        new LinkedBlockingDeque<>(5),
        new CallerRunsPolicy());

    @Override
    public int execute() {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return baiduNewsService.track();
            }
        },threadPoolExecutor).thenApply(news->{
            return twitterNewsService.track()+ news;
        }).thenApply(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return headlinesNewsService.track() +s;
            }
        });

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
