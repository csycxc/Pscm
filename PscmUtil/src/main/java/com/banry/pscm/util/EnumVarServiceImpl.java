package com.banry.pscm.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.EnumVarMapper;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.service.util.EnumVarService;
import com.banry.pscm.service.util.UtilException;

/**
 * 枚举
 * @author Xu Dingkui
 * @date 2017年8月13日
 */
@Service
public class EnumVarServiceImpl implements EnumVarService {
	@Autowired
	EnumVarMapper enumVarMapper;

	@Override
	public List<EnumVar> findByEnumName(String enumName) throws UtilException {
		// TODO Auto-generated method stub
		return enumVarMapper.selectByEnumName(enumName);
	}

	@Override
	public EnumVar findEnumVarByPrimaryKey(EnumVar enumVar) throws UtilException {
		// TODO Auto-generated method stub
		return enumVarMapper.selectByPrimaryKey(enumVar);
	}
	

}
