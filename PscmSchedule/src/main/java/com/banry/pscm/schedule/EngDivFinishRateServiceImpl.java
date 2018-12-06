/**
 * 
 */
package com.banry.pscm.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.EngDivFinishRateMapper;
import com.banry.pscm.service.Pager;
import com.banry.pscm.service.schedule.EngDivFinishRate;
import com.banry.pscm.service.schedule.EngDivFinishRateService;
import com.banry.pscm.service.schedule.ScheduleException;

/**
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class EngDivFinishRateServiceImpl implements EngDivFinishRateService {
	@Autowired
	EngDivFinishRateMapper engDivFinishRateMapper;
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivFinishRateService#findEngDivFinishRateByPrimaryKey(EngDivFinishRate)
	 */
	public EngDivFinishRate findEngDivFinishRateByPrimaryKey(EngDivFinishRate engDivFinishRate) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivFinishRateMapper.selectByPrimaryKey(engDivFinishRate);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivFinishRateService#saveEngDivFinishRate(com.banry.pscm.service.schedule.EngDivFinishRate)
	 */
	public void saveEngDivFinishRate(EngDivFinishRate engDivFinishRate) throws ScheduleException {
		// TODO Auto-generated method stub
		EngDivFinishRate entity = engDivFinishRateMapper.selectByPrimaryKey(engDivFinishRate);
		if (entity != null) {
			engDivFinishRateMapper.updateByPrimaryKeySelective(engDivFinishRate);
		} else {
			engDivFinishRateMapper.insertSelective(engDivFinishRate);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivFinishRateService#paging(java.lang.String, int, int)
	 */
	public List<EngDivFinishRate> findEngDivFinishRateBySqlWhere(String sqlWhere) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivFinishRateMapper.selectBySqlWhere(sqlWhere);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivFinishRateService#deleteEngDivFinishRate(EngDivFinishRate)
	 */
	public int deleteEngDivFinishRate(EngDivFinishRate key) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivFinishRateMapper.deleteByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngDivFinishRateService#sumFinishNumberByDivSnCode(java.lang.String)
	 */
	@Override
	public EngDivFinishRate sumFinishNumberByDivSnCode(String divCode) throws ScheduleException {
		// TODO Auto-generated method stub
		return engDivFinishRateMapper.sumFinishNumberByDivSnCode(divCode);
	}

}
