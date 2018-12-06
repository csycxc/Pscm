package com.banry.pscm.web.mvc.pscm.contract;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.contract.*;
import com.banry.pscm.service.util.*;
import com.banry.pscm.service.workflow.WorkFlowService;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.utils.SystemConstants;
import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/settleAccount")
public class SettleAccountController {

    private static final Logger logger = LoggerFactory.getLogger(SettleAccountController.class);

    @Autowired
    private SettleAccountService settleAccountService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private EnumVarService enumVarService;

    @Autowired
    private HttpSession session;

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ContractAttService contractAttService;

    @Autowired
    private SystemConstants constants;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView view = new ModelAndView("contract/settle_account");
        try {
            String parentTenantAccount = session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();

            List<DownContract> downContracts = contractService.findDownContracts(null, Constants.WF_STATUS_FINISH, null);
            view.addObject("contracts", downContracts);

            DynamicDataSourceContextHolder.set(parentTenantAccount);
            List<EnumVar> contractChangeStatus = enumVarService.findByEnumName("ContractChangeStatus");
            view.addObject("contractChangeStatus", contractChangeStatus);

        } catch (UtilException e) {
            logger.error("获取ContractChangeStatus出错", e);
        }
        return view;
    }

    @ResponseBody
    @RequestMapping(value = "/dataList", method = RequestMethod.GET)
    public DataTableModel dataList(String contractName, Integer status) {
        DataTableModel dataTableModel = new DataTableModel();
        List<SettleAccountVo> list = settleAccountService.list(contractName, status);
        dataTableModel.setData(list);
        return dataTableModel;
    }

    @ResponseBody
    @RequestMapping(value = "/saveSettleAccount", method = RequestMethod.POST)
    public Object saveSettleAccount(SettleAccountVo settleAccountVo) {
        if (StringUtils.isEmpty(settleAccountVo.getSettleCode())) {
            settleAccountVo.setSettleCode(String.valueOf(System.currentTimeMillis()));
            settleAccountVo.setSettleNumber(1);
            settleAccountVo.setStatus(1);
            settleAccountService.saveSettleAccount(settleAccountVo);
        } else {
            settleAccountService.updateSettleAccount(settleAccountVo);
        }
        return JSON.parse("{msg : 'success'}");
    }

    @ResponseBody
    @RequestMapping(value = "/getSettleAccount", method = RequestMethod.GET)
    public Object getSettleAccount(String settleAccountCode) {
        return settleAccountService.getSettleAccount(settleAccountCode);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteSettleAccount", method = RequestMethod.POST)
    public Object deleteSettleAccount(String settleAccountCode) {
        settleAccountService.deleteByCode(settleAccountCode);
        return JSON.parse("{msg : 'success'}");
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(String settleAccountCode, MultipartFile file) {
        String result = "success";

        String actualFileName = file.getOriginalFilename();
        String type = actualFileName.substring(actualFileName.lastIndexOf('.'));
        String fileInName = String.valueOf(System.currentTimeMillis());
        String path = constants.getUploadDirReal() + constants.getAttach() + fileInName + type;
        try {
            File dest = new File(path);
            file.transferTo(dest);
            ContractAtt contractAtt = new ContractAtt();
            contractAtt.setFileInName(fileInName);
            contractAtt.setActualFileName(actualFileName);
            contractAtt.setLocation(constants.getAttach() + fileInName + type);
            contractAtt.setType(type);
            contractAttService.saveContractAtt(contractAtt);

            SettleAccount settleAccount = settleAccountService.getSettleAccount(settleAccountCode);
            String contractAttach = settleAccount.getSettleAttach();
            if (StringUtils.isEmpty(contractAttach)) {
                contractAttach = fileInName;
            } else {
                contractAttach += "," + fileInName;
            }
            settleAccount.setSettleAttach(contractAttach);
            settleAccountService.updateSettleAccount(settleAccount);
        } catch (IOException e) {
            logger.error("上传文件异常:" + path, e);
            result = "failed";
        } catch (UtilException e) {
            logger.error("保存附件信息异常", e);
            result = "failed";
        }

        return result;
    }
}
