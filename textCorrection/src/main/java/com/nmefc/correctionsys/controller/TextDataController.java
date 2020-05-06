package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.entity.TextData;
import com.nmefc.correctionsys.service.TextDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "textData")
public class TextDataController {
    @Autowired
    private TextDataService textDataService;
    /**
     *@Description:（6）根据模板id查询当日文本记录
     *@Param: [tid]
     *@Return: com.nmefc.correctionsys.entity.TextData
     *@Author: QuYuan
     *@Date: 2020/5/6 20:42
     */
    @GetMapping(value = "/textData")
   public TextData getTextDataById(Integer tid){
       //1.数据校验
       if(tid == null){return null;}
       return textDataService.getTextDataById(tid);
   }
}
