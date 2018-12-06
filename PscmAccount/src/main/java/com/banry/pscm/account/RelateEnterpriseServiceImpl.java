package com.banry.pscm.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.RelateEnterpriseMapper;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.RelateEnterprise;
import com.banry.pscm.service.account.RelateEnterpriseService;

@Service
public class RelateEnterpriseServiceImpl implements RelateEnterpriseService {

	@Autowired
	RelateEnterpriseMapper enterpriseMapper;

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.RelateEnterpriseService#findRelateEnterpriseByPrimaryKey(java.lang.String)
	 */
	@Override
	public RelateEnterprise findRelateEnterpriseByPrimaryKey(String key) throws AccountException {
		// TODO Auto-generated method stub
		return enterpriseMapper.selectByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.RelateEnterpriseService#findRelateEnterpriseList()
	 */
	@Override
	public List<RelateEnterprise> findRelateEnterpriseList() throws AccountException {
		// TODO Auto-generated method stub
		return enterpriseMapper.selectAll();
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.RelateEnterpriseService#saveRelateEnterprise(com.banry.pscm.service.account.RelateEnterprise)
	 */
	@Override
	public void saveRelateEnterprise(RelateEnterprise relateEnterprise) throws AccountException {
		// TODO Auto-generated method stub
		RelateEnterprise ent = enterpriseMapper.selectByPrimaryKey(relateEnterprise.getEnterpriseCode());
		if (ent != null) {
			enterpriseMapper.updateByPrimaryKeySelective(relateEnterprise);
		} else {
			enterpriseMapper.insertSelective(relateEnterprise);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.RelateEnterpriseService#deleteRelateEnterprise(java.lang.String)
	 */
	@Override
	public int deleteRelateEnterprise(String key) throws AccountException {
		// TODO Auto-generated method stub
		return enterpriseMapper.deleteByPrimaryKey(key);
	}
}
