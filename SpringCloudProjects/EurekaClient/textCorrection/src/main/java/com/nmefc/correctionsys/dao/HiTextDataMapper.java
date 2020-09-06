package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.HiTextData;
import com.nmefc.correctionsys.entity.HiTextDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HiTextDataMapper {
    int countByExample(HiTextDataExample example);

    int deleteByExample(HiTextDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HiTextData record);

    int insertSelective(HiTextData record);

    List<HiTextData> selectByExample(HiTextDataExample example);

    HiTextData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HiTextData record, @Param("example") HiTextDataExample example);

    int updateByExample(@Param("record") HiTextData record, @Param("example") HiTextDataExample example);

    int updateByPrimaryKeySelective(HiTextData record);

    int updateByPrimaryKey(HiTextData record);
}