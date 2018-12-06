/**
 * 
 */
package com.banry.pscm.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.EngDivisionPlanMapper;
import com.banry.pscm.service.Pager;
import com.banry.pscm.service.schedule.EngDivisionPlan;
import com.banry.pscm.service.schedule.EngDivisionPlanService;
import com.banry.pscm.service.schedule.ScheduleException;

/**
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class EngDivisionPlanServiceImpl implements EngDivisionPlanService {
	@Autowired
	EngDivisionPlanMapper engDivisionPlanMapper;
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionPlanService#findEngDivisionPlanByPrimaryKey(EngDivisionPlan)
	 */
	public EngDivisionPlan findEngDivisionPlanByPrimaryKey(EngDivisionPlan engDivisionPlan) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivisionPlanMapper.selectByPrimaryKey(engDivisionPlan);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionPlanService#saveEngDivisionPlan(com.banry.pscm.service.schedule.EngDivisionPlan)
	 */
	public void saveEngDivisionPlan(EngDivisionPlan engDivisionPlan) throws ScheduleException {
		// TODO Auto-generated method stub
		EngDivisionPlan entity = engDivisionPlanMapper.selectByPrimaryKey(engDivisionPlan);
		if (entity != null) {
			engDivisionPlanMapper.updateByPrimaryKeySelective(engDivisionPlan);
		} else {
			engDivisionPlanMapper.insertSelective(engDivisionPlan);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionPlanService#deleteEngDivisionPlan(EngDivisionPlan)
	 */
	public int deleteEngDivisionPlan(EngDivisionPlan key) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivisionPlanMapper.deleteByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionPlanService#selectAll()
	 */
	@Override
	public List<EngDivisionPlan> selectBySqlWhere(String sqlWhere) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivisionPlanMapper.selectBySqlWhere(sqlWhere);
	}

}
