package com.nmefc.correctionsys.service;

import com.nmefc.correctionsys.entity.HiTextData;
import com.nmefc.correctionsys.entity.HiTextDataExample;
import com.nmefc.correctionsys.entity.TextInfo;

import java.util.Date;
import java.util.List;

public interface HiTextDataService extends BaseService<HiTextData,Integer,HiTextDataExample> {
    /**
     *@Description:（4）按日期查询历史文本
     *@Param: [start, end]
     *@Return: java.util.List<com.nmefc.correctionsys.entity.HiTextData>
     *@Author: QuYuan
     *@Date: 2020/5/14 14:25
     */
    List<HiTextData> getHiTextDataByDate(Date start, Date end);
    /**
     *@Description:（5）按日期查询历史文本相关的模板
     *@Param: [start, end]
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/14 14:40
     */
    List<TextInfo> getTextInfoByDate(Date start, Date end);


//    Integer saveHiTextData();
}
