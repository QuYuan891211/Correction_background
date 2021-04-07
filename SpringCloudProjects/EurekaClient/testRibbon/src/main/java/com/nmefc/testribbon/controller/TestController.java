package com.nmefc.testribbon.controller;

import com.alibaba.fastjson.JSON;
import com.nmefc.testribbon.entity.API.CorrectPacket;
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
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    RestTemplate restTemplate;


    @GetMapping(value = "/getTextInfo")
    public String getTextInfo(){
          MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
        /** paramMap.add("id", id);
        paramMap.add("version", version);
        /**
         *  text-correction: 去调用的微服务名称
         *  /textInfo： 调用的controller
         *  /getTextInfo",调用的方法
         *  paramMap： 参数Map
         *  TextInfo.class： 返回值类型
         */
//        ResponseEntity<TextInfo> responseEntity =  restTemplate.postForEntity(
//                "http://TEXT-CORRECTION/textInfo/getTextInfo",paramMap,TextInfo.class
//        );
//        无参数
        ResponseEntity<CorrectPacket> responseEntity =  restTemplate.getForEntity(
               "http://TEXT-CORRECTION/textData/getCorrectTextToday", CorrectPacket.class
        );
        // 如果状态等于200则证明查询成功。否则接口调用失败

        if (responseEntity.getStatusCodeValue() == 200) {
            CorrectPacket correctPacket = responseEntity.getBody();
            String json = JSON.toJSONString(correctPacket);
            return json;
        }
        return "false";
    }
}
