package com.nmefc.correctionsys.entity.midModel;

import java.util.List;

public class TextDataAndTextDetailSaveModel {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<String> getTextDetailList() {
        return textDetailList;
    }

    public void setTextDetailList(List<String> textDetailList) {
        this.textDetailList = textDetailList;
    }

    private List<String> textDetailList;
}
