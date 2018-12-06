package com.banry.pscm.account;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.RoleMapper;
import com.banry.pscm.persist.dao.RoleUserMapper;
import com.banry.pscm.persist.dao.SysResourceMapper;
import com.banry.pscm.persist.dao.SysRolesResourcesMapper;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.Role;
import com.banry.pscm.service.account.RoleKey;
import com.banry.pscm.service.account.RoleUser;
import com.banry.pscm.service.account.SysResource;
import com.banry.pscm.service.account.SysRolesResources;
import com.banry.pscm.service.account.SysRolesService;
import com.banry.pscm.service.account.SysUsers;

@Service
public class SysRolesServiceImpl implements SysRolesService {

	@Autowired
	RoleMapper roleMapper;
	@Autowired
	SysResourceMapper resMapper;
	@Autowired
	RoleUserMapper roleUserMapper;
	@Autowired
	SysRolesResourcesMapper roleResMapper;
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#getById(java.lang.String)
	 */
	@Override
	public Role getById(RoleKey id) throws AccountException {
		// TODO Auto-generated method stub
		return roleMapper.selectByPrimaryKey(id);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#saveRole(com.banry.pscm.service.account.Role)
	 */
	@Override
	public void saveRole(Role role) throws AccountException {
		// TODO Auto-generated method stub
		Role r = roleMapper.selectByPrimaryKey(role);
		if (r != null) {
			roleMapper.updateByPrimaryKeySelective(role);
		} else {
			roleMapper.insertSelective(role);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#deleteRole(java.lang.String)
	 */
	@Override
	public void deleteRole(RoleKey key) throws AccountException {
		// TODO Auto-generated method stub
		roleMapper.deleteByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#findAllRole(java.lang.String)
	 */
	@Override
	public List<Role> findAllRole(String sqlWhere) throws AccountException {
		// TODO Auto-generated method stub
		return roleMapper.selectBySqlWhere(sqlWhere);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#saveRoleResources(com.banry.pscm.service.account.SysRolesResources)
	 */
	@Override
	public void saveRoleResources(SysRolesResources roleRes) throws AccountException {
		// TODO Auto-generated method stub
		roleResMapper.insert(roleRes);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#findRoleRes(java.lang.String)
	 */
	@Override
	public List<SysResource> findRoleRes(String roleCode, String tenantCode) throws AccountException {
		// TODO Auto-generated method stub
		return resMapper.selectResourceByRoleCode(roleCode, tenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#saveRoleRes(java.lang.String, java.lang.String)
	 */
	@Override
	public void saveRoleRes(String roleCode, String ids, String createUser, String tenantCode) throws AccountException {
		// TODO Auto-generated method stub
		roleResMapper.deleteByRoleCode(roleCode, tenantCode);
		if (ids != null && !"".equals(ids)) {
			String resCodes[] = ids.split(",");
			if (resCodes.length > 0) {
				for (String resCode : resCodes) {
	//				SysRolesResources rr = roleResMapper.selectByRoleAndResCode(roleCode, resCode);
	//				if (rr == null) {
					SysRolesResources rr = new SysRolesResources();
					rr.setRoleResourceCode(UUID.randomUUID().toString().replaceAll("\\-", ""));
					rr.setRoleCode(roleCode);
					rr.setTenantCode(tenantCode);
					rr.setResourceCode(resCode);
					rr.setCreateUser(createUser);
					rr.setCreateDate(new Date());
					rr.setStatus(1);
					roleResMapper.insert(rr);
	//				}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#saveUsersRoles(com.banry.pscm.service.account.RoleUser)
	 */
	@Override
	public void saveUsersRoles(String roleCode, String ids, String createUser, String tenantCode) throws AccountException {
		// TODO Auto-generated method stub
		String userCodes[] = ids.split(",");
		if (userCodes.length > 0) {
			for (String userCode : userCodes) {
				RoleUser ru = roleUserMapper.selectByRoleAndUserCode(roleCode, tenantCode, userCode);
				if (ru == null) {
					ru = new RoleUser();
					ru.setRoleUserCode(UUID.randomUUID().toString().replaceAll("\\-", ""));
					ru.setRoleCode(roleCode);
					ru.setTenantCode(tenantCode);
					ru.setUserCode(userCode);
					ru.setCreateUser(createUser);
					ru.setCreateDate(new Date());
					ru.setStatus(1);
					roleUserMapper.insert(ru);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#delUserInRolesList(java.lang.String, java.lang.String)
	 */
	@Override
	public void delUserInRolesList(String roleCode, String tenantCode, String ids) throws AccountException {
		// TODO Auto-generated method stub
		String userCodes[] = ids.split(",");
		if (userCodes.length > 0) {
			for (String userCode : userCodes) {
				RoleUser ru = roleUserMapper.selectByRoleAndUserCode(roleCode, tenantCode, userCode);
				if (ru != null) {
					roleUserMapper.deleteByPrimaryKey(ru.getRoleUserCode());
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#findUsersByRole(java.lang.String)
	 */
	@Override
	public List<SysUsers> findUsersByRole(String roleId, String tenantCode) throws AccountException {
		// TODO Auto-generated method stub
		return roleUserMapper.findUsersByRole(roleId, tenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#findWaitUsersByRole(java.lang.String)
	 */
	@Override
	public List<SysUsers> findWaitUsersByRole(String roleCode, String tenantCode) throws AccountException {
		// TODO Auto-generated method stub
		return roleUserMapper.findWaitUsersByRole(roleCode, tenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysRolesService#findRoleByUser(java.lang.String)
	 */
	@Override
	public List<Role> findRoleByUser(String tenantCode, String userCode) throws AccountException {
		// TODO Auto-generated method stub
		return roleMapper.selectRoleByUserCode(tenantCode, userCode);
	}

	/**
	 * 根据角色删除其拥有的资源模块
	 * @author chenshiyu
	 * @param roleCode
	 */
	@Override
	public int deleteRoleRes(String roleCode, String tenantCode) {
		return roleResMapper.deleteByRoleCode(roleCode, tenantCode);
	}

	@Override
	public List<SysUsers> findUsersByRoleWithParentTenantAccount(String roleCode, String tenantCode, String parentTenantAccount)
			throws AccountException {
		// TODO Auto-generated method stub
		return roleUserMapper.findUsersByRoleWithParentTenantAccount(roleCode, tenantCode, parentTenantAccount);
	}
	
}
