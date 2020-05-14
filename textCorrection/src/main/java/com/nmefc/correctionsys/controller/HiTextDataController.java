package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.entity.HiTextData;
import com.nmefc.correctionsys.entity.TextDataExample;
import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.service.HiTextDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    /**
     *@Description:（2）根据id删除历史文本记录
     *@Param: [id]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 15:28
     */
    @PostMapping(value = "/delete")
    public Integer delete(Integer id){
        if (id == null){return 0;}
        return hiTextDataService.deleteByPrimaryKey(id);
    }
    /**
     *@Description: 定时任务：（1）实时库转历史库：将text_data
     * 中的数据全部复制到hi_text_data中，并清空text_data中所有数据.
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 15:47
     */
    @GetMapping(value = "/deleteAll")
    public Integer deleteAll(){
        return hiTextDataService.saveHiTextData();
    }
}
