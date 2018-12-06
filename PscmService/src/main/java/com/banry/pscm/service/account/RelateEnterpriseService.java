package com.banry.pscm.service.account;

import java.util.List;

/**
 * 关联企业Service
 * 
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
public interface RelateEnterpriseService {

	/**
	 * 根据Key查找关联企业
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public RelateEnterprise findRelateEnterpriseByPrimaryKey(String key) throws AccountException;
	
	/**
	 * 查询所有的关联企业
	 * @author Xu Dingkui
	 * @date 2017年8月4日
	 * @return
	 * @throws AccountException
	 */
	public List<RelateEnterprise> findRelateEnterpriseList() throws AccountException;

	/**
	 * 保存关联企业
	 * 
	 * @param relateEnterprise
	 * @return
	 * @throws AccountException
	 */
	public void saveRelateEnterprise(RelateEnterprise relateEnterprise) throws AccountException;

	/**
	 * 根据指定key删除关联企业
	 * 
	 * @param key
	 * @return
	 * @throws AccountException
	 */
	public int deleteRelateEnterprise(String key) throws AccountException;

}
