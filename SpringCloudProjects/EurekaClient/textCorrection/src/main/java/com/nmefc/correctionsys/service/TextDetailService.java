package com.nmefc.correctionsys.service;

import com.nmefc.correctionsys.entity.TextDetail;
import com.nmefc.correctionsys.entity.TextDetailExample;

public interface TextDetailService extends BaseService<TextDetail,Integer,TextDetailExample>{

    Integer updateTextDetail(TextDetail textDetail);
}
