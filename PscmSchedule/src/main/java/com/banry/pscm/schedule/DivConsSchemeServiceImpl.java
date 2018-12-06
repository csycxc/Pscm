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

import com.banry.pscm.persist.dao.DivConsSchemeMapper;
import com.banry.pscm.persist.dao.EngDivisionMapper;
import com.banry.pscm.service.conf.UUIDModel;
import com.banry.pscm.service.schedule.DivConsScheme;
import com.banry.pscm.service.schedule.DivConsSchemeService;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.ScheduleException;

/**
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class DivConsSchemeServiceImpl implements DivConsSchemeService {
	@Autowired
	DivConsSchemeMapper divConsSchemeMapper;
	@Autowired
	EngDivisionMapper engDivisionMapper;
	
	private static Logger log = LoggerFactory.getLogger(DivConsSchemeServiceImpl.class);
	
	/* (non-Javadoc)
	 * @see com.banry.pscm.service.schedule.ConsSchemeService#findConsSchemeByPrimaryKey(ConsScheme)
	 */
	public DivConsScheme findDivConsSchemeByPrimaryKey(String schemeCode) throws ScheduleException {
		// TODO Auto-generated method stub
		log.info("DivConsSchemeServiceImpl:findDivConsSchemeByKey call with schemeCode="+schemeCode);
		return divConsSchemeMapper.selectByPrimaryKey(schemeCode);
	}

	@Override
	public List<DivConsScheme> findDivConsSchemesByDivisionSnCode(String divisionSnCode) throws ScheduleException {
		log.info("DivConsSchemeServiceImpl : findDivConsSchemesByDivisionSnCode call with divisionSnCode="+divisionSnCode);
		return divConsSchemeMapper.findDivConsSchemesByDivisionSnCode(divisionSnCode);
	}
	@Override
	public List<DivConsScheme> findDivConsSchemesByName(String name) throws ScheduleException {
		log.info("DivConsSchemeServiceImpl : findDivConsSchemesByName call with name="+name);
		return divConsSchemeMapper.findDivConsSchemesByName(name);//模糊查询
	}
	@Override
	public List<DivConsScheme> findAllDivConsScheme() throws ScheduleException {
		log.info("DivConsSchemeServiceImpl : findAllDivConsScheme start...");
		return divConsSchemeMapper.findAllDivConsScheme();
	}
	
	@Override
	@Transactional
	public void saveDivConsScheme(DivConsScheme divConsScheme) throws ScheduleException {
		log.info("DivConsSchemeServiceImpl : saveDivConsScheme call with SchemeCode="+divConsScheme.getSchemeCode());
		if(divConsScheme.getSchemeCode() == null || "".equals(divConsScheme.getSchemeCode())) { //插入
			divConsScheme.setSchemeCode(UUIDModel.getUUID());
			log.debug("saveDivConsScheme:insert call with schemeCode="+divConsScheme.getSchemeCode());
			divConsSchemeMapper.insert(divConsScheme);
		}else { //更新			
			log.debug("saveDivConsScheme:update call with schemeCode="+divConsScheme.getSchemeCode());
			divConsSchemeMapper.updateByPrimaryKey(divConsScheme);
		}
	}
	@Override
	@Transactional
	public void saveDivConsSchemeSelective(DivConsScheme divConsScheme) throws ScheduleException {
		log.info("DivConsSchemeServiceImpl : saveDivConsSchemeSelective call with SchemeCode="+divConsScheme.getSchemeCode());
		if(divConsScheme.getSchemeCode() == null || "".equals(divConsScheme.getSchemeCode())) {
			throw new ScheduleException("主键schemeCode不能为空！");
		}
		DivConsScheme dcs = divConsSchemeMapper.selectByPrimaryKey(divConsScheme.getSchemeCode());
		if(dcs != null) {//更新
			if(divConsScheme.getDivisionSnCode() != null && !"".equals(divConsScheme.getDivisionSnCode())) {//如果外键不为空
				EngDivision ed = engDivisionMapper.selectByPrimaryKey(divConsScheme.getDivisionSnCode());//查询外键在数据库中是否存在
				if(ed != null) {//如果外键正确
					divConsScheme.setDivLevel(""+ed.getDivLevel());
					log.info("saveDivConsSchemeSelective:updateSelective call with schemeCode="+divConsScheme.getSchemeCode());
					divConsSchemeMapper.updateByPrimaryKeySelective(divConsScheme);
				}else {
					throw new ScheduleException("传入的外键DivisionSnCode数据库中不存在，保存失败！");
				}
			}else {
				divConsScheme.setDivLevel(null);
				log.info("saveDivConsSchemeSelective:updateSelective call with schemeCode="+divConsScheme.getSchemeCode());
				divConsSchemeMapper.updateByPrimaryKeySelective(divConsScheme);
			}
		}else {//插入
			if(divConsScheme.getDivisionSnCode() == null || "".equals(divConsScheme.getDivisionSnCode())) {
				throw new ScheduleException("外键divisionSnCode不能为空！");
			}
			EngDivision ed = engDivisionMapper.selectByPrimaryKey(divConsScheme.getDivisionSnCode());//查询外键在数据库中是否存在
			if(ed != null) {//外键可用
				divConsScheme.setDivLevel(""+ed.getDivLevel());
				log.info("saveDivConsSchemeSelective:insertSelective call with schemeCode="+divConsScheme.getSchemeCode());
				divConsSchemeMapper.insert(divConsScheme);
			}
		}
	}
	@Override
	@Transactional
	public int deleteDivConsSchemeByKey(String schemeCode) throws ScheduleException {
		log.info("service:deleteDivConsSchemeByKey call with schemeCode="+schemeCode);
		return divConsSchemeMapper.deleteByPrimaryKey(schemeCode);
	}
	@Override
	@Transactional
	public void updateDivConsScheme(DivConsScheme divConsScheme) throws ScheduleException {
		log.info("DivConsSchemeServiceImpl : updateDivConsScheme call with schemeCode="+divConsScheme.getSchemeCode());
		if(divConsScheme.getSchemeCode() == null || "".equals(divConsScheme.getSchemeCode())) {
			log.error("updateDivConsScheme fail with divCodsScheme=null...");
			throw new ScheduleException("主键schemeCode不能为空！");
		}
		divConsSchemeMapper.updateByPrimaryKey(divConsScheme);
	}
	@Override
	@Transactional
	public void updateDivConsSchemeSelective(DivConsScheme divConsScheme) throws ScheduleException {
		log.info("DivConsSchemeServiceImpl : updateDivConsSchemeSelective call with schemeCode="+divConsScheme.getSchemeCode());
		if(divConsScheme.getSchemeCode() == null || "".equals(divConsScheme.getSchemeCode())) {
			log.error("updateDivConsSchemeSelective fail with divConsScheme=null...");
			throw new ScheduleException("主键schemeCode不能为空！");
		}
		divConsSchemeMapper.updateByPrimaryKeySelective(divConsScheme);
	}
}
