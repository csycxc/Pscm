package com.banry.pscm.service.schedule;

public class SubDivWorkBillChange extends SubDivWorkBill {

    private Double engNumChange;

    private String unitPriceChange;

    private Integer changeType;

    private SubDivWorkChangeBill subDivWorkChangeBill;

    public Double getEngNumChange() {
        return engNumChange;
    }

    public void setEngNumChange(Double engNumChange) {
        this.engNumChange = engNumChange;
    }

    public String getUnitPriceChange() {
        return unitPriceChange;
    }

    public void setUnitPriceChange(String unitPriceChange) {
        this.unitPriceChange = unitPriceChange;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public SubDivWorkChangeBill getSubDivWorkChangeBill() {
        return subDivWorkChangeBill;
    }

    public void setSubDivWorkChangeBill(SubDivWorkChangeBill subDivWorkChangeBill) {
        this.subDivWorkChangeBill = subDivWorkChangeBill;
    }
}
