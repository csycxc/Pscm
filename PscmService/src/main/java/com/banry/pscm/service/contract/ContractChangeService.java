package com.banry.pscm.service.contract;

import java.util.List;

public interface ContractChangeService {

    List<DownContractChangeVo> findContractChanges(String originalContractName, Integer status);

    DownContractChangeVo getChangeContract(String changeCode);

    void saveChangeContract(DownContractChangeWithBLOBs downContractChangeWithBLOBs);

    void updateChangeContract(DownContractChangeWithBLOBs downContractChangeWithBLOBs);

    void deleteChangeContract(String changeCode);
}
