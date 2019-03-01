package com.cloudcreativity.intellijworker.entity;

import java.io.Serializable;

public class UserProjectEntity implements Serializable {
    private String logid;
    private int lstate;
    private int wid;
    private int did;
    private int pid;
    private int workerType;
    private String workerTypeName;
    private String workerSalary;
    private String contract;
    private String cardNum;
    private String workerNum;
    private int warnState;
    private String deptName;
    private String projectName;
    private ProjectEntity projectDomain;

    public String getLogid() {
        return logid;
    }

    public void setLogid(String logid) {
        this.logid = logid;
    }

    public int getLstate() {
        return lstate;
    }

    public void setLstate(int lstate) {
        this.lstate = lstate;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public int getDid() {
        return did;
    }

    public void setDid(int did) {
        this.did = did;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getWorkerType() {
        return workerType;
    }

    public void setWorkerType(int workerType) {
        this.workerType = workerType;
    }

    public String getWorkerTypeName() {
        return workerTypeName;
    }

    public void setWorkerTypeName(String workerTypeName) {
        this.workerTypeName = workerTypeName;
    }

    public String getWorkerSalary() {
        return workerSalary;
    }

    public void setWorkerSalary(String workerSalary) {
        this.workerSalary = workerSalary;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(String workerNum) {
        this.workerNum = workerNum;
    }

    public int getWarnState() {
        return warnState;
    }

    public void setWarnState(int warnState) {
        this.warnState = warnState;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ProjectEntity getProjectDomain() {
        return projectDomain;
    }

    public void setProjectDomain(ProjectEntity projectDomain) {
        this.projectDomain = projectDomain;
    }
}
