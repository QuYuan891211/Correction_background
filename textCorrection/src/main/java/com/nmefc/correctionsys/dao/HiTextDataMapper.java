package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.HiTextData;
import com.nmefc.correctionsys.entity.HiTextDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HiTextDataMapper extends BaseMapper<HiTextData,Integer,HiTextDataExample> {

    List<HiTextData> selectByExampleWithBLOBs(HiTextDataExample example);

    int updateByExampleWithBLOBs(@Param("record") HiTextData record, @Param("example") HiTextDataExample example);


    int updateByPrimaryKeyWithBLOBs(HiTextData record);

}