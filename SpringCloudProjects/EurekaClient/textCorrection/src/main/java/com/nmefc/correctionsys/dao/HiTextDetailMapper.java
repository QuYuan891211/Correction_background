package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.HiTextDetail;
import com.nmefc.correctionsys.entity.HiTextDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HiTextDetailMapper extends BaseMapper<HiTextDetail,Integer,HiTextDetailExample>{

    List<HiTextDetail> selectByExampleWithBLOBs(HiTextDetailExample example);


    int updateByExampleWithBLOBs(@Param("record") HiTextDetail record, @Param("example") HiTextDetailExample example);


    int updateByPrimaryKeyWithBLOBs(HiTextDetail record);


}