package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.TextData;
import com.nmefc.correctionsys.entity.TextDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TextDataMapper extends BaseMapper<TextData, Integer, TextDataExample>{


    List<TextData> selectByExampleWithBLOBs(TextDataExample example);


    int updateByExampleWithBLOBs(@Param("record") TextData record, @Param("example") TextDataExample example);


    int updateByPrimaryKeyWithBLOBs(TextData record);


}