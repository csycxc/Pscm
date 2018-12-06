package com.banry.pscm.web.utils;

import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.util.Constant;
import com.banry.pscm.service.util.ConstantService;
import com.banry.pscm.service.util.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractCodeUtil {

    @Autowired
    private ConstantService constantService;

    public final static String CONTRACT_CODE = "contract_code";

    public final static String CHANGE_CONTRACT_CODE = "change_contract_code";

    public final static String PREFIX = "CINC_PSCM_";

    public final static String CHANGE_PREFIX = "C_CINC_PSCM";

    private static Logger logger = LoggerFactory.getLogger(ContractCodeUtil.class);

    public String getContractCode(String parentTenantAccount, String contractCode, String prefix) throws UtilException {
        DynamicDataSourceContextHolder.set(parentTenantAccount);
        try {
            Constant constant = constantService.findConstantByPrimaryKey(contractCode);
            Integer intValue = Integer.valueOf(constant.getConstantValue());
            String format = String.format("%1$03d", intValue++);
            constant.setConstantValue(intValue.toString());
            constantService.updateConstant(constant);
            return prefix + format;
        } catch (UtilException e) {
            logger.error("获取合同编号异常", e);
            throw e;
        }
    }

}
