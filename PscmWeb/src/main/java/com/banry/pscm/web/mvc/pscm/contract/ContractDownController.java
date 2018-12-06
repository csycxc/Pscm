package com.banry.pscm.web.mvc.pscm.contract;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.contract.DownContract;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.service.tender.BidEvaluationReport;
import com.banry.pscm.service.tender.BidEvaluationReportService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.util.*;
import com.banry.pscm.service.workflow.WorkFlowService;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.utils.ContractCodeUtil;
import com.banry.pscm.web.utils.SystemConstants;
import com.banry.pscm.workflow.util.ProcessDefinitionKey;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.banry.pscm.web.utils.ContractCodeUtil.CONTRACT_CODE;
import static com.banry.pscm.web.utils.ContractCodeUtil.PREFIX;

@Controller
@RequestMapping("/contract/down")
public class ContractDownController {

    private Logger logger = LoggerFactory.getLogger(ContractDownController.class);

    @Autowired
    private ContractService contractService;

    @Autowired
    private EnumVarService enumVarService;

    @Autowired
    private BidEvaluationReportService bidEvaluationReportService;

    @Autowired
    private SubDivWorkBillService subDivWorkBillService;

    @Autowired
    private HttpSession session;

    @Autowired
    private ContractCodeUtil contractCodeUtil;

    @Autowired
    private SystemConstants constants;

    @Autowired
    private ContractAttService contractAttService;

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private TaskService taskService;

    @RequestMapping("/checkList")
    public ModelAndView checkList() {
        ModelAndView view = new ModelAndView("contract/checkList");


        try {
            String departmentName = session.getAttribute("PROJECT_DEPARTMENT_NAME").toString();
            view.addObject("departmentName", departmentName);
            String parentTenantAccount = session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
            List<BidEvaluationReport> reports = bidEvaluationReportService.findAllReport(parentTenantAccount);
            view.addObject("reports", reports);
            DynamicDataSourceContextHolder.set(parentTenantAccount);
            List<EnumVar> bizType = enumVarService.findByEnumName("BizType");
            view.addObject("bizType", bizType);
            List<EnumVar> contractStatus = enumVarService.findByEnumName("ContractStatus");
            view.addObject("contractStatus", contractStatus);
        } catch (UtilException e) {
            logger.info("获取BizType出错", e);
        } catch (TenderPlanException e) {
            logger.info("查询招标结果异常", e);
        }

        return view;
    }

    @RequestMapping("/dataList")
    @ResponseBody
    public DataTableModel downContractList(Integer bizType, Integer status, String contractName) {

        String tenantCode = session.getAttribute("CURRENT_TENANT_CODE").toString();
        String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();

        DataTableModel dataTableModel = new DataTableModel();
        List<DownContract> downContracts = contractService.findDownContracts(bizType, status, contractName);
        for (DownContract contract : downContracts) {
            if (contract.getStatus() > Constants.WF_STATUS_DRAFT && contract.getStatus() < Constants.WF_STATUS_FINISH) {
                initTaskId(contract, userCode);
                initTaskId(contract, userCode + '-' + tenantCode);
            }
        }
        dataTableModel.setData(downContracts);
        return dataTableModel;
    }

    private void initTaskId(DownContract contract, String userPlusTenant) {
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(
                ProcessDefinitionKey.CONTRACT_CHECK + '-' + contract.getDownContractCode()).taskAssignee(userPlusTenant).singleResult();
        if (task == null) {
            task = taskService.createTaskQuery().processInstanceBusinessKey(
                    ProcessDefinitionKey.CONTRACT_CHECK + '-' + contract.getDownContractCode()).taskCandidateUser(userPlusTenant).singleResult();
        }
        if (task != null) {
            contract.setTaskId(task.getId());
        }
    }

    @ResponseBody
    @RequestMapping("/getDownContract")
    public Object getDownContract(String downContractCode) {
        return contractService.findByDownContractCode(downContractCode);
    }

