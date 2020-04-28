package com.nmefc.correctionsys.service.imp;

import com.nmefc.correctionsys.mapper.TextInfoDao;
import com.nmefc.correctionsys.service.TextInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("textInfoService")
public class TextInfoServiceImp implements TextInfoService{
    @Autowired
    private TextInfoDao textInfoDao;
    @Override
    public Integer test(Integer id) {
        return textInfoDao.test(id);
    }
}
