package com.banry.pscm.service.contract;

import java.util.List;

public interface SettleAccountService {

    List<SettleAccountVo> list(String contractName, Integer status);

    void saveSettleAccount(SettleAccount settleAccount);

    void updateSettleAccount(SettleAccount settleAccount);

    SettleAccountVo getSettleAccount(String settleAccountCode);

    void deleteByCode(String settleAccountCode);
}
