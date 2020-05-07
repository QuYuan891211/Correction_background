package com.nmefc.correctionsys.service;

import com.nmefc.correctionsys.entity.TextData;
import com.nmefc.correctionsys.entity.TextDataExample;

import java.util.List;

public interface TextDataService extends BaseService<TextData,Integer,TextDataExample>{
    /**
     *@Description:（6）根据模板id查询当日文本记录
     *@Param: [tid]
     *@Return: com.nmefc.correctionsys.entity.TextData
     *@Author: QuYuan
     *@Date: 2020/5/6 20:44
     */
    TextData getTextDataById(Integer tid);
    /**
     *@Description:（7）查询当日已编辑文本记录
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextData>
     *@Author: QuYuan
     *@Date: 2020/5/7 9:04
     */
    List<TextData> getAll();
    /**
     *@Description:（12）审核人员签名
     *@Param: [textData]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/7 10:00
     */
    Integer lastCheck(TextData textData);
}
