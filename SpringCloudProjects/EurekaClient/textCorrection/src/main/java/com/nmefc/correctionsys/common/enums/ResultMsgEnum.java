package com.nmefc.correctionsys.common.enums;

public enum ResultMsgEnum {
    SUCCESS("调用接口成功"),
    FAIL("调用接口失败"),
    NULL_DATA("没有查到数据"),
    ERROR_PARAMETER("参数错误");


    public String getMsg() {
        return msg;
    }

    private String msg;
    //不加Set方法，防止外部修改
    private ResultMsgEnum(String msg){
        this.msg = msg;
    }
}
