package com.nmefc.correctionsys.entity;

import java.util.Date;

public class TextInfo extends TextInfoKey {
    private Date gmtModified;

    private Date gmtCreate;

    private Boolean isDelete;

    private String tName;

    private String tAbbreviation;

    private Integer department;

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName == null ? null : tName.trim();
    }

    public String gettAbbreviation() {
        return tAbbreviation;
    }

    public void settAbbreviation(String tAbbreviation) {
        this.tAbbreviation = tAbbreviation == null ? null : tAbbreviation.trim();
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }
}