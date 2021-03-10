package com.nmefc.testribbon.entity.API;

import java.util.Date;
import java.util.List;

public class CorrectPacket {
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<CorrectData> getDataList() {
        return dataList;
    }

    public void setDataList(List<CorrectData> dataList) {
        this.dataList = dataList;
    }

    private List<CorrectData> dataList;
}
