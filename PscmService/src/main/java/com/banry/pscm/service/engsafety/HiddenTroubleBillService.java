package com.banry.pscm.service.engsafety;

import java.util.List;

import com.banry.pscm.service.util.EnumVar;

/**
 * 隐患清单Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
public interface HiddenTroubleBillService {

	/**
	 * 依据划分编码查找隐患清单
	 * 
	 * @param divItemCode
	 * @param trobleFrom
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findHiddenTroubleBillByDivItemCode(String divItemCode, String trobleFrom, String parentTenantCode) throws EngsafetyException;

	/**
	 * 依据查询条件查找隐患清单
	 * 
	 * @param sqlWhere
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findHiddenTroubleBillBySqlWhere(String sqlWhere, String parentTenantCode) throws EngsafetyException;
	
	/**
	 * 已经最新一个月上报的频率排序获取隐患清单
	 * @author Xu Dingkui
	 * @date 2018年1月11日
	 * @param divItemCode
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findHiddenTroubleBillOrderByRate(String divItemCode, String parentTenantCode) throws EngsafetyException;
	
	
	/**
	 * 依据查询隐患清单类别和排查项目
	 * 
	 * @param sqlWhere
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findTroubleCateInvestItem(String sqlWhere, String parentTenantCode) throws EngsafetyException;
	
	/**
	 * 根据主键查找HiddenTroubleBill
	 * @param troubleCode
	 * @return
	 * @throws EngsafetyException
	 */
	public HiddenTroubleBill findHiddenTroubleBillByKey(String troubleCode, String parentTenantCode) throws EngsafetyException;
	/**
	 * 根据divitemcode查找隐患
	 * @param divItemCode
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findHiddenTroubleBillsByDivItemCode(String divItemCode, String parentTenantCode) throws EngsafetyException;
	/**
	 * 根据隐患类别查找隐患
	 * @param troubleCate
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findHiddenTroubleBillsByTroubleCate(String troubleCate, String parentTenantCode) throws EngsafetyException;
	/**
	 * 根据排查项目 模糊查询 隐患
	 * @param investItem
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findHiddenTroubleBillsByInvestItem(String investItem, String parentTenantCode) throws EngsafetyException;
	/**
	 * 根据排查内容  模糊查询 隐患
	 * @param investContent
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findHiddenTroubleBillsByInvestContent(String investContent, String parentTenantCode) throws EngsafetyException;
	/**
	 * 查找所有隐患
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findAllHiddenTroubleBill(String parentTenantCode) throws EngsafetyException;
	
	/**
	 * 保存隐患，无论属性是否为null,都保存     保存包括 更新和插入两种操作
	 * @param HiddenTroubleBill
	 * @throws EngsafetyException
	 */
	public void saveHiddenTroubleBill(HiddenTroubleBill hiddenTroubleBill) throws EngsafetyException;
	/**
	 * 保存隐患，属性为null,不保存     保存包括 更新和插入两种操作
	 * @param HiddenTroubleBill
	 * @throws EngsafetyException
	 */
	public void saveHiddenTroubleBillSelective(HiddenTroubleBill hiddenTroubleBill) throws EngsafetyException;
	/**
	 * 根据主键删除隐患
	 * @param troubleCode
	 * @return
	 * @throws EngsafetyException
	 */
	public int deleteHiddenTroubleBillByKey(String troubleCode) throws EngsafetyException;
	/**
	 * 更新隐患，无论属性是否为null，都更新
	 * @param HiddenTroubleBill
	 * @throws EngsafetyException
	 */
	public void updateHiddenTroubleBill(HiddenTroubleBill hiddenTroubleBill) throws EngsafetyException;
	/**
	 * 更新隐患，属性为null，不更新
	 * @param HiddenTroubleBill
	 * @throws EngsafetyException
	 */
	public void updateHiddenTroubleBillSelective(HiddenTroubleBill hiddenTroubleBill) throws EngsafetyException;
	
	/**
	 * 根据divsncode获取HiddenTroubleBill
	 * @param divItemCode
	 * @return
	 * @throws EngsafetyException
	 */
	public List<HiddenTroubleBill> findHiddenTroubleBillsByDivSnCode(String divItemCode, String parentTenantCode) throws  EngsafetyException;
	
	
	/**
	 * 根据divisionsncode 查找divItemCode,再根据DivItemCode删除隐患
	 * @param divisionsncode
	 */
	public int deleteHiddenTroubleBillByDivisionSnCode(String divisionsncode);

	/**
	 * 查询所有troublecate
	 * @param string 
	 * @return
	 */
	public List<EnumVar> findEnumVarAllTroubleCate(String parentTenantCode);

	/**
	 * 查询所有troublelevel
	 * @param parentTenantCode
	 * @return
	 */
	public List<EnumVar> findEnumVarAllTroubleLevel(String parentTenantCode);

	/**
	 * 根据enumValueName查询EnumVar
	 * @param enumValueName
	 * @return
	 */
	public EnumVar findEnumVarByEnumValueName(String enumValueName,String parentTenantCode);
	
}
