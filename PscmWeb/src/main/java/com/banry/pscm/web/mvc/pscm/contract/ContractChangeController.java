package com.banry.pscm.web.mvc.pscm.contract;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.contract.ContractChangeService;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.contract.DownContract;
import com.banry.pscm.service.contract.DownContractChangeVo;
import com.banry.pscm.service.enums.ChangeTypeEnum;
import com.banry.pscm.service.schedule.*;
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

import static com.banry.pscm.web.utils.ContractCodeUtil.CHANGE_CONTRACT_CODE;
import static com.banry.pscm.web.utils.ContractCodeUtil.CHANGE_PREFIX;

@Controller
@RequestMapping("/contract/change")
public class ContractChangeController {

    private static Logger logger = LoggerFactory.getLogger(ContractChangeController.class);

    @Autowired
    private ContractChangeService contractChangeService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractCodeUtil contractCodeUtil;

    @Autowired
    private EnumVarService enumVarService;

    @Autowired
    private HttpSession session;

    @Autowired
    private WorkFlowService workFlowService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private SystemConstants constants;

    @Autowired
    private ContractAttService contractAttService;

    @Autowired
    private SubDivWorkChangeBillService subDivWorkChangeBillService;

    @Autowired
    private SubDivWorkBillService workBillService;

