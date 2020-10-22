package com.nmefc.correctionsys.common.enums;

public enum ResultMsgEnum {
    SUCCESS("调用接口成功"),
    FAIL("调用接口失败"),
    NULL_DATA("没有查到数据"),
    ERROR_PARAMETER("参数错误"),
    NULL_FORECASTER("预报员未确认"),
    NOT_TADAY("不是当日预报文本"),
    NULL_CHECKER("审核员未审核"),
    CONTACT_ADMIN("未知错误，请联系管理员"),
    ERROR_CHECKER("当前登录用户与审核员不一致");


    public String getMsg() {
        return msg;
    }

    private String msg;
    //不加Set方法，防止外部修改
    private ResultMsgEnum(String msg){
        this.msg = msg;
    }
}
