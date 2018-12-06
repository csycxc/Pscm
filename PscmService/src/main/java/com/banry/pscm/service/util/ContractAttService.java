package com.banry.pscm.service.util;

import java.util.List;

/**
 * 附件Service
 * @author Xu Dingkui
 * @date 2017年8月13日
 */
public interface ContractAttService {

	/**
	 * 根据Key查找附件
	 * 
	 * @param fileInName
	 * @return
	 * @throws Exception
	 */
	public ContractAtt findByPrimaryKey(String fileInName) throws UtilException;
	
	/**
	 * 保存附件
	 * 
	 * @param contractAtt
	 * @return
	 * @throws UtilException
	 */
	public void saveContractAtt(ContractAtt contractAtt) throws UtilException;

	/**
	 * 根据指定key删除附件
	 * 
	 * @param fileInName
	 * @return
	 * @throws UtilException
	 */
	public int deleteContractAtt(String fileInName) throws UtilException;
	
	/**
	 * 根据fileInNames查询附件（多个fileInName用逗号隔开）
	 * 
	 * @param fileInNames
	 * @return
	 * @throws UtilException
	 */
	public List<ContractAtt> findByFileInNames(String fileInNames) throws UtilException;

}
