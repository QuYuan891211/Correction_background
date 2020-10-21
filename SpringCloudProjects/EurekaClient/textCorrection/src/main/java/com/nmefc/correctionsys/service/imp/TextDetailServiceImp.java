package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.common.utils.DateTimeUtils;
import com.nmefc.correctionsys.dao.TextDetailMapper;
import com.nmefc.correctionsys.entity.*;
import com.nmefc.correctionsys.service.TextDataService;
import com.nmefc.correctionsys.service.TextDetailService;
import com.nmefc.correctionsys.service.TextInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("textDetailService")
public class TextDetailServiceImp extends BaseServiceImp<TextDetail,Integer,TextDetailExample> implements TextDetailService {
    @Autowired
    private TextDetailMapper textDetailMapper;
    @Autowired
    private TextInfoService textInfoService;
    @Autowired
    private TextDataService textDataService;

    @Override
    boolean checkParameters(TextDetail textDetail) {
        return false;
    }

    @Transactional
    @Override
    public Integer updateTextDetail(TextDetail textDetail) {
        return textDetailMapper.updateByPrimaryKeyWithBLOBs(textDetail);
    }

    @Override
    public List<TextDetail> findByTextDataId(Integer id) {
        TextDetailExample textDetailExample = new TextDetailExample();
        textDetailExample.createCriteria().andTextDataIdEqualTo(id);
        textDetailExample.setOrderByClause("interval_id ASC");
        return textDetailMapper.selectByExampleWithBLOBs(textDetailExample);
    }

    @Override
    public List<TextDetail> getLastDayTextDetailById(Integer id) {
        List<TextInfo> textInfoList =  textInfoService.getVersionListById(id);
        TextInfoKey textInfoKey = new TextInfoKey();
        textInfoKey.setTid(textInfoList.get(0).getTid());
        textInfoKey.settVersion(textInfoList.get(0).gettVersion());
        TextData textData = textDataService.getLastDayTextData(textInfoKey);
        if(null != textData && textData.getId() != null){
           return this.findByTextDataId(textData.getId());
        }
//        TextDetailExample textDetailExample = new TextDetailExample();
//        DateTimeUtils.initDateByDay();
//        textDetailExample.or().andGmtModifiedBetween(DateTimeUtils.initLastDateByDay(),DateTimeUtils.initDateByDay()).andTextDataIdEqualTo(id);
//        textDetailExample.setOrderByClause("interval_id ASC");
        return null;
    }
}
