package com.nmefc.springcloud.clientcommon.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseResponseResultBean{
    private String code;

    private String msg;

    private String subCode;

    private String subMessage;

    private boolean succeed;

    private Date serverTime;
}
