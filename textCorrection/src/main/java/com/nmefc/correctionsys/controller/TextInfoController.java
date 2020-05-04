package com.nmefc.correctionsys.controller;

import com.nmefc.correctionsys.service.TextInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/textInfo")
public class TextInfoController {
    @Autowired
    private TextInfoService textInfoService;






    

    /**
     *@Description:（8）查询指定文字模板的是否已删除
     *@Param: [id]
     *@Return: boolean
     *@Author: QuYuan
     *@Date: 2020/5/4 23:18
     */
    @GetMapping(value = "/isDelete")
    public boolean isDelete(Integer id){
        //1. 数据校验
        if(id == null){return false;}
        return textInfoService.isDelete(id);

    }
}
