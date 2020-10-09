package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.dao.TextDetailMapper;
import com.nmefc.correctionsys.entity.TextDetail;
import com.nmefc.correctionsys.entity.TextDetailExample;
import com.nmefc.correctionsys.service.TextDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("textDetailService")
public class TextDetailServiceImp extends BaseServiceImp<TextDetail,Integer,TextDetailExample> implements TextDetailService {
    @Autowired
    private TextDetailMapper textDetailMapper;

    @Override
    boolean checkParameters(TextDetail textDetail) {
        return false;
    }

    @Transactional
    @Override
    public Integer updateTextDetail(TextDetail textDetail) {
        return textDetailMapper.updateByPrimaryKeyWithBLOBs(textDetail);
    }
}
