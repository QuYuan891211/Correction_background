package com.nmefc.correctionsys.service;

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
    /**
     *@Description:（13）审核人员取消签名
     *@Param: [textData]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/7 13:00
     */
    Integer cancelLastCheck(TextData textData);
    /**
     *@Description:（9）根据文本记录查询文本模板
     *@Param: []
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextInfo>
     *@Author: QuYuan
     *@Date: 2020/5/7 14:00
     */
    List<TextInfo> getTextInfoByTextData();
//    /**
//     *@Description:（2）根据id删除文本记录
//     *@Param: [id]
//     *@Return: java.lang.Integer
//     *@Author: QuYuan
//     *@Date: 2020/5/7 14:39
//     */
//    Integer deleteById(Integer id);
    /**
     *@Description:（1）根据模板新建文本记录
     *@Param: [textInfo]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/7 14:48
     */
    Integer saveOneTextDataByTextInfo(TextInfo textInfo);
    /**
     *@Description: （5）更新当日文本记录
     *@Param: [textData]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/8 11:35
     */
    Integer update(TextData textData);
    /**
     *@Description:（10）预报员确认完成
     *@Param: [textData]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 9:30
     */
    Integer checkByForecaster(TextData textData);
    /**
     *@Description:（11）预报员取消确认
     *@Param: [textData]
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 9:57
     */
    Integer uncheckByForecaster(TextData textData);
    /**
     *@Description: 删除全部
     *@Param: []
     *@Return: java.lang.Integer
     *@Author: QuYuan
     *@Date: 2020/5/14 16:13
     */
    Integer deleteAll();
}
