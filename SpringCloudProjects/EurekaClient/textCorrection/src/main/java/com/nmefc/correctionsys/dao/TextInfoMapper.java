package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.entity.TextInfoExample;
import com.nmefc.correctionsys.entity.TextInfoKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TextInfoMapper {
    int countByExample(TextInfoExample example);

    int deleteByExample(TextInfoExample example);

    int deleteByPrimaryKey(TextInfoKey key);

    int insert(TextInfo record);

    int insertSelective(TextInfo record);

    List<TextInfo> selectByExample(TextInfoExample example);

    TextInfo selectByPrimaryKey(TextInfoKey key);

    int updateByExampleSelective(@Param("record") TextInfo record, @Param("example") TextInfoExample example);

    int updateByExample(@Param("record") TextInfo record, @Param("example") TextInfoExample example);

    int updateByPrimaryKeySelective(TextInfo record);

    int updateByPrimaryKey(TextInfo record);
}