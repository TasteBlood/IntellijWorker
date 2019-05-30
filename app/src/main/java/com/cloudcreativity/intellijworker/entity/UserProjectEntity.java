package com.cloudcreativity.intellijworker.entity;

import java.io.Serializable;

public class UserProjectEntity implements Serializable {
    private int pid;
    private int wId;
    private String projectCode;
    private String profession;
    private String name;
    private String proStatus;
    private String contractorCorpName;
    private String startDate;
    private String completeDate;

    public int getwId() {
        return wId;
    }

    public void setwId(int wId) {
        this.wId = wId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProStatus() {
        return proStatus;
    }

    public void setProStatus(String proStatus) {
        this.proStatus = proStatus;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContractorCorpName() {
        return contractorCorpName;
    }

    public void setContractorCorpName(String contractorCorpName) {
        this.contractorCorpName = contractorCorpName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }
}
