package com.banry.pscm.service.contract;

public class SettleAccountVo extends SettleAccount {

    private String contractName;

    private DownContract contract;

    private String recreateSettleCode;

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public DownContract getContract() {
        return contract;
    }

    public void setContract(DownContract contract) {
        this.contract = contract;
    }

    public String getRecreateSettleCode() {
        return recreateSettleCode;
    }

    public void setRecreateSettleCode(String recreateSettleCode) {
        this.recreateSettleCode = recreateSettleCode;
    }
}
