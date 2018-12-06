package com.banry.pscm.service.util;

import java.util.List;

/**
 * 常量Service
 * @author Xu Dingkui
 * @date 2017年8月13日
 */
public interface ConstantService {

	/**
	 * 根据Key查找常量
	 * 
	 * @param dict
	 * @return
	 * @throws Exception
	 */
	public Constant findConstantByPrimaryKey(String constantName) throws UtilException;
	
	/**
	 * 查找常量
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Constant> findAllConstant() throws UtilException;

	/**
	 * 保存常量
	 * 
	 * @param constant
	 * @return
	 * @throws UtilException
	 */
	public void saveConstant(Constant constant) throws UtilException;

	/**
	 * 根据指定key删除常量
	 * 
	 * @param constantName
	 * @return
	 * @throws UtilException
	 */
	public int deleteConstant(String constantName) throws UtilException;

	int updateConstant(Constant constant);

}
