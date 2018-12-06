/**
 * 
 */
package com.banry.pscm.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.ContractAttMapper;
import com.banry.pscm.persist.dao.TechDisclosureMapper;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.schedule.TechDisclosure;
import com.banry.pscm.service.schedule.TechDisclosureService;
import com.banry.pscm.service.util.ContractAttService;

/**
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class TechDisclosureServiceImpl implements TechDisclosureService {
	@Autowired
	TechDisclosureMapper techDisclosureMapper;
	@Autowired
	ContractAttMapper contractAttMapper;
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.TechDisclosureService#findTechDisclosureByPrimaryKey(TechDisclosure)
	 */
	public TechDisclosure findTechDisclosureByPrimaryKey(String key) throws ScheduleException {
		// TODO Auto-generated method stub
		return techDisclosureMapper.selectByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.TechDisclosureService#saveTechDisclosure(com.banry.pscm.service.schedule.TechDisclosure)
	 */
	public void saveTechDisclosure(TechDisclosure techDisclosure) throws ScheduleException {
		// TODO Auto-generated method stub
		TechDisclosure entity = techDisclosureMapper.selectByPrimaryKey(techDisclosure.getDisclosureCode());
		if (entity != null) {
			techDisclosureMapper.updateByPrimaryKeySelective(techDisclosure);
		} else {
			techDisclosureMapper.insertSelective(techDisclosure);
		}
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.TechDisclosureService#paging(java.lang.String, int, int)
	 */
	public List<TechDisclosure> findTechDisclosureBySqlWhere(String sqlWhere) throws ScheduleException {
		// TODO Auto-generated method stub
		return techDisclosureMapper.findTechDisclosureBySqlWhere(sqlWhere);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.TechDisclosureService#deleteTechDisclosure(TechDisclosure)
	 */
	public int deleteTechDisclosure(String key) throws ScheduleException {
		// TODO Auto-generated method stub
		return techDisclosureMapper.deleteByPrimaryKey(key);
	}

	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.TechDisclosureService#findTechDisclosureByDisclo(java.lang.String)
	 */
	@Override
	public List<TechDisclosure> findTechDisclosureByDisclo(String disclo) throws ScheduleException {
		// TODO Auto-generated method stub
		return techDisclosureMapper.findTechDisclosureByDisclo(disclo);
	}
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.TechDisclosureService#findTechDisclosureByDisclo(java.lang.String)
	 */
	@Override
	public List<TechDisclosure> findHisTechDisclosureByDisclo(String disclo) throws ScheduleException {
		// TODO Auto-generated method stub
		return techDisclosureMapper.findHisTechDisclosureByDisclo(disclo);
	}
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.TechDisclosureService#findTechDisclosureByDisDivCodeDisclo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<TechDisclosure> findTechDisclosureByDisDivSnCode(String disDivCode)
			throws ScheduleException {
		// TODO Auto-generated method stub
		return techDisclosureMapper.findTechDisclosureByDisDivSnCode(disDivCode);
	}

	@Override
	public int deleteContractAtt(String disclosureCode, String fileInName) throws ScheduleException {
		// TODO Auto-generated method stub
		//删除附件表记录
		contractAttMapper.deleteByPrimaryKey(fileInName);
		//更新附件字段
		TechDisclosure entity = techDisclosureMapper.selectByPrimaryKey(disclosureCode);
		if (entity.getDisAttach().indexOf("," + fileInName) > -1) {
			entity.setDisAttach(entity.getDisAttach().replaceAll("," + fileInName, ""));
		} else {
			entity.setDisAttach(entity.getDisAttach().replaceAll(fileInName, ""));
		}
		techDisclosureMapper.updateByPrimaryKeySelective(entity);
		return 0;
	}

}
