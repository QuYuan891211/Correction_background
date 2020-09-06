package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.TextDetail;
import com.nmefc.correctionsys.entity.TextDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TextDetailMapper extends BaseMapper<TextDetail, Integer, TextDetailExample>{

    List<TextDetail> selectByExampleWithBLOBs(TextDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") TextDetail record, @Param("example") TextDetailExample example);

    int updateByPrimaryKeyWithBLOBs(TextDetail record);

}