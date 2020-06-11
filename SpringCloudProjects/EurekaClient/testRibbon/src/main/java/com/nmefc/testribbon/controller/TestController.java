package com.nmefc.testribbon.controller;

import com.nmefc.testribbon.entity.TextInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;

@RestController
@RequestMapping(value = "test")
public class TestController {

    @Autowired
    RestTemplate restTemplate;

    @PostMapping(value = "/getTextInfo")
    public String getTextInfo(Integer id, Integer version){
         MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        paramMap.add("id", id);
        paramMap.add("version", version);
        /**
         *  text-correction: 去调用的微服务名称
         *  /textInfo： 调用的controller
         *  /getTextInfo",调用的方法
         *  paramMap： 参数Map
         *  TextInfo.class： 返回值类型
         */
        ResponseEntity<TextInfo> responseEntity =  restTemplate.postForEntity(
                "http://text-correction/textInfo/getTextInfo",paramMap,TextInfo.class
        );
        // 如果状态等于200则证明查询成功。否则接口调用失败

        if (responseEntity.getStatusCodeValue() == 200) {
            TextInfo textInfo = responseEntity.getBody();
            return "success";
        }
        return "false";
    }
}
