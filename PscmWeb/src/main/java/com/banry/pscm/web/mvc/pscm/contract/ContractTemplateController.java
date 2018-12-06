package com.banry.pscm.web.mvc.pscm.contract;

import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.contract.ContractTmpl;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.service.util.EnumVarService;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.web.mvc.model.DataTableModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/contract/templates")
public class ContractTemplateController {

    Logger logger = LoggerFactory.getLogger(ContractTemplateController.class);

    @Autowired
    private ContractService contractService;

    @Autowired
    private EnumVarService enumVarService;

    @Autowired
    private HttpSession session;

    @RequestMapping
    @ResponseBody
    public Object list() {
        DataTableModel dataTableModel = new DataTableModel();

        String parentTenantAccount = session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();

        List<ContractTmpl> contractTemplates = contractService.findContractTemplates();
        dataTableModel.setData(contractTemplates);
        Map<Integer, Object> map = new HashMap<>();
        try {
            DynamicDataSourceContextHolder.set(parentTenantAccount);
            List<EnumVar> bizType = enumVarService.findByEnumName("BizType");
            for (EnumVar enumVar : bizType) {
                map.put(enumVar.getEnumValue(), enumVar.getEnumValueName());
            }
            dataTableModel.setOptions(map);
        } catch (UtilException e) {
            logger.info("获取BizType失败", e);
        }
        return dataTableModel;
    }

}
