package com.banry.pscm.workflow;


import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.banry.pscm.service.contract.ContractChangeService;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.contract.DownContract;
import com.banry.pscm.service.contract.DownContractChangeWithBLOBs;
import org.activiti.engine.delegate.event.ActivitiEntityEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysRolesService;
import com.banry.pscm.service.account.SysUsers;
import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTrouble;
import com.banry.pscm.service.engsafety.HiddenTroubleBill;
import com.banry.pscm.service.engsafety.HiddenTroubleService;
import com.banry.pscm.service.tender.BidEvaluationReport;
import com.banry.pscm.service.tender.BidEvaluationReportService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderPlanService;
import com.banry.pscm.service.tender.TenderPlanWithBLOBs;
import com.banry.pscm.service.tender.TenderResultChange;
import com.banry.pscm.service.tender.TenderResultChangeService;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.workflow.util.MessagePush;
import com.banry.pscm.workflow.util.ProcessDefinitionKey;
import com.banry.pscm.workflow.util.SpringUtil;

/**
 * 
 * 任务监听器，实现ActivitiEventListener接口
 * 
 * */
@Component
public class TaskCreateListener implements ActivitiEventListener {
	@Autowired
	private SysRolesService roleService;
	private HiddenTroubleService hiddenTroubleService;
	private TenderPlanService tenderPlanService;
	private BidEvaluationReportService reportService;
	private TenderResultChangeService changeService;
	private ContractService contractService;
	private ContractChangeService contractChangeService;

	private static Logger logger = LoggerFactory.getLogger(TaskCreateListener.class);
	
