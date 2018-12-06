package com.banry.pscm.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.SysUsersMapper;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysUsers;
import com.banry.pscm.service.account.SysUsersKey;
import com.banry.pscm.service.account.SysUsersService;

@Service
public class SysUsersServiceImpl implements SysUsersService {

	@Autowired
	SysUsersMapper userMapper;

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysUsersService#selectByPrimaryKey(java.lang.String)
	 */
	@Override
	public SysUsers getByCode(SysUsersKey key) throws AccountException {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysUsersService#saveUser(com.banry.pscm.service.account.SysUsers)
	 */
	@Override
	public void saveUser(SysUsers user) throws AccountException {
		// TODO Auto-generated method stub
		SysUsers u = userMapper.selectByPrimaryKey(user);
		if (u != null) {
			userMapper.updateByPrimaryKeySelective(user);
		} else {
			userMapper.insertSelective(user);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysUsersService#deleteUser(java.lang.String)
	 */
	@Override
	public void deleteUser(SysUsersKey code) throws AccountException {
		// TODO Auto-generated method stub
		userMapper.deleteByPrimaryKey(code);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysUsersService#findAllUser()
	 */
	@Override
	public List<SysUsers> findAllUser(String tenantCode) throws AccountException {
		// TODO Auto-generated method stub
		return userMapper.selectAll(tenantCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysUsersService#checkEngDivAuthority(java.lang.String, java.lang.String)
	 */
	@Override
	public String checkEngDivAuthority(String userCode, String divSnCode) {
		// TODO Auto-generated method stub
		return userMapper.checkEngDivAuthority(userCode, divSnCode);
	}

	@Override
	public SysUsers getByTelephone(String telephone) throws AccountException {
		// TODO Auto-generated method stub
		return userMapper.selectByTelephone(telephone);
	}

	@Override
	public SysUsers getByCodeAndTenantAccount(String code, String tenantCode, String parentTenantAccount) throws AccountException {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKeyAndTenantAccount(code, tenantCode, parentTenantAccount);
	}

}
