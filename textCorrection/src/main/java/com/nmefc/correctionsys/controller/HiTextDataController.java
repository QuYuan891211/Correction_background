package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.entity.HiTextData;
import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.service.HiTextDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "hiTextData")
public class HiTextDataController {
    @Autowired
    private HiTextDataService hiTextDataService;
    /**
     *@Description:（4）按日期查询历史文本
     *@Param: [start, end]
     *@Return: java.util.List<com.nmefc.correctionsys.entity.HiTextData>
     *@Author: QuYuan
     *@Date: 2020/5/14 14:27
     */
    @GetMapping(value = "/getByDate")
    public List<HiTextData> getHiTextDataByDate(Date start, Date end){
        if(start == null || end == null || start.after(end)){return null;}
        return hiTextDataService.getHiTextDataByDate(start,end);
    }
    /**
     *@Description:（5）按日期查询历史文本相关的模板
     *@Param: [start, end]
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/14 15:08
     */
    @GetMapping(value = "/getTextInfoByDate")
    public List<TextInfo> getTextInfoByDate(Date start, Date end){
        if(start == null || end == null || start.after(end)){return null;}
        return hiTextDataService.getTextInfoByDate(start,end);
    }
}
