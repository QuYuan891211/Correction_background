package com.nmefc.testribbon.entity.API;

public class CorrectData {
    private String stationAbb;
    private String elementAbb;
    private Integer prescriptionNum;

    public String getStationAbb() {
        return stationAbb;
    }

    public void setStationAbb(String stationAbb) {
        this.stationAbb = stationAbb;
    }

    public String getElementAbb() {
        return elementAbb;
    }

    public void setElementAbb(String elementAbb) {
        this.elementAbb = elementAbb;
    }

    public Integer getPrescriptionNum() {
        return prescriptionNum;
    }

    public void setPrescriptionNum(Integer prescriptionNum) {
        this.prescriptionNum = prescriptionNum;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    private boolean checked;
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
