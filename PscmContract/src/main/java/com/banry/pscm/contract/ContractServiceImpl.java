package com.banry.pscm.contract;

import com.banry.pscm.persist.dao.ContractTmplMapper;
import com.banry.pscm.persist.dao.DownContractChangeMapper;
import com.banry.pscm.persist.dao.DownContractMapper;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.contract.ContractTmpl;
import com.banry.pscm.service.contract.DownContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractTmplMapper contractTmplMapper;

    @Autowired
    private DownContractMapper downContractMapper;

    @Override
    public List<ContractTmpl> findContractTemplates() {
        return contractTmplMapper.selectAll();
    }

    @Override
    public List<DownContract> findDownContracts(Integer bizType, Integer status, String contractName) {
        return downContractMapper.findDownContracts(bizType, status, contractName);
    }

    @Override
    public DownContract findByDownContractCode(String downContractCode) {
        return downContractMapper.selectByPrimaryKey(downContractCode);
    }

    @Override
    public void saveDownContract(DownContract downContract) {
        downContractMapper.insert(downContract);
    }

    @Override
    public void updateDownContract(DownContract downContract) {
        downContractMapper.updateByPrimaryKeySelective(downContract);
    }

    @Override
    public void deleteByContractCode(String contractCode) {
        downContractMapper.deleteByPrimaryKey(contractCode);
    }

    @Override
    public List<DownContract> findContractsBySupplier(String supplierCreditCode) {
        return downContractMapper.findContractsBySupplier(supplierCreditCode);
    }

	@Override
	public DownContract findDownContractByTenderResultCode(String tenderResultCode) {
		return downContractMapper.findDownContractByTenderResultCode(tenderResultCode, Constants.WF_STATUS_DRAFT, Constants.WF_STATUS_RETURN);
	}

    @Override
    public int updateConstructionTeam(String downContractCode, String constructionTeam) {
        DownContract downContract = downContractMapper.selectByPrimaryKey(downContractCode);
        if(downContract != null){
            downContract.setConstructionTeam(constructionTeam);
            return downContractMapper.updateByPrimaryKeySelective(downContract);
        }
        return 0;
    }

}
