package com.nmefc.correctionsys.service;

import com.nmefc.correctionsys.entity.TextInfo;
import com.nmefc.correctionsys.entity.TextInfoExample;
import com.nmefc.correctionsys.entity.TextInfoKey;

public interface TextInfoService extends BaseService<TextInfo,TextInfoKey,TextInfoExample> {
    boolean isDelete(Integer id);
}
