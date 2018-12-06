/**
 * 
 */
package com.banry.pscm.schedule;

import com.banry.pscm.persist.dao.SubDivWorkBillMapper;
import com.banry.pscm.service.contract.DownContract;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.schedule.SubDivWorkBillChange;
import com.banry.pscm.service.schedule.SubDivWorkBillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 分项工程清单
 * @author Xu Dingkui
 * @date 2017年6月15日
 */
@Service
public class SubDivWorkBillServiceImpl implements SubDivWorkBillService {
	@Autowired
	SubDivWorkBillMapper subDivWorkBillMapper;
	
	private static Logger log = LoggerFactory.getLogger(SubDivWorkBillServiceImpl.class);
	
	@Override
	public SubDivWorkBill findSubDivWorkBillByKey(String divisionSnCode) throws ScheduleException {
		return subDivWorkBillMapper.selectByPrimaryKey(divisionSnCode);
	}
	@Override
	public List<SubDivWorkBill> findSubDivWorkBillsByName(String name) throws ScheduleException {
		return subDivWorkBillMapper.findSubDivWorkBillsByName(name);
	}
	@Override
	public List<SubDivWorkBill> findAllSubDivWorkBill() throws ScheduleException {
		return subDivWorkBillMapper.findAllSubDivWorkBill();
	}
	@Override
	public List<SubDivWorkBill> findSubDivWorkBillsByParentDivisionSnCode(String parentCode) throws ScheduleException {
		return subDivWorkBillMapper.findSubDivWorkBillsByParentDivisionSnCode(parentCode);
	}
	@Override
	public int findSubDivWorkBillStatusByDivisionSnCode(String divisionsncode) {
		return subDivWorkBillMapper.findSubDivWorkBillStatusByDivisionSnCode(divisionsncode);
	}
	
	@Override
	@Transactional
	public void saveSubDivWorkBill(SubDivWorkBill subDivWorkBill) throws ScheduleException {
		SubDivWorkBill sdwb = subDivWorkBillMapper.selectByPrimaryKey(subDivWorkBill.getDivisionSnCode().getDivisionSnCode());
		if(sdwb != null) {//更新
			subDivWorkBillMapper.updateByPrimaryKey(subDivWorkBill);
		}else {//插入
			subDivWorkBillMapper.insert(subDivWorkBill);
		}
	}
	@Override
	@Transactional
	public void saveSubDivWorkBillSelective(SubDivWorkBill subDivWorkBill) throws ScheduleException {
		SubDivWorkBill sdwb = subDivWorkBillMapper.selectByPrimaryKey(subDivWorkBill.getDivisionSnCode().getDivisionSnCode());
		if(sdwb != null) {//更新
			System.out.println("更新：");
			subDivWorkBillMapper.updateByPrimaryKeySelective(subDivWorkBill);
		}else {//插入
			System.out.println("插入");
			subDivWorkBillMapper.insertSelective(subDivWorkBill);
		}
	}
	@Override
	@Transactional
	public int deleteSubDivWorkBillByKey(String divisionSnCode) throws ScheduleException {
		return subDivWorkBillMapper.deleteByPrimaryKey(divisionSnCode);
	}
	@Override
	@Transactional
	public void updateSubDivWorkBill(SubDivWorkBill subDivWorkBill) throws ScheduleException {
		if(subDivWorkBill.getDivisionSnCode() == null || "".equals(subDivWorkBill.getDivisionSnCode().getDivisionSnCode())) {
			throw new ScheduleException("主键为null,更新失败！");
		}
		subDivWorkBillMapper.updateByPrimaryKey(subDivWorkBill);
	}
	@Override
	@Transactional
	public void updateSubDivWorkBillSelective(SubDivWorkBill subDivWorkBill) throws ScheduleException {
		if(subDivWorkBill.getDivisionSnCode() == null || "".equals(subDivWorkBill.getDivisionSnCode().getDivisionSnCode())) {
			throw new ScheduleException("主键为null,更新失败！");
		}
		subDivWorkBillMapper.updateByPrimaryKeySelective(subDivWorkBill);
	}
	
	/**
	 * 划分工程清单信息提交
	 */
	@Override
	public int updateSubDivWorkBillStatusSubmit(String divisionsncode) {
		return subDivWorkBillMapper.updateSubDivWorkBillStatusSubmit(divisionsncode);
	}

	@Override
	public int addSubDivWorkBillToContract(DownContract downContract) {
		return subDivWorkBillMapper.addSubDivWorkBillToContract(downContract);
	}

	@Override
	public List<SubDivWorkBill> findSubBySqlWhere(String sqlWhere) throws ScheduleException {
		try {
			return subDivWorkBillMapper.findSubBySqlWhere(sqlWhere);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new ScheduleException(e.getMessage(),e);
		}
	}
	
	@Override
	public List<SubDivWorkBill> findWorkBillByTenderPlanCode(String tenderPlanCode) throws ScheduleException {
		try {
			return subDivWorkBillMapper.findWorkBillByTenderPlanCode(tenderPlanCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new ScheduleException(e.getMessage(),e);
		}
	}

    @Override
    public List<SubDivWorkBillChange> findWorkBillChangeByContractCode(String contractCode) {
        return subDivWorkBillMapper.findWorkBillChangeByContractCode(contractCode);
    }

	@Override
	public boolean checkSubDivWorkBill(String divisionSnCode) {
		List<Map<String, Object>> list = subDivWorkBillMapper.findWorkBillsByDivisionSnCode(divisionSnCode);
		return list == null || list.isEmpty();
	}

    @Override
    public void clearContractCode(String contractCode) {
        subDivWorkBillMapper.clearContractCode(contractCode);
    }
}
