package com.nmefc.correctionsys.dao;

import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.entity.TextInfoExample;
import com.nmefc.correctionsys.entity.TextInfoKey;


public interface TextInfoMapper extends BaseMapper<TextInfo,TextInfoKey,TextInfoExample>{
    Integer softDeleteByTid(TextInfo textInfo);
}