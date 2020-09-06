package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.HiTextDetail;
import com.nmefc.correctionsys.entity.HiTextDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HiTextDetailMapper {
    int countByExample(HiTextDetailExample example);

    int deleteByExample(HiTextDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(HiTextDetail record);

    int insertSelective(HiTextDetail record);

    List<HiTextDetail> selectByExampleWithBLOBs(HiTextDetailExample example);

    List<HiTextDetail> selectByExample(HiTextDetailExample example);

    HiTextDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") HiTextDetail record, @Param("example") HiTextDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") HiTextDetail record, @Param("example") HiTextDetailExample example);

    int updateByExample(@Param("record") HiTextDetail record, @Param("example") HiTextDetailExample example);

    int updateByPrimaryKeySelective(HiTextDetail record);

    int updateByPrimaryKeyWithBLOBs(HiTextDetail record);

    int updateByPrimaryKey(HiTextDetail record);
}