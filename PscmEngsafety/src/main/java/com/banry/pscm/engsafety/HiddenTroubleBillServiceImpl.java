/**
 * 
 */
package com.banry.pscm.engsafety;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banry.pscm.persist.dao.HiddenTroubleBillMapper;
import com.banry.pscm.service.conf.UUIDModel;
import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTroubleBill;
import com.banry.pscm.service.engsafety.HiddenTroubleBillService;
import com.banry.pscm.service.util.EnumVar;

/**
 * 隐患
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
@Service
public class HiddenTroubleBillServiceImpl implements HiddenTroubleBillService {

	@Autowired
	HiddenTroubleBillMapper hiddenTroubleBillMapper;
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#findHiddenTroubleByDivCode(java.lang.String)
	 */
	@Override
	public List<HiddenTroubleBill> findHiddenTroubleBillByDivItemCode(String divItemCode, String trobleFrom, String parentTenantCode) throws EngsafetyException {
		// TODO Auto-generated method stub
		return hiddenTroubleBillMapper.selectByDivItemCode(divItemCode, trobleFrom, parentTenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#findHiddenTroubleBySqlWherer(java.lang.String)
	 */
	@Override
	public List<HiddenTroubleBill> findHiddenTroubleBillBySqlWhere(String sqlWhere, String parentTenantCode) throws EngsafetyException {
		// TODO Auto-generated method stub
		return hiddenTroubleBillMapper.selectBySqlWhere(sqlWhere, parentTenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#findHiddenTroubleOrderByRate(java.lang.String)
	 */
	@Override
	public List<HiddenTroubleBill> findHiddenTroubleBillOrderByRate(String divItemCode, String parentTenantCode) throws EngsafetyException {
		// TODO Auto-generated method stub
		return hiddenTroubleBillMapper.selectOrderByRate(divItemCode, parentTenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#findTrobuleCateInvestItem(java.lang.String)
	 */
	@Override
	public List<HiddenTroubleBill> findTroubleCateInvestItem(String sqlWhere, String parentTenantCode) throws EngsafetyException {
		// TODO Auto-generated method stub
		return hiddenTroubleBillMapper.selectTroubleCateInvestItem(sqlWhere, parentTenantCode);
	}
	
	@Override
	public HiddenTroubleBill findHiddenTroubleBillByKey(String troubleCode, String parentTenantCode) throws EngsafetyException {
		return hiddenTroubleBillMapper.selectByPrimaryKey(troubleCode, parentTenantCode);
	}
	@Override
	public List<HiddenTroubleBill> findHiddenTroubleBillsByDivItemCode(String divItemCode, String parentTenantCode) throws EngsafetyException {
		return hiddenTroubleBillMapper.findHiddenTroublesByDivItemCode(divItemCode, parentTenantCode);
	}
	
	
	@Override
	public List<HiddenTroubleBill> findHiddenTroubleBillsByDivSnCode(String divItemCode, String parentTenantCode) throws EngsafetyException  {
		return hiddenTroubleBillMapper.findHiddenTroublesByDivSnCode(divItemCode, parentTenantCode);
	}
	
	@Override
	public List<HiddenTroubleBill> findHiddenTroubleBillsByTroubleCate(String troubleCate, String parentTenantCode) throws EngsafetyException {
		return hiddenTroubleBillMapper.findHiddenTroublesByTroubleCate(troubleCate, parentTenantCode);
	}
	@Override
	public List<HiddenTroubleBill> findHiddenTroubleBillsByInvestItem(String investItem, String parentTenantCode) throws EngsafetyException {
		return hiddenTroubleBillMapper.findHiddenTroublesByInvestItem(investItem, parentTenantCode);
	}
	@Override
	public List<HiddenTroubleBill> findHiddenTroubleBillsByInvestContent(String investContent, String parentTenantCode) throws EngsafetyException {
		return hiddenTroubleBillMapper.findHiddenTroublesByInvestContent(investContent, parentTenantCode);
	}
	@Override
	public List<HiddenTroubleBill> findAllHiddenTroubleBill(String parentTenantCode) throws EngsafetyException {
		return hiddenTroubleBillMapper.findAllHiddenTrouble(parentTenantCode);
	}
	
	@Override
	@Transactional
	public void saveHiddenTroubleBill(HiddenTroubleBill hiddenTrouble) throws EngsafetyException {
		String troubleCode = hiddenTrouble.getTroubleCode();
		if(troubleCode == null || "".equals(troubleCode) || troubleCode.indexOf("jqg")==0) {
			hiddenTrouble.setTroubleCode(UUIDModel.getUUID());
			hiddenTroubleBillMapper.insert(hiddenTrouble);
		}else {
			hiddenTroubleBillMapper.updateByPrimaryKey(hiddenTrouble);
		}
	}
	@Override
	@Transactional
	public void saveHiddenTroubleBillSelective(HiddenTroubleBill hiddenTrouble) throws EngsafetyException {
		if(hiddenTrouble.getTroubleCode() == null || "".equals(hiddenTrouble.getTroubleCode())) {
			//主键为空，插入，主键用uuid
			hiddenTrouble.setTroubleCode(UUIDModel.getUUID());
			hiddenTroubleBillMapper.insertSelective(hiddenTrouble);
		}else {//更新
			hiddenTroubleBillMapper.updateByPrimaryKeySelective(hiddenTrouble);
		}
	}
	@Override
	@Transactional
	public int deleteHiddenTroubleBillByKey(String troubleCode) throws EngsafetyException {
		return hiddenTroubleBillMapper.deleteByPrimaryKey(troubleCode);
	}
	@Override
	@Transactional
	public void updateHiddenTroubleBill(HiddenTroubleBill hiddenTrouble) throws EngsafetyException {
		if(hiddenTrouble.getTroubleCode() == null || "".equals(hiddenTrouble.getTroubleCode())) 
			throw new EngsafetyException("主键不能为null，更新失败！");
		if(hiddenTrouble.getDivItemCode() == null || "".equals(hiddenTrouble.getDivItemCode())) 
			throw new EngsafetyException("外键不能为null，更新失败！");
		hiddenTroubleBillMapper.updateByPrimaryKey(hiddenTrouble);
	}
	@Override
	@Transactional
	public void updateHiddenTroubleBillSelective(HiddenTroubleBill hiddenTrouble) throws EngsafetyException {
		if(hiddenTrouble.getTroubleCode() == null || "".equals(hiddenTrouble.getTroubleCode())) 
			throw new EngsafetyException("主键不能为null，更新失败！");
		hiddenTroubleBillMapper.updateByPrimaryKeySelective(hiddenTrouble);
	}
	
	/**
	 * 根据divisionsncode 查找divItemCode,再根据DivItemCode删除隐患
	 */
	@Override
	public int deleteHiddenTroubleBillByDivisionSnCode(String divisionsncode) {
		// TODO Auto-generated method stub
		return hiddenTroubleBillMapper.deleteHiddenTroubleBillByDivisionSnCode(divisionsncode);
	}

	@Override
	public List<EnumVar> findEnumVarAllTroubleCate(String parentTenantCode) {
		return hiddenTroubleBillMapper.findEnumVarAllTroubleCate(parentTenantCode);
	}

	@Override
	public List<EnumVar> findEnumVarAllTroubleLevel(String parentTenantCode) {
		return hiddenTroubleBillMapper.findEnumVarAllTroubleLevel(parentTenantCode);
	}

	@Override
	public EnumVar findEnumVarByEnumValueName(String enumValueName,String parentTenantCode) {
		// TODO Auto-generated method stub
		return hiddenTroubleBillMapper.findEnumVarByEnumValueName(enumValueName,parentTenantCode);
	}
}
