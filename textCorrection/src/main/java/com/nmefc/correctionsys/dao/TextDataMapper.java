package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.TextData;
import com.nmefc.correctionsys.entity.TextDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TextDataMapper extends BaseMapper<TextData,Integer,TextDataExample>{
    /**
     *@Description: 包含大字段（TEXT）的查询
     *@Param: [example]
     *@Return: java.util.List<com.nmefc.correctionsys.entity.TextData>
     *@Author: QuYuan
     *@Date: 2020/5/6 19:56
     */
    List<TextData> selectByExampleWithBLOBs(TextDataExample example);
    /**
     *@Description: 包含大字段（TEXT）的更新
     *@Param: [record, example]
     *@Return: int
     *@Author: QuYuan
     *@Date: 2020/5/6 19:56
     */
    int updateByExampleWithBLOBs(@Param("record") TextData record, @Param("example") TextDataExample example);
    /**
     *@Description: 包含大字段（TEXT）的查询
     *@Param: [record]
     *@Return: int
     *@Author: QuYuan
     *@Date: 2020/5/6 19:56
     */
    int updateByPrimaryKeyWithBLOBs(TextData record);

}