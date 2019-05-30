package com.cloudcreativity.intellijworker.entity;

public class SalaryEntity {
        private int id;
        private int pbId;
        private int wId;
        private String idCardType;
        private String idCardNumber;
        private int days;
        private int workHours;
        private String payRollBankCardNumber;
        private String payRollBankCode;
        private String payRollBankName;
        private String payBankCardNumber;
        private String payBankCode;
        private String payBankName;
        private int bankId;
        private int totalPayAmount;
        private int actualAmount;
        private int isBackPay;
        private String balanceDate;
        private String backPayMonth;
        private String thirdPayRollCode;
        private String createTime;
        private long updateTime;
        private int state;
        private int warnState;
        private String workerDomain;
        private String proBankDomain;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPbId() {
        return pbId;
    }

    public void setPbId(int pbId) {
        this.pbId = pbId;
    }

    public int getwId() {
        return wId;
    }

    public void setwId(int wId) {
        this.wId = wId;
    }

    public String getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(String idCardType) {
        this.idCardType = idCardType;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getWorkHours() {
        return workHours;
    }

    public void setWorkHours(int workHours) {
        this.workHours = workHours;
    }

    public String getPayRollBankCardNumber() {
        return payRollBankCardNumber;
    }

    public void setPayRollBankCardNumber(String payRollBankCardNumber) {
        this.payRollBankCardNumber = payRollBankCardNumber;
    }

    public String getPayRollBankCode() {
        return payRollBankCode;
    }

    public void setPayRollBankCode(String payRollBankCode) {
        this.payRollBankCode = payRollBankCode;
    }

    public String getPayRollBankName() {
        return payRollBankName;
    }

    public void setPayRollBankName(String payRollBankName) {
        this.payRollBankName = payRollBankName;
    }

    public String getPayBankCardNumber() {
        return payBankCardNumber;
    }

    public void setPayBankCardNumber(String payBankCardNumber) {
        this.payBankCardNumber = payBankCardNumber;
    }

    public String getPayBankCode() {
        return payBankCode;
    }

    public void setPayBankCode(String payBankCode) {
        this.payBankCode = payBankCode;
    }

    public String getPayBankName() {
        return payBankName;
    }

    public void setPayBankName(String payBankName) {
        this.payBankName = payBankName;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getTotalPayAmount() {
        return totalPayAmount;
    }

    public void setTotalPayAmount(int totalPayAmount) {
        this.totalPayAmount = totalPayAmount;
    }

    public int getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(int actualAmount) {
        this.actualAmount = actualAmount;
    }

    public int getIsBackPay() {
        return isBackPay;
    }

    public void setIsBackPay(int isBackPay) {
        this.isBackPay = isBackPay;
    }

    public String getBalanceDate() {
        return balanceDate;
    }

    public void setBalanceDate(String balanceDate) {
        this.balanceDate = balanceDate;
    }

    public String getBackPayMonth() {
        return backPayMonth;
    }

    public void setBackPayMonth(String backPayMonth) {
        this.backPayMonth = backPayMonth;
    }

    public String getThirdPayRollCode() {
        return thirdPayRollCode;
    }

    public void setThirdPayRollCode(String thirdPayRollCode) {
        this.thirdPayRollCode = thirdPayRollCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getWarnState() {
        return warnState;
    }

    public void setWarnState(int warnState) {
        this.warnState = warnState;
    }

    public String getWorkerDomain() {
        return workerDomain;
    }

    public void setWorkerDomain(String workerDomain) {
        this.workerDomain = workerDomain;
    }

    public String getProBankDomain() {
        return proBankDomain;
    }

    public void setProBankDomain(String proBankDomain) {
        this.proBankDomain = proBankDomain;
    }

    public String getTime(){
        return "共出勤"+this.days+"天,总共时"+this.workHours;
    }
}
