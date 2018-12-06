package com.banry.pscm.workflow;


import java.util.HashMap;
import java.util.Map;

import com.banry.pscm.service.schedule.SubDivWorkBillService;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.springframework.stereotype.Component;

import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.contract.ContractChangeService;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.contract.DownContract;
import com.banry.pscm.service.contract.DownContractChangeWithBLOBs;
import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTrouble;
import com.banry.pscm.service.engsafety.HiddenTroubleService;
import com.banry.pscm.service.tender.BidEvaluationReport;
import com.banry.pscm.service.tender.BidEvaluationReportService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderPlanService;
import com.banry.pscm.service.tender.TenderPlanWithBLOBs;
import com.banry.pscm.service.tender.TenderResultChange;
import com.banry.pscm.service.tender.TenderResultChangeService;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.workflow.util.ProcessDefinitionKey;
import com.banry.pscm.workflow.util.SpringUtil;

/**
 * 任务监听器，实现TaskListener接口
 */
@Component
public class ProcessCompletedListener implements ActivitiEventListener {

    private HiddenTroubleService hiddenTroubleService;
    private TenderPlanService tenderPlanService;
    private BidEvaluationReportService reportService;
    private TenderResultChangeService changeService;
    private ContractService contractService;
    private ContractChangeService contractChangeService;
    private SubDivWorkBillService subDivWorkBillService;

    @Override
    public void onEvent(ActivitiEvent event) {
        try {
            ActivitiEntityEvent entityEvent = (ActivitiEntityEvent) event;
            Object entity = entityEvent.getEntity();
            if (entity instanceof ExecutionEntity) {
                ExecutionEntity exec = (ExecutionEntity) entity;
                //公司级业务库租户账号
                String parentTenantAccount = (String) exec.getVariable("parentTenantAccount");
                //是否不同意结束流程
                Boolean agree = (Boolean) exec.getVariable("agree");
                //获取“业务键”
                String businessKey = exec.getBusinessKey();
                ;//pi.getBusinessKey();
                String pdkAndId[] = businessKey.split("-");
                if (pdkAndId.length != 2) {
                    return;
                }
                String processDefinitionKey = pdkAndId[0];
                String key = pdkAndId[1];
                //隐患处理
                if (ProcessDefinitionKey.HIDDENTROUBLE.equals(processDefinitionKey)) {
                    hiddenTroubleService = (HiddenTroubleService) SpringUtil.getObject(HiddenTroubleService.class);
                    if (hiddenTroubleService != null) {
                        HiddenTrouble htt = hiddenTroubleService.selectByKeyWithoutBill(key);
                        if (htt != null) {
                            EnumVar s = new EnumVar(Constants.WF_STATUS_FINISH);
                            //更新状态
                            htt.setStatus(s);
                            hiddenTroubleService.saveHiddenTrouble(htt);
                        }
                    }
                } else if (ProcessDefinitionKey.TENDER_PLAN.equals(processDefinitionKey)) {
                    tenderPlanService = (TenderPlanService) SpringUtil.getObject(TenderPlanService.class);
                    Map<String, String> queryMap = new HashMap<String, String>();
                    queryMap.put("parentTenantAccount", parentTenantAccount);
                    queryMap.put("tenderPlanCode", key);
                    TenderPlanWithBLOBs tp = tenderPlanService.selectByPrimaryKey(queryMap);
                    if (tp != null) {
                        //更新状态
                        tp.setStatus(getEnumVar(agree));
                        tenderPlanService.updateByPrimaryKeySelective(tp);
                    }
                } else if (ProcessDefinitionKey.TENDER_RESULT.equals(processDefinitionKey)) {
                    reportService = (BidEvaluationReportService) SpringUtil.getObject(BidEvaluationReportService.class);
                    Map<String, String> queryMap = new HashMap<String, String>();
                    queryMap.put("parentTenantAccount", parentTenantAccount);
                    queryMap.put("bidResultCode", key);
                    BidEvaluationReport tp = reportService.selectByPrimaryKey(queryMap);
                    if (tp != null) {
                        //更新状态
                        EnumVar s = new EnumVar();
                        if (agree != null && !agree.booleanValue()) {
                            s.setEnumValue(Constants.WF_STATUS_RETURN);
                        } else {
                            s.setEnumValue(Constants.WF_STATUS_FINISH);
                            //将招标结果编号,中标单位，投标价格更新至工程划分清单
                            reportService.updateBidResultCodeBidSupplierPriceToSubDivWorkBill(key);
                        }
                        tp.setStatus(s);
                        reportService.updateByPrimaryKey(tp);
                    }
                } else if (ProcessDefinitionKey.CONTRACT_CHECK.equals(processDefinitionKey)) {
                    contractService = (ContractService) SpringUtil.getObject(ContractService.class);
                    DownContract contract = contractService.findByDownContractCode(key);
                    Integer status = agree ? Constants.WF_STATUS_FINISH : Constants.WF_STATUS_RETURN;
                    contract.setStatus(status);
                    contractService.updateDownContract(contract);
                    if (Constants.WF_STATUS_RETURN.equals(status)) {
                        //TODO 还原所有清单项
                        subDivWorkBillService = (SubDivWorkBillService) SpringUtil.getObject(SubDivWorkBillService.class);
                        subDivWorkBillService.clearContractCode(key);
                    }
                } else if (ProcessDefinitionKey.CONTRACT_CHECK_CHANGE.equals(processDefinitionKey)) {
                    contractChangeService = (ContractChangeService) SpringUtil.getObject(ContractChangeService.class);
                    DownContractChangeWithBLOBs changeContract = contractChangeService.getChangeContract(key);
                    changeContract.setStatus(agree ? Constants.WF_STATUS_FINISH : Constants.WF_STATUS_RETURN);
                    contractChangeService.updateChangeContract(changeContract);
                } else if (ProcessDefinitionKey.TENDER_RESULT_CHANGE.equals(processDefinitionKey)) {
                    changeService = (TenderResultChangeService) SpringUtil.getObject(TenderResultChangeService.class);
                    Map<String, String> queryMap = new HashMap<String, String>();
                    queryMap.put("parentTenantAccount", parentTenantAccount);
                    queryMap.put("tenderResultIdChangeCode", key);
                    TenderResultChange trc = changeService.selectByPrimaryKey(queryMap);
                    if (trc != null) {
                        //更新状态
                    	if (agree != null && !agree.booleanValue()) {
                    		trc.setStatus(new EnumVar(Constants.WF_STATUS_RETURN));
                        } else {
                        	trc.setStatus(new EnumVar(Constants.WF_STATUS_FINISH));
                        	//将变更信息更新至清单表中
                        	changeService.updateChangeToSubDivWorkBill(key);
                        }
                        changeService.updateByPrimaryKeySelective(trc);
                    }
                }
            }
        } catch (EngsafetyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TenderPlanException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private EnumVar getEnumVar(Boolean agree) {
        EnumVar s = new EnumVar();
        if (agree != null && !agree.booleanValue()) {
            s.setEnumValue(Constants.WF_STATUS_RETURN);
        } else {
            s.setEnumValue(Constants.WF_STATUS_FINISH);
        }
        return s;
    }

    /* (non-Javadoc)
     * @see org.activiti.engine.delegate.event.ActivitiEventListener#isFailOnException()
     */
    @Override
    public boolean isFailOnException() {
        // TODO Auto-generated method stub
        return false;
    }
}
