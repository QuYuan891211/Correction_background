package com.nmefc.correctionsys.entity.API;

import java.util.Date;

public class ResponseInfo {

    public CorrectPacket result;
    public Date serverTime;
    public boolean succeed;
    public String subMessage;
    public String subCode;
    public String msg;
    public String code;

    public CorrectPacket getResult() {
        return result;
    }

    public void setResult(CorrectPacket result) {
        this.result = result;
    }

    public Date getServerTime() {
        return serverTime;
    }

    public void setServerTime(Date serverTime) {
        this.serverTime = serverTime;
    }

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getSubMessage() {
        return subMessage;
    }

    public void setSubMessage(String subMessage) {
        this.subMessage = subMessage;
    }

    public String getSubCode() {
        return subCode;
    }

    public void setSubCode(String subCode) {
        this.subCode = subCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
