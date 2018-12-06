/**
 * 
 */
package com.banry.pscm.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banry.pscm.persist.dao.HazardsMapper;
import com.banry.pscm.service.conf.UUIDModel;
import com.banry.pscm.service.schedule.Hazards;
import com.banry.pscm.service.schedule.HazardsService;
import com.banry.pscm.service.schedule.ScheduleException;

/**
 * @author Xu Dingkui
 * @date 2018年7月4日
 */
@Service
public class HazardsServiceImpl implements HazardsService {
	@Autowired
	HazardsMapper hazardsMapper;
	
	private static Logger log = LoggerFactory.getLogger(HazardsServiceImpl.class);

	@Override
	public Hazards findHazardsByKey(String hazardsCode) throws ScheduleException {
		return hazardsMapper.selectByPrimaryKey(hazardsCode);
	}
	@Override
	public List<Hazards> findHazardssByDivItemCode(String divItemCode) throws ScheduleException {
		return hazardsMapper.findHazardssByDivItemCode(divItemCode);
	}
	@Override
	public List<Hazards> findHazardssByHazardsFactors(String hazardsFactors) throws ScheduleException {
		return hazardsMapper.findHazardssByHazardsFactors(hazardsFactors);
	}
	@Override
	public List<Hazards> findAllHazards() throws ScheduleException {
		return hazardsMapper.findAllHazards();
	}
	
	@Override
	@Transactional
	public void saveHazards(Hazards hazards) throws ScheduleException {
		String hazardsCode = hazards.getHazardsCode();
		if(hazardsCode == null || "".equals(hazardsCode) || hazardsCode.indexOf("jqg")==0 ) {
			//插入
			hazards.setHazardsCode(UUIDModel.getUUID());
			hazardsMapper.insert(hazards);
		}else {
			//更新
			hazardsMapper.updateByPrimaryKey(hazards);
		}
	}
	@Override
	@Transactional
	public void saveHazardsSelective(Hazards hazards) throws ScheduleException {
		if(hazards.getHazardsCode() == null || "".equals(hazards.getHazardsCode())) {
			hazards.setHazardsCode(UUIDModel.getUUID());
			hazardsMapper.insertSelective(hazards);
		}else {
			hazardsMapper.updateByPrimaryKeySelective(hazards);
		}
	}
	@Override
	@Transactional
	public int deleteHazardsByKey(String hazardsCode) throws ScheduleException {
		return hazardsMapper.deleteByPrimaryKey(hazardsCode);
	}
	@Override
	@Transactional
	public void updateHazards(Hazards hazards) throws ScheduleException {
		if(hazards.getHazardsCode() == null || "".equals(hazards.getHazardsCode())) {
			throw new ScheduleException("主键HazardsCode不能为null,更新失败！");
		}
		if(hazards.getDivItemCode() == null || "".equals(hazards.getDivItemCode())) {
			throw new ScheduleException("外键divitemcode不能为null,更新失败！");
		}
		hazardsMapper.updateByPrimaryKey(hazards);
	}
	@Override
	@Transactional
	public void updateHazardsSelective(Hazards hazards) throws ScheduleException {
		if(hazards.getHazardsCode() == null || "".equals(hazards.getHazardsCode())) 
			throw new ScheduleException("主键HazardsCode不能为null,更新失败！");
		hazardsMapper.updateByPrimaryKeySelective(hazards);
	}
	
	@Override
	public List<Hazards> findHazardssByDivSnCode(String divSnCode) throws ScheduleException {
		return hazardsMapper.findHazardssByDivSnCode(divSnCode);		
	}
	
	/**
	 * 根据divisionsncode 查找divItemCode,再根据DivItemCode删除危险源
	 */
	@Override
	public int deleteHazardsByDivisionSnCode(String divisionsncode) {
		return hazardsMapper.deleteHazardsByDivisionSnCode(divisionsncode);
	}
}
