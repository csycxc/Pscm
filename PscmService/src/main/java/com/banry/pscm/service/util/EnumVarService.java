package com.banry.pscm.service.util;

import java.util.List;

/**
 * 枚举Service
 * @author Xu Dingkui
 * @date 2017年8月13日
 */
public interface EnumVarService {

	
	/**
	 * 根据枚举名称查找
	 * 
	 * @param enumName
	 * @return
	 * @throws Exception
	 */
	public List<EnumVar> findByEnumName(String enumName) throws UtilException;
	
	/**
	 * 依据主键查询
	 * @param enumVar
	 * @return
	 * @throws UtilException
	 */
	public EnumVar findEnumVarByPrimaryKey(EnumVar enumVar) throws UtilException;
}
