package com.banry.pscm.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.SysDeptsMapper;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysDepts;
import com.banry.pscm.service.account.SysDeptsKey;
import com.banry.pscm.service.account.SysDeptsService;

@Service
public class SysDeptsServiceImpl implements SysDeptsService {
	
	@Autowired
	SysDeptsMapper deptMapper;

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysDeptsService#getByDeptCode(int)
	 */
	@Override
	public SysDepts getByDeptCode(SysDeptsKey key) throws AccountException {
		// TODO Auto-generated method stub
		return deptMapper.selectByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysDeptsService#saveDept(com.banry.pscm.service.account.SysDepts)
	 */
	@Override
	public void saveDept(SysDepts dept) throws AccountException {
		// TODO Auto-generated method stub
		SysDepts d = deptMapper.selectByPrimaryKey(dept);
		if (d != null) {
			deptMapper.updateByPrimaryKey(dept);
		} else {
			deptMapper.insertSelective(dept);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysDeptsService#deleteDept(int)
	 */
	@Override
	public void deleteDept(SysDeptsKey key) throws AccountException {
		// TODO Auto-generated method stub
		deptMapper.deleteByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.account.SysDeptsService#findAllDept()
	 */
	@Override
	public List findAllDept(String tenantCode) throws AccountException {
		// TODO Auto-generated method stub
		return deptMapper.selectAll(tenantCode);
	}

}
