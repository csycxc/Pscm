package com.banry.pscm.contract;

import com.banry.pscm.persist.dao.SettleAccountMapper;
import com.banry.pscm.service.contract.SettleAccount;
import com.banry.pscm.service.contract.SettleAccountService;
import com.banry.pscm.service.contract.SettleAccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettleAccountServiceImpl implements SettleAccountService {

    @Autowired
    private SettleAccountMapper settleAccountMapper;

    @Override
    public List<SettleAccountVo> list(String contractName, Integer status) {
        return settleAccountMapper.list(contractName, status);
    }

    @Override
    public void saveSettleAccount(SettleAccount settleAccount) {
        settleAccountMapper.insertSelective(settleAccount);
    }

    @Override
    public void updateSettleAccount(SettleAccount settleAccount) {
        settleAccountMapper.updateByPrimaryKeySelective(settleAccount);
    }

    @Override
    public SettleAccountVo getSettleAccount(String settleAccountCode) {
        return settleAccountMapper.selectByPrimaryKey(settleAccountCode);
    }

    @Override
    public void deleteByCode(String settleAccountCode) {
        settleAccountMapper.deleteByPrimaryKey(settleAccountCode);
    }
}
