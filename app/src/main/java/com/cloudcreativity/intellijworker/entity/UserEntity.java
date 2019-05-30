package com.cloudcreativity.intellijworker.entity;

import java.io.Serializable;

public class UserEntity implements Serializable {
    private int id;
    private String idCardNumber;
    private String password;
    private String token;
    private String createTime;
    private WorkEntity workerDomain;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public WorkEntity getWorkerDomain() {
        return workerDomain;
    }

    public void setWorkerDomain(WorkEntity workerDomain) {
        this.workerDomain = workerDomain;
    }

    public static class WorkEntity{
        private int id;
        private int corpId;
        private String projectCode;
        private String corpCode;
        private String projectCropTeamId;
        private String workerName;
        private String birthday;
        private int age;
        private int isTeamLeader;
        private String idCardType;
        private String idCardNumber;
        private String workType;
        private int workRole;
        private String issueCardDate;
        private String issueCardPic;
        private String cardNumber;
        private String payRollBankCardNumber;
        private String payRollBankName;
        private String bankLinkNumber;
        private String payRollTopBankCode;
        private String hasBuyInsurance;
        private String nation;
        private String address;
        private String headImage;
        private String politicsType;
        private String joinedTime;
        private String cellPhone;
        private String cultureLevelType;
        private String specialty;
        private String hasBadMedicalHistory;
        private String urgentLinkMan;
        private String urgentLinkManPhone;
        private String workDate;
        private String maritalStatus;
        private String grantOrg;
        private String positiveIDCardImage;
        private String negativeIDCardImage;
        private String startDate;
        private String expiryDate;
        private String creatTime;
        private String updateTime;
        private String legalManIdCardTypeDomain;
        private WorkTypeEntity workerTypeDomain;
        private String workRoleDomain;
        private String cultureLevelTypeDomain;
        private String politicsTypeDomain;
        private String gender;
        private ProjectCorpEntity projectCorpTeamDomain;
        private String isSelect;
        private int hasContract;
        private String projectCorpDomain;
        private String date;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getCorpId() {
            return corpId;
        }

        public void setCorpId(int corpId) {
            this.corpId = corpId;
        }

        public String getProjectCode() {
            return projectCode;
        }

        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }

        public String getCorpCode() {
            return corpCode;
        }

        public void setCorpCode(String corpCode) {
            this.corpCode = corpCode;
        }

        public String getProjectCropTeamId() {
            return projectCropTeamId;
        }

        public void setProjectCropTeamId(String projectCropTeamId) {
            this.projectCropTeamId = projectCropTeamId;
        }

        public String getWorkerName() {
            return workerName;
        }

