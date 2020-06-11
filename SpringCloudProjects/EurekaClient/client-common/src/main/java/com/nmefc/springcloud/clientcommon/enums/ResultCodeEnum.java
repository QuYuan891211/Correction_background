package com.nmefc.springcloud.clientcommon.enums;
/**
 *@Description: 响应代码的枚举
 *@Param:
 *@Return:
 *@Author: QuYuan
 *@Date: 2020/5/16 13:32
 */
public enum ResultCodeEnum {
    SUCCESS(200),
    FAIL(500);


    public int getCode() {
        return code;
    }

    private int code;
    //不加Set方法，防止外部修改
    private ResultCodeEnum(int code){
        this.code = code;
    }
}
