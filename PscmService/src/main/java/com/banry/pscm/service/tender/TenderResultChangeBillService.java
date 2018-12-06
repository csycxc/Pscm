package com.banry.pscm.service.tender;

import java.util.List;

/**
 * 招标结果变更清单Service
 * @author chenJuan
 * @date 2018-5-30
 */
public interface TenderResultChangeBillService {

	/**
	 * 根据划分项(分项工程)序号编码查找结果变更
	 * @param divisionSnCode
	 * @return TenderResultChangeBill
	 * @throws TenderPlanException
	 */
	public TenderResultChangeBill selectByPrimaryKey(String divisionSnCode) throws TenderPlanException;
	/**
	 * 查找全部变更清单
	 * @return list
	 * @throws TenderPlanException
	 */
	public List<TenderResultChangeBill> findAllChangeBill() throws TenderPlanException;
	/**
	 * 保存变更清单，不论为空，均保存
	 * @param record
	 * @throws TenderPlanException
	 */
	public void saveChangeBill(TenderResultChangeBill record) throws TenderPlanException;
	/**
	 * 保存变更清单，如果为空，不保存
	 * @param record
	 * @throws TenderPlanException
	 */
	public void saveChangeBillSelective(TenderResultChangeBill record) throws TenderPlanException;
	/**
     * 更新招标结果变更清单，划分项(分项工程)序号编码不为空（更新条件），其它项，若不论为空，则更新
     * @param record
     * @throws TenderPlanException
     */
	public void updateByPrimaryKeySelective(TenderResultChangeBill record) throws TenderPlanException;
    /**
     * 更新招标结果变更清单，划分项(分项工程)序号编码不为空（更新条件），其它项，不论为空，一律更新，如果为空，则置空
     * @param record
     * @throws TenderPlanException
     */
	public void updateByPrimaryKey(TenderResultChangeBill record) throws TenderPlanException;
	/**
	 * 根据划分项(分项工程)序号编码删除变更
	 * @param divisionSnCode
	 * @return int
	 * @throws TenderPlanException
	 */
	public int deleteByPrimaryKey(String divisionSnCode) throws TenderPlanException;
	
	/**
	 * 条件查询招标结果变更清单
	 * @param sqlWhere
	 * @return
	 * @throws TenderPlanException
	 */
	 public List<TenderResultChangeBill> findChangeBillByChangeCode(String tenderResultIdChangeCode) throws TenderPlanException;
	
}
