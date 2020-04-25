package com.hwj.playgo.service;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author holder
 * @date 2020/04/25
 */
@Component
public class TwitterNewsService implements NewsTracker{

    private static final String weatherApiUrl = "http://v.juhe.cn/weather/index?format=2&key=428aee1943e44a19fb8ae7f6c65778e5&cityname=";

    @Autowired
    RestTemplate restTemplate;

    public String track(){
        try {
            TimeUnit.MILLISECONDS.sleep(500);
           // String result = restTemplate.getForObject(weatherApiUrl+city, String.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "twitter new returned.";
    }
}
