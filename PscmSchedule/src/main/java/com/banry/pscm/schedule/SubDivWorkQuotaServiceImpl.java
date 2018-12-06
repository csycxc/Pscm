/**
 * 
 */
package com.banry.pscm.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banry.pscm.persist.dao.SubDivWorkBillMapper;
import com.banry.pscm.persist.dao.SubDivWorkQuotaMapper;
import com.banry.pscm.service.conf.UUIDModel;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.schedule.SubDivWorkQuota;
import com.banry.pscm.service.schedule.SubDivWorkQuotaService;

/**
 * 分项工程定额资源
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class SubDivWorkQuotaServiceImpl implements SubDivWorkQuotaService {
	@Autowired
	SubDivWorkQuotaMapper subDivWorkQuotaMapper;
	@Autowired
	SubDivWorkBillMapper subDivWorkBillMapper;
	
	@Override
	public SubDivWorkQuota findSubDivWorkQuotaByKey(String resCode) throws ScheduleException {
		return subDivWorkQuotaMapper.selectByPrimaryKey(resCode);
	}
	@Override
	public List<SubDivWorkQuota> findSubDivWorkQuotaByDivisionSnCode(String divisionSnCode) throws ScheduleException {
		return subDivWorkQuotaMapper.findSubDivWorkQuotaByDivisionSnCode(divisionSnCode);
	}
	@Override
	public List<SubDivWorkQuota> findSubDivWorkQuotaByResourcesType(String resourcesType) throws ScheduleException {
		return subDivWorkQuotaMapper.findSubDivWorkQuotaByResourcesType(resourcesType);
	}
	@Override
	public List<SubDivWorkQuota> findSubDivWorkQuotaByResName(String resName) throws ScheduleException {
		return subDivWorkQuotaMapper.findSubDivWorkQuotaByResName(resName);
	}
	@Override
	public List<SubDivWorkQuota> findSubDivWorkQuotaByUnit(String unit) throws ScheduleException {
		return subDivWorkQuotaMapper.findSubDivWorkQuotaByUnit(unit);
	}
	@Override
	public List<SubDivWorkQuota> findAllSubDivWorkQuota() throws ScheduleException {
		return subDivWorkQuotaMapper.findAllSubDivWorkQuota();
	}
	
	@Override
	@Transactional
	public void saveSubDivWorkQuota(SubDivWorkQuota subDivWorkQuota) throws ScheduleException {
		String resCode = subDivWorkQuota.getResCode();
		if(subDivWorkQuota.getResCode() == null || "".equals(resCode) ||resCode.indexOf("jqg")==0) {
			//主键为空时  插入操作，主键设置为uuid
			subDivWorkQuota.setResCode(UUIDModel.getUUID());
			subDivWorkQuotaMapper.insert(subDivWorkQuota);
		}else {//主键不为空时  更新操作
			subDivWorkQuotaMapper.updateByPrimaryKey(subDivWorkQuota);
		}
	}
	@Override
	@Transactional
	public void saveSubDivWorkQuotaSelective(SubDivWorkQuota subDivWorkQuota) throws ScheduleException {
		if(subDivWorkQuota.getResCode() == null || "".equals(subDivWorkQuota.getResCode())) {
			//主键为null,插入操作
			subDivWorkQuota.setResCode(UUIDModel.getUUID());
			subDivWorkQuotaMapper.insertSelective(subDivWorkQuota);
		}else {//更新操作
			subDivWorkQuotaMapper.updateByPrimaryKeySelective(subDivWorkQuota);
		}
	}
	@Override
	@Transactional
	public int deleteSubDivWorkQuotaByKey(String resCode) throws ScheduleException {
		return subDivWorkQuotaMapper.deleteByPrimaryKey(resCode);
	}
	@Override
	@Transactional
	public int deleteSubDivWorkQuotaByDivisionSnCode(String divisionsncode) {
		return subDivWorkQuotaMapper.deleteSubDivWorkQuotaByDivisionSnCode(divisionsncode);
	}
	@Override
	@Transactional
	public void updateSubDivWorkQuota(SubDivWorkQuota subDivWorkQuota) throws ScheduleException {
		if(subDivWorkQuota.getResCode() == null || "".equals(subDivWorkQuota.getResCode())) 
			throw new ScheduleException("主键为null，更新失败！");
		if(subDivWorkQuota.getDivisionSnCode() == null || "".equals(subDivWorkQuota.getDivisionSnCode())) 
			throw new ScheduleException("外键为null，更新失败！");
		subDivWorkQuotaMapper.updateByPrimaryKey(subDivWorkQuota);
	}
	@Override
	@Transactional
	public void updateSubDivWorkQuotaSelective(SubDivWorkQuota subDivWorkQuota) throws ScheduleException {
		if(subDivWorkQuota.getResCode() == null || "".equals(subDivWorkQuota.getResCode())) 
			throw new ScheduleException("主键为null，更新失败！");
		subDivWorkQuotaMapper.updateByPrimaryKeySelective(subDivWorkQuota);
	}
	@Override
	public int findSubDivWorkBillStatus(String rescode) {
		return subDivWorkQuotaMapper.findSubDivWorkBillStatus(rescode);
	}
}
