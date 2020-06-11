package com.nmefc.springcloud.clientcommon.beans;

import com.nmefc.springcloud.clientcommon.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class ResponseResultBean<T> extends BaseResponseResultBean {
    private T result;


}
