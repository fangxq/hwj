package com.hwj.playgo.strategy;

import java.util.List;
import java.util.Map;

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
@Component("serialSendRequestStrategy")
public class SerialSendRequestStrategy implements RequestStrategy {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public int execute() {
        Map<String, NewsTracker> trackerList = applicationContext.getBeansOfType(NewsTracker.class);
        trackerList.entrySet().stream().forEach(e->{
            System.out.println(e.getValue().track());
        });
        return 1;
    }
}
