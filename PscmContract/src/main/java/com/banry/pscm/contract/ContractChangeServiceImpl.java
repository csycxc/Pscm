package com.banry.pscm.contract;

import com.banry.pscm.persist.dao.DownContractChangeMapper;
import com.banry.pscm.service.contract.ContractChangeService;
import com.banry.pscm.service.contract.DownContractChangeVo;
import com.banry.pscm.service.contract.DownContractChangeWithBLOBs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractChangeServiceImpl implements ContractChangeService {

    @Autowired
    private DownContractChangeMapper downContractChangeMapper;

    @Override
    public List<DownContractChangeVo> findContractChanges(String originalContractName, Integer status) {
        return downContractChangeMapper.findContractChanges(originalContractName, status);
    }

    @Override
    public DownContractChangeVo getChangeContract(String changeCode) {

        return downContractChangeMapper.selectByPrimaryKey(changeCode);
    }

    @Override
    public void saveChangeContract(DownContractChangeWithBLOBs downContractChangeWithBLOBs) {
        downContractChangeMapper.insertSelective(downContractChangeWithBLOBs);
    }

    @Override
    public void updateChangeContract(DownContractChangeWithBLOBs downContractChangeWithBLOBs) {
        downContractChangeMapper.updateByPrimaryKeySelective(downContractChangeWithBLOBs);
    }

    @Override
    public void deleteChangeContract(String changeCode) {
        downContractChangeMapper.deleteByPrimaryKey(changeCode);
    }
}
