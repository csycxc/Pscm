package com.banry.pscm.service.contract;

public class DownContractChangeVo extends DownContractChangeWithBLOBs {

    private String originalContractName;

    private String contractPartSecond;

    private Double contractAmount;

    private String taskId;

    public String getOriginalContractName() {
        return originalContractName;
    }

    public void setOriginalContractName(String originalContractName) {
        this.originalContractName = originalContractName;
    }

    public String getContractPartSecond() {
        return contractPartSecond;
    }

    public void setContractPartSecond(String contractPartSecond) {
        this.contractPartSecond = contractPartSecond;
    }

    public Double getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
