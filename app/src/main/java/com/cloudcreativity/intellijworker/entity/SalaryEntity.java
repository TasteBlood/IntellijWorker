package com.cloudcreativity.intellijworker.entity;

public class SalaryEntity {
    private int wsId;
    private int pbId;
    private double realSalary;
    private int state;
    private int isNormal;
    private String createTime;
    private int wid;
    private int pid;

    public int getWsId() {
        return wsId;
    }

    public void setWsId(int wsId) {
        this.wsId = wsId;
    }

    public int getPbId() {
        return pbId;
    }

    public void setPbId(int pbId) {
        this.pbId = pbId;
    }

    public double getRealSalary() {
        return realSalary;
    }

    public void setRealSalary(double realSalary) {
        this.realSalary = realSalary;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(int isNormal) {
        this.isNormal = isNormal;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
