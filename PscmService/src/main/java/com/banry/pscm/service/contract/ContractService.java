package com.banry.pscm.service.contract;

import java.util.List;

public interface ContractService {

    List<ContractTmpl> findContractTemplates();

    List<DownContract> findDownContracts(Integer bizType, Integer status, String contractName);

    DownContract findByDownContractCode(String downContractCode);

    void saveDownContract(DownContract downContract);

    void updateDownContract(DownContract downContract);

    void deleteByContractCode(String contractCode);

    List<DownContract> findContractsBySupplier(String supplierCreditCode);
    
    /**
     * 依据招标结果编码查询状态不是起草和退回的合同
     * @param tenderResultCode
     * @return
     */
    DownContract findDownContractByTenderResultCode(String tenderResultCode);

    /**
     * 根据downContractCode更新作业队名称信息
     * @param downContractCode
     * @param constructionTeam
     * @return
     */
    int updateConstructionTeam(String downContractCode, String constructionTeam);
}
