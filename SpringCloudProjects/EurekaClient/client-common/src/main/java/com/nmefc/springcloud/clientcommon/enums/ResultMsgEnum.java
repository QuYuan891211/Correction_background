package com.nmefc.springcloud.clientcommon.enums;

public enum ResultMsgEnum {
    SUCCESS("Call interface successfully"),
    FAIL("Fail to call interface");

    public String getMsg() {
        return msg;
    }

    private String msg;
    //不加Set方法，防止外部修改
    private ResultMsgEnum(String msg){
        this.msg = msg;
    }
}
