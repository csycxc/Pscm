package com.banry.pscm.service.contract;

import java.util.Date;

public class ContractTmpl {
    private String contractTmplCode;

    private Integer bizType;

    private String contractTmplName;

    private String source;

    private String version;

    private String uploadByOwnerid;

    private Date uploadDate;

    private String attachement;

    public String getContractTmplCode() {
        return contractTmplCode;
    }

    public void setContractTmplCode(String contractTmplCode) {
        this.contractTmplCode = contractTmplCode == null ? null : contractTmplCode.trim();
    }

    public Integer getBizType() {
        return bizType;
    }

    public void setBizType(Integer bizType) {
        this.bizType = bizType;
    }

    public String getContractTmplName() {
        return contractTmplName;
    }

    public void setContractTmplName(String contractTmplName) {
        this.contractTmplName = contractTmplName == null ? null : contractTmplName.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getUploadByOwnerid() {
        return uploadByOwnerid;
    }

    public void setUploadByOwnerid(String uploadByOwnerid) {
        this.uploadByOwnerid = uploadByOwnerid == null ? null : uploadByOwnerid.trim();
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getAttachement() {
        return attachement;
    }

    public void setAttachement(String attachement) {
        this.attachement = attachement == null ? null : attachement.trim();
    }
}