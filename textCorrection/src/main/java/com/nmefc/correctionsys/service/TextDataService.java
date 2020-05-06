package com.nmefc.correctionsys.service;

import com.nmefc.correctionsys.entity.TextData;
import com.nmefc.correctionsys.entity.TextDataExample;

public interface TextDataService extends BaseService<TextData,Integer,TextDataExample>{
    /**
     *@Description:（6）根据模板id查询当日文本记录
     *@Param: [tid]
     *@Return: com.nmefc.correctionsys.entity.TextData
     *@Author: QuYuan
     *@Date: 2020/5/6 20:44
     */
    TextData getTextDataById(Integer tid);
    
}
