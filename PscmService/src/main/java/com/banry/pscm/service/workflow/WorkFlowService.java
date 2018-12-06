/**
 * 
 */
package com.banry.pscm.service.workflow;

import java.util.List;
import java.util.Map;

/**
 * @author Xu Dingkui
 * @date 2018年1月25日
 */
public interface WorkFlowService {

	/**
	 * 启动工作流
	 * @author Xu Dingkui
	 * @date 2018年1月25日
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 */
	public void startProcessInstanceByKey(String processDefinitionKey, String businessKey, Map<String, Object> variables);
	
	/**
	 * 完成任务
	 * @author Xu Dingkui
	 * @date 2018年1月26日
	 * @param taskId
	 * @param variables
	 * @param userCode
	 */
	public void complete(String taskId, Map<String, Object> variables, String userCode) throws WorkFlowException;
	
	/**
	 * 获取审批记录
	 * @author Xu Dingkui
	 * @date 2018年1月26日
	 * @param businessKey
	 * @return
	 */
	public List<ApprovalTrack> getHisUserTaskActivityInstanceList(String businessKey, String tenantCode, String parentTenantAccount);
	
	/** 
     * 审批通过(驳回直接跳回功能需后续扩展) 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param variables 
     *            流程存储参数 
     * @throws Exception 
     */  
    public void passProcess(String taskId, Map<String, Object> variables) throws Exception;
    
    /** 
     * 驳回流程 
     *  
     * @param taskId 
     *            当前任务ID 
     * @param activityId 
     *            驳回节点ID 
     * @param variables 
     *            流程存储参数 
     * @throws Exception 
     */  
    public void backProcess(String taskId, String activityId,  
             Map<String, Object> variables) throws Exception;
}
