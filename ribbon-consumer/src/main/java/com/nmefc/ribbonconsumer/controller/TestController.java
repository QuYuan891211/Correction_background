package com.nmefc.ribbonconsumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestController {
    @Autowired
    RestTemplate restTemplate;
    /**
     *@Description: 通过restTemplate去调用其他服务
     *@Param: []
     *@Return: java.lang.String
     *@Author: QuYuan
     *@Date: 2020/4/24 21:04
     */
    @RequestMapping(value = "/consumer",method = RequestMethod.GET)
    public String helloConsumer(){
        return restTemplate.getForEntity("http://TEXT-CORRECTION/test/hello", String.class).getBody();
    }
}