    @ResponseBody
    @RequestMapping(value = "/saveDownContract", method = RequestMethod.POST)
    public Object saveDownContract(@ModelAttribute DownContract downContract) {
        JSONObject json = new JSONObject();
        if (StringUtils.isEmpty(downContract.getDownContractCode())) {
            try {
                List<DownContract> contractList = contractService.findContractsBySupplier(downContract.getContractPartSecond().getSupplierCreditCode());
                if (CollectionUtils.isEmpty(contractList)) {
                    String parentTenantAccount = session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
                    String contractCode = contractCodeUtil.getContractCode(parentTenantAccount, CONTRACT_CODE, PREFIX);
                    downContract.setDownContractCode(contractCode);
                    downContract.setStatus(1);
                    String tenantCode = session.getAttribute("CURRENT_TENANT_ACCOUNT").toString();
                    DynamicDataSourceContextHolder.set(tenantCode);
                    subDivWorkBillService.addSubDivWorkBillToContract(downContract);
                    contractService.saveDownContract(downContract);
                    json.put("msg", "success");
                } else {
                    json.put("msg", "failed");
                    json.put("errorMsg", "项目部与供方之前已经签订过合同");
                }
            } catch (UtilException e) {
                logger.info(e.getMessage(), e);
                json.put("msg", "failed");
                json.put("errorMsg", e.getMessage());
            }
        } else {
            contractService.updateDownContract(downContract);
        }
        return json;
    }

    @ResponseBody
    @RequestMapping(value = "/deleteDownContract", method = RequestMethod.POST)
    public Object deleteDownContract(String contractCode) {
        contractService.deleteByContractCode(contractCode);
        return JSON.parse("{msg:'success'}");
    }

    @ResponseBody
    @RequestMapping(value = "/submitDownContract", method = RequestMethod.POST)
    public Object submitDownContract(String contractCode) {
        String userName = ((CustomUserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal()).getUserName();
        Map<String, Object> variables = new HashMap<>();
        String parentTenantAccount = session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
        String tenantCode = session.getAttribute("CURRENT_TENANT_CODE").toString();
        variables.put("parentTenantAccount", parentTenantAccount);
        variables.put("tenantCode", tenantCode);
        variables.put("reportUser", userName);
        List<String> assigneeList = new ArrayList<>();
        assigneeList.add("calculate");
        assigneeList.add("engineer");
        variables.put("userList", assigneeList);
        workFlowService.startProcessInstanceByKey(ProcessDefinitionKey.CONTRACT_CHECK, ProcessDefinitionKey.CONTRACT_CHECK + '-' + contractCode, variables);

        return JSON.parse("{msg:'success'}");
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(String contractCode, MultipartFile file) {
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
            DownContract downContract = contractService.findByDownContractCode(contractCode);
            String contractAttach = downContract.getContractAttach();
            if (StringUtils.isEmpty(contractAttach)) {
                downContract.setContractAttach(fileInName);
            } else {
                contractAttach += "," + fileInName;
                downContract.setContractAttach(contractAttach);
            }
            contractService.updateDownContract(downContract);
        } catch (IOException e) {
            logger.error("上传文件异常:" + path, e);
            result = "failed";
        } catch (UtilException e) {
            logger.error("保存附件信息异常", e);
            result = "failed";
        }

        return result;
    }

    @ResponseBody
    @RequestMapping(value = "/addSubDivWorkBill", method = RequestMethod.POST)
    public Object addSubDivWorkBill(String divisionSnCode, String downContractCode) {
        JSONObject json = new JSONObject();
        /**
         * 1.获取清单项，检查清单项是否无主
         * 2.检查清单项是否已经移入其他合同或者招标结果
         * 3.将清单项加入合同
         */
        try {
            SubDivWorkBill workBill = subDivWorkBillService.findSubDivWorkBillByKey(divisionSnCode);
            if (workBill.getTenderPlanCode() != null && workBill.getTenderResultCode() != null
                    && workBill.getSupplier() == null) {

                if (subDivWorkBillService.checkSubDivWorkBill(divisionSnCode)) {
                    DownContract downContract = contractService.findByDownContractCode(downContractCode);
                    workBill.setContractCode(downContractCode);
                    workBill.setSupplier(downContract.getContractPartSecond());
                    subDivWorkBillService.updateSubDivWorkBill(workBill);
                } else {
                    json.put("msg", "failed");
                    json.put("errorMsg", "清单项已经移入其他招标结果或合同");
                }
            } else {
                json.put("msg", "failed");
                json.put("errorMsg", "清单项没有经过招投标!");
            }
        } catch (ScheduleException e) {
            logger.error(e.getMessage(), e);
            json.put("msg", "failed");
            json.put("errorMsg", e.getMessage());
        }
        return json;
    }

}
