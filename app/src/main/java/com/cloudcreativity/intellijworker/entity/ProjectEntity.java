package com.cloudcreativity.intellijworker.entity;

import java.io.Serializable;

public class ProjectEntity implements Serializable {
    private int pid;
    private int pstate;
    private int projectNum;
    private int did;
    private String projectName;
    private String projectAddr;
    private int cityId;
    private String buildSize;
    private String investSize;
    private String projectType;
    private String investMain;
    private String mainboardNum;
    private int isWarning;
    private String startTime;
    private String endTime;
    private String chargeName;
    private String chargePhone;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPstate() {
        return pstate;
    }

    public void setPstate(int pstate) {
        this.pstate = pstate;
    }

    public int getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(int projectNum) {
        this.projectNum = projectNum;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectAddr() {
        return projectAddr;
    }

    public void setProjectAddr(String projectAddr) {
        this.projectAddr = projectAddr;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getBuildSize() {
        return buildSize;
    }

    public void setBuildSize(String buildSize) {
        this.buildSize = buildSize;
    }

    public String getInvestSize() {
        return investSize;
    }

    public void setInvestSize(String investSize) {
        this.investSize = investSize;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getInvestMain() {
        return investMain;
    }

    public void setInvestMain(String investMain) {
        this.investMain = investMain;
    }

    public String getMainboardNum() {
        return mainboardNum;
    }

    public void setMainboardNum(String mainboardNum) {
        this.mainboardNum = mainboardNum;
    }

    public int getIsWarning() {
        return isWarning;
    }

    public void setIsWarning(int isWarning) {
        this.isWarning = isWarning;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName;
    }

    public String getChargePhone() {
        return chargePhone;
    }

    public void setChargePhone(String chargePhone) {
        this.chargePhone = chargePhone;
    }
}