    @RequestMapping("/list")
    public ModelAndView list() {
        ModelAndView view = new ModelAndView("contract/checkChangeList");
        try {
            List<DownContract> downContracts = contractService.findDownContracts(null, Constants.WF_STATUS_FINISH, null);
            view.addObject("contracts", downContracts);

            String parentTenantAccount = session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();

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
    public DataTableModel dataList(String originalContractName, Integer status) {
        String tenantCode = session.getAttribute("CURRENT_TENANT_CODE").toString();
        String userCode = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();

        DataTableModel dataTableModel = new DataTableModel();
        List<DownContractChangeVo> changes = contractChangeService.findContractChanges(originalContractName, status);
        for (DownContractChangeVo change : changes) {
            if (change.getStatus() > Constants.WF_STATUS_DRAFT && change.getStatus() < Constants.WF_STATUS_FINISH) {
                initTaskId(change, userCode);
                initTaskId(change, userCode + '-' + tenantCode);
            }
        }
        dataTableModel.setData(changes);
        return dataTableModel;
    }

    private void initTaskId(DownContractChangeVo contract, String userPlusTenant) {
        String businessKey = ProcessDefinitionKey.CONTRACT_CHECK_CHANGE + '-' + contract.getChangeCode();
        Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskAssignee(userPlusTenant).singleResult();
        if (task == null) {
            task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).taskCandidateUser(userPlusTenant).singleResult();
        }
        if (task != null) {
            contract.setTaskId(task.getId());
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getChangeContract")
    public Object getChangeContract(String changeCode) {
        return contractChangeService.getChangeContract(changeCode);
    }

    @ResponseBody
    @RequestMapping(value = "/saveChangeContract", method = RequestMethod.POST)
    public Object saveChangeContract(@ModelAttribute DownContractChangeVo changeContract) {
        if (StringUtils.isEmpty(changeContract.getChangeCode())) {
            String parentTenantAccount = session.getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
            /*
                 1.生成一个变更合同的合同编号
                 2.设置变更合同的状态为‘起草’
                 3.保存变更合同

             */
            try {
                String changeContractCode = contractCodeUtil.getContractCode(parentTenantAccount, CHANGE_CONTRACT_CODE, CHANGE_PREFIX);
                changeContract.setChangeCode(changeContractCode);
                changeContract.setStatus(1);
                String tenantCode = session.getAttribute("CURRENT_TENANT_ACCOUNT").toString();
                DynamicDataSourceContextHolder.set(tenantCode);
                contractChangeService.saveChangeContract(changeContract);
            } catch (UtilException e) {
                logger.error(e.getMessage(), e);
                return JSON.parse("{msg: 'failed'}");
            }
        } else {
            contractChangeService.updateChangeContract(changeContract);
        }

        return JSON.parse("{msg:'success'}");
    }

    @ResponseBody
    @RequestMapping(value = "/submitChangeContract", method = RequestMethod.POST)
    public Object submitChangeContract(String changeCode) {
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
        String businessKey = ProcessDefinitionKey.CONTRACT_CHECK_CHANGE + '-' + changeCode;
        workFlowService.startProcessInstanceByKey(ProcessDefinitionKey.CONTRACT_CHECK_CHANGE, businessKey, variables);

        return JSON.parse("{msg : 'success'}");
    }

    @ResponseBody
    @RequestMapping(value = "/deleteChangeContract", method = RequestMethod.POST)
    public Object deleteChangeContract(String changeCode) {
        contractChangeService.deleteChangeContract(changeCode);
        return JSON.parse("{msg : 'success'}");
    }

    @ResponseBody
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public Object upload(String changeCode, MultipartFile file) {
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
            DownContractChangeVo changeContract = contractChangeService.getChangeContract(changeCode);
            String contractAttach = changeContract.getChangeAttach();
            if (StringUtils.isEmpty(contractAttach)) {
                contractAttach = fileInName;
            } else {
                contractAttach += "," + fileInName;
            }
            changeContract.setChangeAttach(contractAttach);
            contractChangeService.updateChangeContract(changeContract);
        } catch (IOException e) {
            logger.error("上传文件异常:" + path, e);
            result = "failed";
        } catch (UtilException e) {
            logger.error("保存附件信息异常", e);
            result = "failed";
        }

        return result;
    }

    /**
     * 移入清单项
     * @param divisionSnCode 清单项编码
     * @param changeCode 变更合同编码
     * @param changedContractCode 移入合同编码
     * @return success if success
     */
    @RequestMapping(value = "/moveIn", method = RequestMethod.POST)
    @ResponseBody
    public Object moveIn(String divisionSnCode, String changeCode, String changedContractCode) {
        JSONObject json = new JSONObject();

        /**
         * 判断是否能够移入
         * 清单项能移入的情况只有一种：有招标结果code，但没有中标单位，也不在任何招标结果变更或者合同变更的审批流程中
         */
        try {
            SubDivWorkBill subDivWorkBill = workBillService.findSubDivWorkBillByKey(divisionSnCode);
            if (subDivWorkBill.getTenderPlanCode() != null && subDivWorkBill.getTenderResultCode() != null
                    && subDivWorkBill.getSupplier() == null) {//确定清单项是无主清单项
                /**
                 * 检查无主清单项是否已经移入某个尚未通过审签的招标结果变更或者合同中
                 */
                if (workBillService.checkSubDivWorkBill(divisionSnCode)) {
                    SubDivWorkChangeBill subDivWorkChangeBill = new SubDivWorkChangeBill();
                    subDivWorkChangeBill.setDivisionSnCode(divisionSnCode);
                    subDivWorkChangeBill.setChangeCode(changeCode);
                    subDivWorkChangeBill.setChangeType(ChangeTypeEnum.MOVE_IN);
                    subDivWorkChangeBill.setChangedContractCode(changedContractCode);
                    subDivWorkChangeBillService.saveSubDivWorkChangeBill(subDivWorkChangeBill);
                    json.put("msg", "success");
                } else {
                    json.put("msg", "failed");
                    json.put("errorMsg", "清单项已被移入其他招标结果或合同");
                }
            } else {
                json.put("msg", "failed");
                json.put("errorMsg", "不能移入没有经过招投标的清单项!");
            }
        } catch (ScheduleException e) {
            logger.error(e.getMessage(), e);
            json.put("msg", "failed");
            json.put("errorMsg", "查询清单项异常");
        }


        return JSON.parse("{msg : 'success'}");
    }

    /**
     * 移出清单项
     * @param divisionSnCode 清单项编码
     * @param changeCode 变更合同编码
     * @return success if success
     */
    @RequestMapping(value = "/moveOut", method = RequestMethod.POST)
    @ResponseBody
    public Object moveOut(String divisionSnCode, String changeCode) {
        SubDivWorkChangeBill subDivWorkChangeBill = new SubDivWorkChangeBill();
        subDivWorkChangeBill.setDivisionSnCode(divisionSnCode);
        subDivWorkChangeBill.setChangeCode(changeCode);
        subDivWorkChangeBill.setChangeType(ChangeTypeEnum.MOVE_OUT);
        subDivWorkChangeBillService.saveSubDivWorkChangeBill(subDivWorkChangeBill);
        return JSON.parse("{msg:'success'}");
    }

    /**
     * 取消清单项变更
     * @param divisionSnCode 清单项code
     * @param changeCode 变更合同code
     * @return success if success
     */
    @RequestMapping(value = "/cancelMove", method = RequestMethod.POST)
    @ResponseBody
    public Object cancelMove(String divisionSnCode, String changeCode) {

        subDivWorkChangeBillService.deleteSubDivWorkChangeBill(divisionSnCode, changeCode);
        return JSON.parse("{msg : 'success'}");
    }

    /**
     * 变更清单项
     * @param subDivWorkChangeBill 清单项变更信息
     * @return success if success
     */
    @RequestMapping(value = "/changeWorkBill", method = RequestMethod.POST)
    @ResponseBody
    public Object changeWorkBill(SubDivWorkChangeBill subDivWorkChangeBill) {
        try {
            SubDivWorkBill workBill = workBillService.findSubDivWorkBillByKey(subDivWorkChangeBill.getDivisionSnCode());
            if (subDivWorkChangeBill.getEngNumChange() != null) {
                subDivWorkChangeBill.setEngNumBefore(workBill.getRawConMapQuan());
                subDivWorkChangeBill.setEngNumAfter(workBill.getRawConMapQuan() + subDivWorkChangeBill.getEngNumChange());
                subDivWorkChangeBill.setChangeType(ChangeTypeEnum.ENG_NUM_CHANGE);
            }
            if (subDivWorkChangeBill.getUnitPriceChange() != null) {
                subDivWorkChangeBill.setUnitPriceBefore(workBill.getContractUnitPrice());
                subDivWorkChangeBill.setUnitPriceAfter(workBill.getContractUnitPrice() + subDivWorkChangeBill.getUnitPriceChange());
                if (subDivWorkChangeBill.getChangeType() != null) {
                    subDivWorkChangeBill.setChangeType(ChangeTypeEnum.ENG_NUM_AND_UNIT_PRICE_CHANGE);
                } else {
                    subDivWorkChangeBill.setChangeType(ChangeTypeEnum.UNIT_PRICE_CHANGE);
                }
            }
            subDivWorkChangeBillService.saveSubDivWorkChangeBill(subDivWorkChangeBill);
        } catch (ScheduleException e) {
            logger.error(e.getMessage(), e);
        }

        return JSON.parse("{msg : 'success'}");
    }

    /**
     * 更新变更信息
     * @param subDivWorkChangeBill
     * @return
     */
    @RequestMapping(value = "/updateEngUnit", method = RequestMethod.POST)
    @ResponseBody
    public Object updateEngUnit(SubDivWorkChangeBill subDivWorkChangeBill) {
        try {
            SubDivWorkChangeBill originalChangeBill = subDivWorkChangeBillService.findBillChangeByKey(subDivWorkChangeBill);
            originalChangeBill.setChangeType(null);
            if (subDivWorkChangeBill.getEngNumChange() != null) {
                originalChangeBill.setEngNumChange(subDivWorkChangeBill.getEngNumChange());
                originalChangeBill.setEngNumAfter(originalChangeBill.getEngNumBefore() + subDivWorkChangeBill.getEngNumChange());
                originalChangeBill.setChangeType(ChangeTypeEnum.ENG_NUM_CHANGE);
            }
            if (subDivWorkChangeBill.getUnitPriceChange() != null) {
                originalChangeBill.setUnitPriceChange(subDivWorkChangeBill.getUnitPriceChange());
                originalChangeBill.setUnitPriceAfter(originalChangeBill.getUnitPriceBefore() + subDivWorkChangeBill.getUnitPriceChange());
                if (originalChangeBill.getChangeType() != null) {
                    originalChangeBill.setChangeType(ChangeTypeEnum.ENG_NUM_AND_UNIT_PRICE_CHANGE);
                } else {
                    originalChangeBill.setChangeType(ChangeTypeEnum.UNIT_PRICE_CHANGE);
                }
            }
            subDivWorkChangeBillService.updateSubDivWorkChangeBill(originalChangeBill);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return JSON.parse("{msg : 'success'}");
    }

}
