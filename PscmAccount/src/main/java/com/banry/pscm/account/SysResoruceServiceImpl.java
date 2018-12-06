package com.banry.pscm.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.SysResourceMapper;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysResource;
import com.banry.pscm.service.account.SysResourceService;

@Service
public class SysResoruceServiceImpl implements SysResourceService {

	@Autowired
	SysResourceMapper resourceMapper;
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysResourceService#getBySysResourceCode(java.lang.String)
	 */
	@Override
	public SysResource getBySysResourceCode(String resourceCode) throws AccountException {
		// TODO Auto-generated method stub
		return resourceMapper.selectByPrimaryKey(resourceCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysResourceService#saveSysResource(com.banry.pscm.service.account.SysResource)
	 */
	@Override
	public void saveSysResource(SysResource resource) throws AccountException {
		// TODO Auto-generated method stub
		if (resource.getResourceCode() != null && !"".equals(resource.getResourceCode())) {
			resourceMapper.updateByPrimaryKeySelective(resource);
		} else {
			resourceMapper.insertSelective(resource);
		}
		
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysResourceService#deleteSysResource(java.lang.String)
	 */
	@Override
	public void deleteSysResource(String resourceCode) throws AccountException {
		// TODO Auto-generated method stub
		resourceMapper.deleteByPrimaryKey(resourceCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysResourceService#findSysResourceByUserCode(java.lang.String)
	 */
	@Override
	public List<SysResource> findSysResourceByUserCode(String userCode, String prCode, String menu, String tenantCode) throws AccountException {
		// TODO Auto-generated method stub
		return resourceMapper.selectResourceByUserCode(userCode, prCode, menu, tenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysResourceService#findAll()
	 */
	@Override
	public List<SysResource> findAll() throws AccountException {
		// TODO Auto-generated method stub
		return resourceMapper.selectAll();
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysResourceService#findSysResourceByRoleCode(java.lang.String)
	 */
	@Override
	public List<SysResource> findSysResourceByRoleCode(String roleCode, String tenantCode) throws AccountException {
		// TODO Auto-generated method stub
		return resourceMapper.selectResourceByRoleCode(roleCode, tenantCode);
	}

}
