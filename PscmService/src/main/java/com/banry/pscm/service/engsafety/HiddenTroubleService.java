package com.banry.pscm.service.engsafety;

import java.util.List;
import java.util.Map;

/**
 * 隐患Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
public interface HiddenTroubleService {

	/**
	 * 根据Key查找隐患
	 * 
	 * @param key
	 * @return
	 * @throws EngsafetyException
	 */
	public HiddenTrouble findHiddenTroubleByPrimaryKey(String key, String parentTenantCode) throws EngsafetyException;
	
	/**
	 * 根据Key查找隐患 不带隐患清单，状态属性
	 * 
	 * @param key
	 * @return
	 * @throws EngsafetyException
	 */
	public HiddenTrouble selectByKeyWithoutBill(String key) throws EngsafetyException;
	
	/**
	 * 查找隐患
	 * 
	 * @param sqlWhere
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTrouble> findHiddenTroubleBySqlWhere(String sqlWhere, String parentTenantCode) throws EngsafetyException;

	/**
	 * 保存隐患
	 * 
	 * @param hiddenTrouble
	 * @return
	 * @throws EngsafetyException
	 */
	public void saveHiddenTrouble(HiddenTrouble hiddenTrouble) throws EngsafetyException;

	/**
	 * 根据指定key删除隐患
	 * 
	 * @param key
	 * @return
	 * @throws EngsafetyException
	 */
	public int deleteHiddenTrouble(String key) throws EngsafetyException;
	
	/**
	 * 上报隐患
	 * @author Xu Dingkui
	 * @date 2018年1月26日
	 * @param htt
	 */
	public void startProcessInstance(HiddenTrouble htt, String processDefinitionKey, String userName, String tenantCode, String parentTenantCode) throws EngsafetyException;
	
	/**
	 * 完成任务
	 * @author Xu Dingkui
	 * @date 2018年1月26日
	 * @param taskId
	 * @param variables
	 * @param userCode
	 * @param troubletreacode
	 * @param attachUrl
	 */
	public void complete(String taskId, Map<String, Object> variables, String userCode, String troubletreacode, String attachUrl) throws EngsafetyException;

}
