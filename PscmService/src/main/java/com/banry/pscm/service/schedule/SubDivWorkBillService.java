package com.banry.pscm.service.schedule;

import com.banry.pscm.service.contract.DownContract;

import java.util.List;

/**
 * 分项工程清单Service
 * 
 * @author Xu Dingkui
 * @date 2018年7月4日
 */
public interface SubDivWorkBillService {

	/**
	 * 根据主键查找SubDivWorkBill（分项工程清单信息）
	 * @param divisionSnCode
	 * @return
	 * @throws ScheduleException
	 */
	public SubDivWorkBill findSubDivWorkBillByKey(String divisionSnCode) throws ScheduleException;
	/**
	 * 根据项目名称 模糊查询 分项工程清单信息
	 * @param name
	 * @return
	 * @throws ScheduleException
	 */
	public List<SubDivWorkBill> findSubDivWorkBillsByName(String name) throws ScheduleException;
	/**
	 * 查询一个分项工程下的所有工序的清单信息。
	 * @param parentCode
	 * @return
	 * @throws ScheduleException
	 */
	public List<SubDivWorkBill> findSubDivWorkBillsByParentDivisionSnCode(String parentCode) throws ScheduleException;
	/**
	 * 查找所有 分项工程清单信息
	 * @return
	 * @throws ScheduleException
	 */
	public List<SubDivWorkBill> findAllSubDivWorkBill() throws ScheduleException;
	
	/**
	 * 保存 清单，无论属性是否为null，都保存，          保存包括更新和插入两个操作
	 * @param subDivWorkBill
	 * @throws ScheduleException
	 */
	public void saveSubDivWorkBill(SubDivWorkBill subDivWorkBill) throws ScheduleException;
	/**
	 * 保存 清单，属性为null，不保存，          保存包括更新和插入两个操作
	 * @param subDivWorkBill
	 * @throws ScheduleException
	 */
	public void saveSubDivWorkBillSelective(SubDivWorkBill subDivWorkBill) throws ScheduleException;
	/**
	 * 根据主键删除清单信息
	 * @param divisionSnCode
	 * @return
	 * @throws ScheduleException
	 */
	public int deleteSubDivWorkBillByKey(String divisionSnCode) throws ScheduleException;
	/**
	 * 更新 清单，无论属性是否为null,都更新
	 * @param subDivWorkBill
	 * @throws ScheduleException
	 */
	public void updateSubDivWorkBill(SubDivWorkBill subDivWorkBill) throws ScheduleException;
	/**
	 * 更新 清单，属性为null,不更新
	 * @param subDivWorkBill
	 * @throws ScheduleException
	 */
	public void updateSubDivWorkBillSelective(SubDivWorkBill subDivWorkBill) throws ScheduleException;
	
	/**
	 * 根据divisionsncode查看清单状态。
	 * @param divisionsncode
	 */
	public int findSubDivWorkBillStatusByDivisionSnCode(String divisionsncode);
	
	/**
	 * 划分工程清单信息 提交
	 */
	public int updateSubDivWorkBillStatusSubmit(String divisionsncode);

	/**
	 * 将清单项添加到合同中
	 * @param tenderResultCode
	 * @param contractCode
	 * @return
	 */
	int addSubDivWorkBillToContract(DownContract downContract);
	
	/**
	 * 条件查找
	 * @param sqlWhere
	 * @return SubDivWorkBill
	 * @throws TenderPlanException 
	 */
	public List<SubDivWorkBill> findSubBySqlWhere(String sqlWhere) throws ScheduleException;
	
	/**
	 * 根据招标计划编码查招标结果清单
	 * @param tenderPlanCode
	 * @return list
	 * @throws TenderPlanException
	 */
	public List<SubDivWorkBill> findWorkBillByTenderPlanCode(String tenderPlanCode) throws ScheduleException;

	/**
	 * 根据合同编码查询招标结果清单变更信息
	 * @param contractCode
	 * @return
	 */
	List<SubDivWorkBillChange> findWorkBillChangeByContractCode(String contractCode);

	/**
	 * 检查无主清单项是否已经移入到另外的尚未通过审核的招标结果或合同中
	 * @param divisionSnCode 清单项code
	 * @return true if not in false if in
	 */
	boolean checkSubDivWorkBill(String divisionSnCode);

    /**
     * 合同审核不通过后清空合同下清单项的合同code
     * @param contractCode 审核不通过的合同code
     */
	void clearContractCode(String contractCode);
	
}