        public void setWorkerName(String workerName) {
            this.workerName = workerName;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getIsTeamLeader() {
            return isTeamLeader;
        }

        public void setIsTeamLeader(int isTeamLeader) {
            this.isTeamLeader = isTeamLeader;
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

        public String getWorkType() {
            return workType;
        }

        public void setWorkType(String workType) {
            this.workType = workType;
        }

        public int getWorkRole() {
            return workRole;
        }

        public void setWorkRole(int workRole) {
            this.workRole = workRole;
        }

        public String getIssueCardDate() {
            return issueCardDate;
        }

        public void setIssueCardDate(String issueCardDate) {
            this.issueCardDate = issueCardDate;
        }

        public String getIssueCardPic() {
            return issueCardPic;
        }

        public void setIssueCardPic(String issueCardPic) {
            this.issueCardPic = issueCardPic;
        }

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getPayRollBankCardNumber() {
            return payRollBankCardNumber;
        }

        public void setPayRollBankCardNumber(String payRollBankCardNumber) {
            this.payRollBankCardNumber = payRollBankCardNumber;
        }

        public String getPayRollBankName() {
            return payRollBankName;
        }

        public void setPayRollBankName(String payRollBankName) {
            this.payRollBankName = payRollBankName;
        }

        public String getBankLinkNumber() {
            return bankLinkNumber;
        }

        public void setBankLinkNumber(String bankLinkNumber) {
            this.bankLinkNumber = bankLinkNumber;
        }

        public String getPayRollTopBankCode() {
            return payRollTopBankCode;
        }

        public void setPayRollTopBankCode(String payRollTopBankCode) {
            this.payRollTopBankCode = payRollTopBankCode;
        }

        public String getHasBuyInsurance() {
            return hasBuyInsurance;
        }

        public void setHasBuyInsurance(String hasBuyInsurance) {
            this.hasBuyInsurance = hasBuyInsurance;
        }

        public String getNation() {
            return nation;
        }

        public void setNation(String nation) {
            this.nation = nation;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHeadImage() {
            return headImage;
        }

        public void setHeadImage(String headImage) {
            this.headImage = headImage;
        }

        public String getPoliticsType() {
            return politicsType;
        }

        public void setPoliticsType(String politicsType) {
            this.politicsType = politicsType;
        }

        public String getJoinedTime() {
            return joinedTime;
        }

        public void setJoinedTime(String joinedTime) {
            this.joinedTime = joinedTime;
        }

        public String getCellPhone() {
            return cellPhone;
        }

        public void setCellPhone(String cellPhone) {
            this.cellPhone = cellPhone;
        }

        public String getCultureLevelType() {
            return cultureLevelType;
        }

        public void setCultureLevelType(String cultureLevelType) {
            this.cultureLevelType = cultureLevelType;
        }

        public String getSpecialty() {
            return specialty;
        }

        public void setSpecialty(String specialty) {
            this.specialty = specialty;
        }

        public String getHasBadMedicalHistory() {
            return hasBadMedicalHistory;
        }

        public void setHasBadMedicalHistory(String hasBadMedicalHistory) {
            this.hasBadMedicalHistory = hasBadMedicalHistory;
        }

        public String getUrgentLinkMan() {
            return urgentLinkMan;
        }

        public void setUrgentLinkMan(String urgentLinkMan) {
            this.urgentLinkMan = urgentLinkMan;
        }

        public String getUrgentLinkManPhone() {
            return urgentLinkManPhone;
        }

        public void setUrgentLinkManPhone(String urgentLinkManPhone) {
            this.urgentLinkManPhone = urgentLinkManPhone;
        }

        public String getWorkDate() {
            return workDate;
        }

        public void setWorkDate(String workDate) {
            this.workDate = workDate;
        }

        public String getMaritalStatus() {
            return maritalStatus;
        }

        public void setMaritalStatus(String maritalStatus) {
            this.maritalStatus = maritalStatus;
        }

        public String getGrantOrg() {
            return grantOrg;
        }

        public void setGrantOrg(String grantOrg) {
            this.grantOrg = grantOrg;
        }

        public String getPositiveIDCardImage() {
            return positiveIDCardImage;
        }

        public void setPositiveIDCardImage(String positiveIDCardImage) {
            this.positiveIDCardImage = positiveIDCardImage;
        }

        public String getNegativeIDCardImage() {
            return negativeIDCardImage;
        }

        public void setNegativeIDCardImage(String negativeIDCardImage) {
            this.negativeIDCardImage = negativeIDCardImage;
        }

        public String getStartDate() {
            return startDate;
        }

        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getCreatTime() {
            return creatTime;
        }

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getLegalManIdCardTypeDomain() {
            return legalManIdCardTypeDomain;
        }

        public void setLegalManIdCardTypeDomain(String legalManIdCardTypeDomain) {
            this.legalManIdCardTypeDomain = legalManIdCardTypeDomain;
        }

        public WorkTypeEntity getWorkerTypeDomain() {
            return workerTypeDomain;
        }

        public void setWorkerTypeDomain(WorkTypeEntity workerTypeDomain) {
            this.workerTypeDomain = workerTypeDomain;
        }

        public String getWorkRoleDomain() {
            return workRoleDomain;
        }

        public void setWorkRoleDomain(String workRoleDomain) {
            this.workRoleDomain = workRoleDomain;
        }

        public String getCultureLevelTypeDomain() {
            return cultureLevelTypeDomain;
        }

        public void setCultureLevelTypeDomain(String cultureLevelTypeDomain) {
            this.cultureLevelTypeDomain = cultureLevelTypeDomain;
        }

        public String getPoliticsTypeDomain() {
            return politicsTypeDomain;
        }

        public void setPoliticsTypeDomain(String politicsTypeDomain) {
            this.politicsTypeDomain = politicsTypeDomain;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public ProjectCorpEntity getProjectCorpTeamDomain() {
            return projectCorpTeamDomain;
        }

        public void setProjectCorpTeamDomain(ProjectCorpEntity projectCorpTeamDomain) {
            this.projectCorpTeamDomain = projectCorpTeamDomain;
        }

        public String getIsSelect() {
            return isSelect;
        }

        public void setIsSelect(String isSelect) {
            this.isSelect = isSelect;
        }

        public int getHasContract() {
            return hasContract;
        }

        public void setHasContract(int hasContract) {
            this.hasContract = hasContract;
        }

        public String getProjectCorpDomain() {
            return projectCorpDomain;
        }

        public void setProjectCorpDomain(String projectCorpDomain) {
            this.projectCorpDomain = projectCorpDomain;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    private static class WorkTypeEntity{
        private String profession;
        private int state;
        private String tid;

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }
    }
    private static class ProjectCorpTeamEntity{
        private long teamSysNo;
        private String projectCode;
        private int projectCorpId;
        private String teamName;
        private String responsiblePersonName;
        private String responsiblePersonPhone;
        private String responsiblePersonIDCardType;
        private String responsiblePersonIDNumber;
        private String remark;
        private String entryTime;
        private String exitTime;
        private String createTime;
        private String updateTime;
        private String legalManIdCardTypeDomain;
        private String entryAttachments;
        private String exitAttachments;
        private ProjectCorpEntity projectCorpDomain;
        private String corpCode;
        private String corpName;

        public long getTeamSysNo() {
            return teamSysNo;
        }

        public void setTeamSysNo(long teamSysNo) {
            this.teamSysNo = teamSysNo;
        }

        public String getProjectCode() {
            return projectCode;
        }

        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }

        public int getProjectCorpId() {
            return projectCorpId;
        }

        public void setProjectCorpId(int projectCorpId) {
            this.projectCorpId = projectCorpId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public String getResponsiblePersonName() {
            return responsiblePersonName;
        }

        public void setResponsiblePersonName(String responsiblePersonName) {
            this.responsiblePersonName = responsiblePersonName;
        }

        public String getResponsiblePersonPhone() {
            return responsiblePersonPhone;
        }

        public void setResponsiblePersonPhone(String responsiblePersonPhone) {
            this.responsiblePersonPhone = responsiblePersonPhone;
        }

        public String getResponsiblePersonIDCardType() {
            return responsiblePersonIDCardType;
        }

        public void setResponsiblePersonIDCardType(String responsiblePersonIDCardType) {
            this.responsiblePersonIDCardType = responsiblePersonIDCardType;
        }

        public String getResponsiblePersonIDNumber() {
            return responsiblePersonIDNumber;
        }

        public void setResponsiblePersonIDNumber(String responsiblePersonIDNumber) {
            this.responsiblePersonIDNumber = responsiblePersonIDNumber;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(String entryTime) {
            this.entryTime = entryTime;
        }

        public String getExitTime() {
            return exitTime;
        }

        public void setExitTime(String exitTime) {
            this.exitTime = exitTime;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getLegalManIdCardTypeDomain() {
            return legalManIdCardTypeDomain;
        }

        public void setLegalManIdCardTypeDomain(String legalManIdCardTypeDomain) {
            this.legalManIdCardTypeDomain = legalManIdCardTypeDomain;
        }

        public String getEntryAttachments() {
            return entryAttachments;
        }

        public void setEntryAttachments(String entryAttachments) {
            this.entryAttachments = entryAttachments;
        }

        public String getExitAttachments() {
            return exitAttachments;
        }

        public void setExitAttachments(String exitAttachments) {
            this.exitAttachments = exitAttachments;
        }

        public ProjectCorpEntity getProjectCorpDomain() {
            return projectCorpDomain;
        }

        public void setProjectCorpDomain(ProjectCorpEntity projectCorpDomain) {
            this.projectCorpDomain = projectCorpDomain;
        }

        public String getCorpCode() {
            return corpCode;
        }

        public void setCorpCode(String corpCode) {
            this.corpCode = corpCode;
        }

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }
    }
    private static class ProjectCorpEntity{
        private int id;
        private String projectCode;
        private String corpCode;
        private String corpName;
        private String corpType;
        private String entryTime;
        private String exitTime;
        private String pmName;
        private String pmIDCardType;
        private String pmIDCardNumber;
        private String pmPhone;
        private String createTime;
        private String updateTime;
        private String subcontractorTypeDomain;
        private String legalManIdCardTypeDomain;
        private String bankInfos;
        private String bankDomain;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProjectCode() {
            return projectCode;
        }

        public void setProjectCode(String projectCode) {
            this.projectCode = projectCode;
        }

        public String getCorpCode() {
            return corpCode;
        }

        public void setCorpCode(String corpCode) {
            this.corpCode = corpCode;
        }

        public String getCorpName() {
            return corpName;
        }

        public void setCorpName(String corpName) {
            this.corpName = corpName;
        }

        public String getCorpType() {
            return corpType;
        }

        public void setCorpType(String corpType) {
            this.corpType = corpType;
        }

        public String getEntryTime() {
            return entryTime;
        }

        public void setEntryTime(String entryTime) {
            this.entryTime = entryTime;
        }

        public String getExitTime() {
            return exitTime;
        }

        public void setExitTime(String exitTime) {
            this.exitTime = exitTime;
        }

        public String getPmName() {
            return pmName;
        }

        public void setPmName(String pmName) {
            this.pmName = pmName;
        }

        public String getPmIDCardType() {
            return pmIDCardType;
        }

        public void setPmIDCardType(String pmIDCardType) {
            this.pmIDCardType = pmIDCardType;
        }

        public String getPmIDCardNumber() {
            return pmIDCardNumber;
        }

        public void setPmIDCardNumber(String pmIDCardNumber) {
            this.pmIDCardNumber = pmIDCardNumber;
        }

        public String getPmPhone() {
            return pmPhone;
        }

        public void setPmPhone(String pmPhone) {
            this.pmPhone = pmPhone;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getSubcontractorTypeDomain() {
            return subcontractorTypeDomain;
        }

        public void setSubcontractorTypeDomain(String subcontractorTypeDomain) {
            this.subcontractorTypeDomain = subcontractorTypeDomain;
        }

        public String getLegalManIdCardTypeDomain() {
            return legalManIdCardTypeDomain;
        }

        public void setLegalManIdCardTypeDomain(String legalManIdCardTypeDomain) {
            this.legalManIdCardTypeDomain = legalManIdCardTypeDomain;
        }

        public String getBankInfos() {
            return bankInfos;
        }

        public void setBankInfos(String bankInfos) {
            this.bankInfos = bankInfos;
        }

        public String getBankDomain() {
            return bankDomain;
        }

        public void setBankDomain(String bankDomain) {
            this.bankDomain = bankDomain;
        }
    }

}