    @Override
    public void onEvent(ActivitiEvent event){
    	try {
    		ActivitiEntityEvent entityEvent = (ActivitiEntityEvent)event;
	        Object entity = entityEvent.getEntity();
	        //推送别名
			Set<String> aliasSet=new HashSet<String>();
	        if(entity instanceof TaskEntity) {
	            TaskEntity task = (TaskEntity) entity;
	            //公司级业务库租户账号
		        String parentTenantAccount = (String)task.getVariable("parentTenantAccount");
		        //当前用户租户代码
		        String tenantCode = (String)task.getVariable("tenantCode");
		        //获取“业务键”
//		        String businessKey = task.getExecution().getProcessInstanceBusinessKey();//pi.getBusinessKey();
		        String businessKey = task.getExecution().getProcessBusinessKey();
		        String pdkAndId[] = businessKey.split("-");
		        if (pdkAndId.length != 2) {
		        	return;
		        }
		        String processDefinitionKey = pdkAndId[0];
		        String key = pdkAndId[1];
		        //隐患处理
		        if (ProcessDefinitionKey.HIDDENTROUBLE.equals(processDefinitionKey)) {
		        	hiddenTroubleService = (HiddenTroubleService) SpringUtil.getObject(HiddenTroubleService.class);
		        	HiddenTrouble htt = hiddenTroubleService.findHiddenTroubleByPrimaryKey(key, parentTenantAccount);
			        if (htt != null) {
			        	//更新状态
			        	String activityId = task.getExecution().getActivityId().replaceAll("task", "");
			        	if (isInteger(activityId)) {
				        	EnumVar s = new EnumVar();
				        	s.setEnumValue(Integer.parseInt(activityId));
				        	htt.setStatus(s);
				        	hiddenTroubleService.saveHiddenTrouble(htt);
			        	}
			        	HiddenTroubleBill ht = htt.getTroubleBillItemCode();
				        Map<String, String> extras = new HashMap<String, String>();
						extras.put("divisionSnCode", htt.getDivisionSnCode());
						extras.put("troubleId", htt.getTroubleCode());
						//隐患类别
		            	if (ht.getTroubleCate() != null) {
		            		extras.put("troubleCate", ht.getTroubleCate().getEnumValueName());
		            	} else {
		            		extras.put("troubleCate", "");
		            	}
		            	//隐患级别
		            	if (ht.getTroubleLevel() != null) {
		            		extras.put("troubleLevel", ht.getTroubleLevel().getEnumValueName());
		            	} else {
		            		extras.put("troubleLevel", "");
		            	}
		            	//排查项目
		            	extras.put("investItem", ht.getInvestItem());
		            	//排查内容
		            	extras.put("investContent", ht.getInvestContent());
		            	//隐患说明
		            	extras.put("description", htt.getDescription());
		            	//整改期限
		            	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		            	extras.put("rectifyTimeLimit", sf.format(htt.getRectifyTimeLimit()));
		            	//整改延期
		            	extras.put("rectifyPostpone", String.valueOf(htt.getRectifyPostpone()));
		            	//安全责任人
		            	if (htt.getSafetyChargeUser() != null) {
		            		extras.put("personLiable", htt.getSafetyChargeUser());
		            	} else {
		            		extras.put("personLiable", "");
		            	}
		            	//状态
		            	extras.put("status", String.valueOf(htt.getStatus()));
		            	extras.put("taskId", task.getId());
		            	if (htt.getRectifySteps() != null) {
		            		extras.put("rectifySteps", htt.getRectifySteps());	
		            	} else {
		            		extras.put("rectifySteps", "");
		            	}
		            	String assignee = task.getAssignee();
			            if (assignee != null && !"".equals(assignee)) {
			            	aliasSet.add(assignee);
			            } else {
			            	for (IdentityLink il : task.getIdentityLinks()) {
			                	if (IdentityLinkType.CANDIDATE.equals(il.getType())) {
									List<SysUsers> userLst = roleService.findUsersByRoleWithParentTenantAccount(il.getGroupId(), tenantCode, parentTenantAccount);
									for (SysUsers u : userLst) {
				            			aliasSet.add(u.getUserCode());
				            		}
			                	}
			            	}
			            }
		            	if (!aliasSet.isEmpty()) {
			    			logger.info("隐患处理推送:"+htt.getTroubleCode()+"-"+ht.getInvestItem());
				            MessagePush push= new MessagePush("有您需要审批的内容"/*ht.getInvestitem()+"-"+hai.getActivityName()*/,"隐患处理", extras, aliasSet);
				            push.start();
	//					            long msgId = push.sendPushAlias(aliasSet);
			    		}
			        }
	        	} else if (ProcessDefinitionKey.TENDER_PLAN.equals(processDefinitionKey)) {
	        		//更新状态
		        	String activityId = task.getExecution().getActivityId().replaceAll("task", "");
		        	if (isInteger(activityId)) {
		        		tenderPlanService = (TenderPlanService) SpringUtil.getObject(TenderPlanService.class);
		        		Map<String, String> queryMap = new HashMap<String, String>();
		    			queryMap.put("parentTenantAccount", parentTenantAccount);
		    			queryMap.put("tenderPlanCode", key);
			        	TenderPlanWithBLOBs tp = tenderPlanService.selectByPrimaryKey(queryMap);
				        if (tp != null) {
				        	EnumVar s = new EnumVar();
				        	s.setEnumValue(Integer.parseInt(activityId));
				        	tp.setStatus(s);
				        	tenderPlanService.updateByPrimaryKeySelective(tp);
			        	}
			        }
	        	} else if (ProcessDefinitionKey.TENDER_RESULT.equals(processDefinitionKey)) {
	        		//更新状态
		        	String activityId = task.getExecution().getActivityId().replaceAll("task", "");
		        	if (isInteger(activityId)) {
		        		reportService = (BidEvaluationReportService) SpringUtil.getObject(BidEvaluationReportService.class);
		        		Map<String, String> queryMap = new HashMap<String, String>();
		    			queryMap.put("parentTenantAccount", parentTenantAccount);
		    			queryMap.put("bidResultCode", key);
		    			BidEvaluationReport tp = reportService.selectByPrimaryKey(queryMap);
				        if (tp != null) {
				        	EnumVar s = new EnumVar();
				        	s.setEnumValue(Integer.parseInt(activityId));
				        	tp.setStatus(s);
				        	reportService.updateByPrimaryKey(tp);
			        	}
			        }
	        	} else if (ProcessDefinitionKey.TENDER_RESULT_CHANGE.equals(processDefinitionKey)) {
	        		//更新状态
		        	String activityId = task.getExecution().getActivityId().replaceAll("task", "");
		        	if (isInteger(activityId)) {
		        		changeService = (TenderResultChangeService) SpringUtil.getObject(TenderResultChangeService.class);
		        		Map<String, String> queryMap = new HashMap<String, String>();
		    			queryMap.put("parentTenantAccount", parentTenantAccount);
		    			queryMap.put("tenderResultIdChangeCode", key);
		    			TenderResultChange tp = changeService.selectByPrimaryKey(queryMap);
				        if (tp != null) {
				        	EnumVar s = new EnumVar();
				        	s.setEnumValue(Integer.parseInt(activityId));
				        	tp.setStatus(s);
				        	changeService.updateByPrimaryKeySelective(tp);
				        }
		        	}
				} else if (ProcessDefinitionKey.CONTRACT_CHECK.equals(processDefinitionKey)) {
					contractService = (ContractService) SpringUtil.getObject(ContractService.class);
					DownContract contract = contractService.findByDownContractCode(key);
					if (contract != null) {
						String status = task.getExecution().getActivityId().replaceAll("task", "");
						if (isInteger(status)) {
							contract.setStatus(Integer.parseInt(status));
							contractService.updateDownContract(contract);
						}
					}
				} else if (ProcessDefinitionKey.CONTRACT_CHECK_CHANGE.equals(processDefinitionKey)) {
					contractChangeService = (ContractChangeService) SpringUtil.getObject(ContractChangeService.class);
					DownContractChangeWithBLOBs changeContract = contractChangeService.getChangeContract(key);
					if (changeContract != null) {
						String status = task.getExecution().getActivityId().replaceAll("task", "");
						if (isInteger(status)) {
							changeContract.setStatus(Integer.parseInt(status));
							contractChangeService.updateChangeContract(changeContract);
						}
					}
				}
            }
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TenderPlanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	/* (non-Javadoc)
	 * @see org.activiti.engine.delegate.event.ActivitiEventListener#isFailOnException()
	 */
	@Override
	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	/*方法二：推荐，速度最快
	  * 判断是否为整数 
	  * @param str 传入的字符串 
	  * @return 是整数返回true,否则返回false 
	*/

	public boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}
}
