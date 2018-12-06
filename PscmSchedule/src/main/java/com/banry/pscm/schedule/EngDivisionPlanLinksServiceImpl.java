/**
 * 
 */
package com.banry.pscm.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.EngDivisionPlanLinksMapper;
import com.banry.pscm.service.schedule.EngDivisionPlanLinks;
import com.banry.pscm.service.schedule.EngDivisionPlanLinksService;
import com.banry.pscm.service.schedule.ScheduleException;

/**
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class EngDivisionPlanLinksServiceImpl implements EngDivisionPlanLinksService {
	@Autowired
	EngDivisionPlanLinksMapper engDivisionPlanLinksMapper;
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionPlanLinksService#findEngDivisionPlanLinksByPrimaryKey(EngDivisionPlanLinks)
	 */
	public EngDivisionPlanLinks findEngDivisionPlanLinksByPrimaryKey(Long key) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivisionPlanLinksMapper.selectByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionPlanLinksService#saveEngDivisionPlanLinks(com.banry.pscm.service.schedule.EngDivisionPlanLinks)
	 */
	public void saveEngDivisionPlanLinks(EngDivisionPlanLinks engDivisionPlanLinks) throws ScheduleException {
		// TODO Auto-generated method stub
		EngDivisionPlanLinks entity = engDivisionPlanLinksMapper.selectByPrimaryKey(engDivisionPlanLinks.getId());
		if (entity != null) {
			engDivisionPlanLinksMapper.updateByPrimaryKeySelective(engDivisionPlanLinks);
		} else {
			engDivisionPlanLinksMapper.insertSelective(engDivisionPlanLinks);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionPlanLinksService#deleteEngDivisionPlanLinks(EngDivisionPlanLinks)
	 */
	public int deleteEngDivisionPlanLinks(Long key) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivisionPlanLinksMapper.deleteByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivisionPlanLinksService#selectAll()
	 */
	@Override
	public List<EngDivisionPlanLinks> selectBySqlWhere(String sqlWhere) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivisionPlanLinksMapper.selectBySqlWhere(sqlWhere);
	}
}
