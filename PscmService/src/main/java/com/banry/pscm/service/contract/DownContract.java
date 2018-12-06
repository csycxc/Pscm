package com.banry.pscm.service.contract;

import com.banry.pscm.service.tender.Supplier;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class DownContract {
    private String downContractCode;

    private String engCode;

    private Integer bizType;

    private String contractName;

    private String contractPartFirst;

    private Supplier contractPartSecond;

    private String contractPartThird;

    private String contractTargetMatter;

    private Double contractAmount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expectedSignDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actualSignDate;

    private String fulfilSpot;

    private Date fulfilDate;

    private Integer fulfilPeriod;

    private Double depositAmount;

    private String breakCost;

    private String riskLevel;

    private String contractPayment;

    private Double contractPayRate;

    private String corporateAssets;

    private Date alarmDate;

    private Integer status;

    private String comtractTmpl;

    private String contractAttach;

    private String scanFilePath;

    private String tenderResultCode;

    private String constructionTeam;

    private String taskId;


    public String getDownContractCode() {
        return downContractCode;
    }

    public void setDownContractCode(String downContractCode) {
        this.downContractCode = downContractCode == null ? null : downContractCode.trim();
    }

    public String getEngCode() {
        return engCode;
    }

    public void setEngCode(String engCode) {
        this.engCode = engCode == null ? null : engCode.trim();
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getContractPartFirst() {
        return contractPartFirst;
    }

    public void setContractPartFirst(String contractPartFirst) {
        this.contractPartFirst = contractPartFirst == null ? null : contractPartFirst.trim();
    }

    public Supplier getContractPartSecond() {
        return contractPartSecond;
    }

    public void setContractPartSecond(Supplier contractPartSecond) {
        this.contractPartSecond = contractPartSecond;
    }

    public String getContractPartThird() {
        return contractPartThird;
    }

    public void setContractPartThird(String contractPartThird) {
        this.contractPartThird = contractPartThird == null ? null : contractPartThird.trim();
    }

    public String getContractTargetMatter() {
        return contractTargetMatter;
    }

    public void setContractTargetMatter(String contractTargetMatter) {
        this.contractTargetMatter = contractTargetMatter == null ? null : contractTargetMatter.trim();
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Date getExpectedSignDate() {
        return expectedSignDate;
    }

    public void setExpectedSignDate(Date expectedSignDate) {
        this.expectedSignDate = expectedSignDate;
    }

    public Date getActualSignDate() {
        return actualSignDate;
    }

    public void setActualSignDate(Date actualSignDate) {
        this.actualSignDate = actualSignDate;
    }

    public String getFulfilSpot() {
        return fulfilSpot;
    }

    public void setFulfilSpot(String fulfilSpot) {
        this.fulfilSpot = fulfilSpot == null ? null : fulfilSpot.trim();
    }

    public Date getFulfilDate() {
        return fulfilDate;
    }

    public void setFulfilDate(Date fulfilDate) {
        this.fulfilDate = fulfilDate;
    }

    public Integer getFulfilPeriod() {
        return fulfilPeriod;
    }

    public void setFulfilPeriod(Integer fulfilPeriod) {
        this.fulfilPeriod = fulfilPeriod;
    }

    public Double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getBreakCost() {
        return breakCost;
    }

    public void setBreakCost(String breakCost) {
        this.breakCost = breakCost == null ? null : breakCost.trim();
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel == null ? null : riskLevel.trim();
    }

    public String getContractPayment() {
        return contractPayment;
    }

    public void setContractPayment(String contractPayment) {
        this.contractPayment = contractPayment == null ? null : contractPayment.trim();
    }

    public Double getContractPayRate() {
        return contractPayRate;
    }

    public void setContractPayRate(Double contractPayRate) {
        this.contractPayRate = contractPayRate;
    }

    public String getCorporateAssets() {
        return corporateAssets;
    }

    public void setCorporateAssets(String corporateAssets) {
        this.corporateAssets = corporateAssets == null ? null : corporateAssets.trim();
    }

    public Date getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(Date alarmDate) {
        this.alarmDate = alarmDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getComtractTmpl() {
        return comtractTmpl;
    }

    public void setComtractTmpl(String comtractTmpl) {
        this.comtractTmpl = comtractTmpl == null ? null : comtractTmpl.trim();
    }

    public String getContractAttach() {
        return contractAttach;
    }

    public void setContractAttach(String contractAttach) {
        this.contractAttach = contractAttach == null ? null : contractAttach.trim();
    }

    public String getScanFilePath() {
        return scanFilePath;
    }

    public void setScanFilePath(String scanFilePath) {
        this.scanFilePath = scanFilePath == null ? null : scanFilePath.trim();
    }

    public String getTenderResultCode() {
        return tenderResultCode;
    }

    public void setTenderResultCode(String tenderResultCode) {
        this.tenderResultCode = tenderResultCode == null ? null : tenderResultCode.trim();
    }

    public String getConstructionTeam() { return constructionTeam; }

    public void setConstructionTeam(String constructionTeam) {
        this.constructionTeam = constructionTeam;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}