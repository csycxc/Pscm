/**
 * 
 */
package com.banry.pscm.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.ScheduleLaborMapper;
import com.banry.pscm.service.schedule.ScheduleLabor;
import com.banry.pscm.service.schedule.ScheduleLaborService;
import com.banry.pscm.service.schedule.ScheduleException;

/**
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class ScheduleLaborServiceImpl implements ScheduleLaborService {
	@Autowired
	ScheduleLaborMapper laborMapper;
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.LaborService#findLaborByPrimaryKey(Labor)
	 */
	public ScheduleLabor findLaborByPrimaryKey(ScheduleLabor key) throws ScheduleException {
		// TODO Auto-generated method stub
		return laborMapper.selectByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.LaborService#saveLabor(com.banry.pscm.service.schedule.Labor)
	 */
	public void saveLabor(ScheduleLabor labor) throws ScheduleException {
		// TODO Auto-generated method stub
		ScheduleLabor entity = laborMapper.selectByPrimaryKey(labor);
		if (entity != null) {
			laborMapper.updateByPrimaryKeySelective(labor);
		} else {
			laborMapper.insertSelective(labor);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.LaborService#deleteLabor(Labor)
	 */
	public int deleteLabor(ScheduleLabor key) throws ScheduleException {
		// TODO Auto-generated method stub
		return laborMapper.deleteByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.LaborService#selectAll()
	 */
	@Override
	public List<ScheduleLabor> selectAll() throws ScheduleException {
		// TODO Auto-generated method stub
		return laborMapper.selectAll();
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.LaborService#selectLaborCompanyByDiviCode(java.lang.String)
	 */
	@Override
	public String selectLaborCompanyByDivSnCode(String divSnCode) throws ScheduleException {
		// TODO Auto-generated method stub
		return laborMapper.selectLaborCompanyByDivSnCode(divSnCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.LaborService#findLaborByUserCode(java.lang.String)
	 */
	@Override
	public ScheduleLabor findLaborByUserCode(String userCode) throws ScheduleException {
		// TODO Auto-generated method stub
		return laborMapper.selectByUserCode(userCode);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.LaborService#updateByPrimaryKey(com.banry.pscm.service.schedule.Labor)
	 */
	@Override
	public void updateByPrimaryKey(ScheduleLabor labor) throws ScheduleException {
		// TODO Auto-generated method stub
		laborMapper.updateByPrimaryKeySelective(labor);
	}

	/*
	 * (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.LaborService#selectAllByDivSnCode(java.lang.String)
	 */
	@Override
	public List<ScheduleLabor> findLaborByDivSnCode(String divisionsncode) throws ScheduleException {
		// TODO Auto-generated method stub
		return laborMapper.findLaborByDivSnCode(divisionsncode);
	}
}
