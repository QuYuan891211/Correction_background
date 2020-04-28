package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.service.TextInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private TextInfoService textInfoService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    @GetMapping(value = "/test")
    public String rest(){
        LOGGER.info("调用了此服务");
        Integer id = textInfoService.test(1);
        return "hello word !   " + id;
    }
}
