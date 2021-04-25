package com.nmefc.correctionsys.service;

import com.nmefc.correctionsys.entity.API.CorrectData;
import com.nmefc.correctionsys.entity.TextDetail;
import com.nmefc.correctionsys.entity.TextDetailExample;

import java.util.Date;
import java.util.List;

public interface TextDetailService extends BaseService<TextDetail,Integer,TextDetailExample>{

    Integer updateTextDetail(TextDetail textDetail);

    List<TextDetail> findByTextDataId(Integer id);

    List<TextDetail> getLastDayTextDetailById(Integer id);

    Boolean saveTextDetailByAutoProject(List<CorrectData> correctDataList, Date date);
}
