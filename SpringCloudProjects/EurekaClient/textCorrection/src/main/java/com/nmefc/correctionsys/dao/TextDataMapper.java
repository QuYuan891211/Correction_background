package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.TextData;
import com.nmefc.correctionsys.entity.TextDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TextDataMapper {
    int countByExample(TextDataExample example);

    int deleteByExample(TextDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TextData record);

    int insertSelective(TextData record);

    List<TextData> selectByExample(TextDataExample example);

    TextData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TextData record, @Param("example") TextDataExample example);

    int updateByExample(@Param("record") TextData record, @Param("example") TextDataExample example);

    int updateByPrimaryKeySelective(TextData record);

    int updateByPrimaryKey(TextData record);
}