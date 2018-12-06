/**
 * 
 */
package com.banry.pscm.engsafety;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banry.pscm.persist.dao.ContractAttMapper;
import com.banry.pscm.persist.dao.EngDivisionMapper;
import com.banry.pscm.persist.dao.HiddenTroubleMapper;
import com.banry.pscm.persist.dao.SysUsersMapper;
import com.banry.pscm.service.account.SysUsers;
import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTrouble;
import com.banry.pscm.service.engsafety.HiddenTroubleService;
import com.banry.pscm.service.util.ContractAtt;
import com.banry.pscm.service.workflow.WorkFlowException;
import com.banry.pscm.service.workflow.WorkFlowService;

/**
 * 隐患
 * @author Xu Dingkui
 * @date 2017年6月17日
 */
@Service
public class HiddenTroubleServiceImpl implements HiddenTroubleService {

	@Autowired
	HiddenTroubleMapper hiddenTroubleMapper;
	@Autowired
	EngDivisionMapper engDivisionMapper;
	@Autowired
	WorkFlowService workFlowService;
	@Autowired
	SysUsersMapper userMapper;
	@Autowired
	ContractAttMapper contractAttMapper;
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#findHiddenTroubleByPrimaryKey(java.lang.String)
	 */
	public HiddenTrouble findHiddenTroubleByPrimaryKey(String key, String parentTenantCode) throws EngsafetyException {
		// TODO Auto-generated method stub
		return hiddenTroubleMapper.selectByPrimaryKey(key, parentTenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#saveHiddenTrouble(com.banry.pscm.service.engsafety.HiddenTrouble)
	 */
	public void saveHiddenTrouble(HiddenTrouble hiddenTrouble) throws EngsafetyException {
		// TODO Auto-generated method stub
		HiddenTrouble entity = hiddenTroubleMapper.selectByKeyWithoutBill(hiddenTrouble.getTroubleCode());
		if (entity != null) {
			hiddenTroubleMapper.updateByPrimaryKeySelective(hiddenTrouble);
		} else {
			hiddenTroubleMapper.insertSelective(hiddenTrouble);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#deleteHiddenTrouble(java.lang.String)
	 */
	public int deleteHiddenTrouble(String key) throws EngsafetyException {
		// TODO Auto-generated method stub
		return hiddenTroubleMapper.deleteByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#findHiddenTroubleBySqlWhere(java.lang.String)
	 */
	@Override
	public List<HiddenTrouble> findHiddenTroubleBySqlWhere(String sqlWhere, String parentTenantCode) throws EngsafetyException {
		// TODO Auto-generated method stub
		return hiddenTroubleMapper.selectBySqlWhere(sqlWhere, parentTenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#startProcessInstance(com.banry.pscm.service.engsafety.HiddenTrouble)
	 */
	@Override
	@Transactional
	public void startProcessInstance(HiddenTrouble htt, String processDefinitionKey, String userName, String tenantCode, String parentTenantAccount)  throws EngsafetyException {
		// TODO Auto-generated method stub
		
		Map<String, Object> variables = new HashMap<String, Object>();
		//安全负责人
		List safetyOfficerList = userMapper.selectSafetyOfficerByDivSnCode(htt.getDivisionSnCode(), parentTenantAccount);
		variables.put("parentTenantAccount", parentTenantAccount);
		variables.put("tenantCode", tenantCode);
		if (safetyOfficerList.size() > 0) {
			int index = (int) (Math.random() * safetyOfficerList.size());
			SysUsers hh = (SysUsers) safetyOfficerList.get(index);
			variables.put("personFlag", true);
			variables.put("safetyOfficer", hh.getUserCode());
			htt.setSafetyChargeUser(hh.getUserCode());
		} else {
			variables.put("personFlag", false);
		}
		saveHiddenTrouble(htt);
		if (htt.getRectifyPostpone() > 0) {
			variables.put("delayFlag", true);
		} else {
			variables.put("delayFlag", false);
		}
		//上报人
		variables.put("reportUser", userName);
		if (htt.getRectifyTimeLimit() != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			variables.put("duTime", sdf.format(htt.getRectifyTimeLimit()) + "T23:59:59");
		}
		workFlowService.startProcessInstanceByKey(processDefinitionKey, processDefinitionKey + "-" + htt.getTroubleCode(), variables);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.engsafety.HiddenTroubleService#complete(java.lang.String, java.util.Map, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional
	public void complete(String taskId, Map<String, Object> variables, String userCode, String troubletreacode, String attachUrl)
			throws EngsafetyException {
		// TODO Auto-generated method stub
		try {
			if (attachUrl != null && !"".equals(attachUrl)) {
				String url[] = attachUrl.split(",");
				for (String u : url) {
					ContractAtt doc = new ContractAtt();
					String type = u.substring(u.lastIndexOf("."), u.length());
					doc.setType(type);
					doc.setActualFileName(u.substring(u.lastIndexOf("/"), u.length()));
					doc.setLocation(u);
					doc.setFileInName(String.valueOf((new Date()).getTime()));
					contractAttMapper.insertSelective(doc);
				}
			}
			workFlowService.complete(taskId, variables, userCode);
		} catch (WorkFlowException e) {
			// TODO Auto-generated catch block
			throw new EngsafetyException(e.getMessage());
		}
	}

	@Override
	public HiddenTrouble selectByKeyWithoutBill(String key) throws EngsafetyException {
		// TODO Auto-generated method stub
		return hiddenTroubleMapper.selectByKeyWithoutBill(key);
	}
}
