/**
 * 
 */
package com.banry.pscm.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.EngineeringMapper;
import com.banry.pscm.service.schedule.Engineering;
import com.banry.pscm.service.schedule.EngineeringService;
import com.banry.pscm.service.schedule.ScheduleException;

/**
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class EngineeringServiceImpl implements EngineeringService {
	@Autowired
	EngineeringMapper engineeringMapper;
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngineeringService#findEngineeringByPrimaryKey(Engineering)
	 */
	public Engineering selectEngineeringByPrimaryKey(String key) throws ScheduleException {
		// TODO Auto-generated method stub
		return engineeringMapper.selectByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngineeringService#saveEngineering(com.banry.pscm.service.schedule.Engineering)
	 */
	public String saveEngineering(Engineering engineering) throws ScheduleException {
		// TODO Auto-generated method stub
		//新建项目信息，对于没有主键的，产生随机EngCode
		if(engineering.getEngCode()==null || engineering.getEngCode().trim().length()==0)
		{
			Engineering existEngineering = engineeringMapper.selectEngineeringByTenantCode(engineering.getTenantCode());
			if(existEngineering!=null)
			{
				throw new ScheduleException("租户 "+engineering.getTenantCode()+" 已经配置过工程信息！");
			}
			
			int randInt = (int)(Math.random() * 1000000);  			  
		    String code = String.valueOf(randInt);
		    engineering.setEngCode(code);
		    
		    engineeringMapper.insert(engineering);
		    return code;
		}
		//更新已有项目信息
		else 
		{
			engineeringMapper.updateByPrimaryKey(engineering);
			return engineering.getEngCode();
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngineeringService#deleteEngineering(Engineering)
	 */
	public int deleteEngineering(String key) throws ScheduleException {
		// TODO Auto-generated method stub
		return engineeringMapper.deleteByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.EngineeringService#findEngineeringByTenantcode(java.lang.String)
	 */
	@Override
	public Engineering getEngineeringByTenantCode(String tenantCode) throws ScheduleException {
		// TODO Auto-generated method stub
		return engineeringMapper.selectEngineeringByTenantCode(tenantCode);
	}


}
