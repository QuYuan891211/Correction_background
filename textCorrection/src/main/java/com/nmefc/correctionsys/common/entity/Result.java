package com.nmefc.correctionsys.common.entity;
/**
 *@Description: 统一返回前端的响应类
 *@Param:
 *@Return:
 *@Author: QuYuan
 *@Date: 2020/4/25 10:27
 */
public class Result<T> {
    private T date;
    private String message;
    private int code;
    public Result(){

    }

    public Result(T date, String message, int code) {
        this.date = date;
        this.message = message;
        this.code = code;
    }

    public Result(String message, int code) {
        this(null, message, code);
    }

    public Result(T date) {
        this(date, "success", 200);
    }
}
