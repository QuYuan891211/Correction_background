package com.nmefc.correctionsys.service;

import com.nmefc.correctionsys.entity.TextDetail;
import com.nmefc.correctionsys.entity.TextDetailExample;

import java.util.List;

public interface TextDetailService extends BaseService<TextDetail,Integer,TextDetailExample>{

    Integer updateTextDetail(TextDetail textDetail);

    List<TextDetail> findByTextDataId(Integer id);
}
