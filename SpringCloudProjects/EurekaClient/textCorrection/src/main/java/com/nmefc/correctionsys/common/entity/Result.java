package com.nmefc.correctionsys.common.entity;

import java.util.List;

/**
 *@Description: 统一返回前端的响应类
 *@Param:
 *@Return:
 *@Author: QuYuan
 *@Date: 2020/4/25 10:27
 */
public class Result<T> {
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    private List<T> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private String message;
    private int code;
    public Result(){

    }

    public Result(List<T> data, String message, int code) {
        this.data = data;
        this.message = message;
        this.code = code;
    }

}
