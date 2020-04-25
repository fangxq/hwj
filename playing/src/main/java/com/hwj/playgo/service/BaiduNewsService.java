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
public class BaiduNewsService implements NewsTracker {

    private static final String baiduKeywordsApi= "http://v.juhe.cn/siteTools/app/BaiduKeyword/query.php?key=543516b30516518b5c4847222b60c72d";

    @Autowired
    RestTemplate restTemplate;

    public String track(){
        try {
            TimeUnit.SECONDS.sleep(1);
            // return restTemplate.getForObject(baiduKeywordsApi,String.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "baidu new returned.";

    }

}
