package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.TextDetail;
import com.nmefc.correctionsys.entity.TextDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TextDetailMapper {
    int countByExample(TextDetailExample example);

    int deleteByExample(TextDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TextDetail record);

    int insertSelective(TextDetail record);

    List<TextDetail> selectByExampleWithBLOBs(TextDetailExample example);

    List<TextDetail> selectByExample(TextDetailExample example);

    TextDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TextDetail record, @Param("example") TextDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") TextDetail record, @Param("example") TextDetailExample example);

    int updateByExample(@Param("record") TextDetail record, @Param("example") TextDetailExample example);

    int updateByPrimaryKeySelective(TextDetail record);

    int updateByPrimaryKeyWithBLOBs(TextDetail record);

    int updateByPrimaryKey(TextDetail record);
}