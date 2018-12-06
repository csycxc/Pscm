package com.banry.pscm.tender;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.ReleaseTargetMapper;
import com.banry.pscm.persist.dao.TenderPlanMapper;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.tender.ReleaseTarget;
import com.banry.pscm.service.tender.ReleaseTargetService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderPlanWithBLOBs;
import com.banry.pscm.service.util.EnumVar;

/**
 * 发布目标实现类
 * @author chenJuan
 * @date 2018-5-28
 */
@Service
public class ReleaseTargetServiceImpl implements ReleaseTargetService {
	
	@Autowired
	ReleaseTargetMapper targetMapper;
	@Autowired
	TenderPlanMapper tenderMapper;
	
	private static Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Override
	public ReleaseTarget selectByPrimaryKey(String targetCode) throws TenderPlanException {
		try {
			return targetMapper.selectByPrimaryKey(targetCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<ReleaseTarget> findAllReleaseTarget() throws TenderPlanException {
		try {
			return targetMapper.findAllReleaseTarget();
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveReleaseTarget(ReleaseTarget releaseTarget) throws TenderPlanException {
		try {
			targetMapper.insert(releaseTarget);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveReleaseTargetSelective(ReleaseTarget releaseTarget) throws TenderPlanException {
		try {
			targetMapper.insertSelective(releaseTarget);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(ReleaseTarget releaseTarget) throws TenderPlanException {
		try {
			targetMapper.updateByPrimaryKey(releaseTarget);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(ReleaseTarget releaseTarget) throws TenderPlanException {
		try {
			targetMapper.updateByPrimaryKeySelective(releaseTarget);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteByPrimaryKey(String targetCode) throws TenderPlanException {
		try {
			return targetMapper.deleteByPrimaryKey(targetCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<ReleaseTarget> findTargetBySqlWhere(String sqlWhere) throws TenderPlanException {
		try {
			return targetMapper.findTargetBySqlWhere(sqlWhere);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<ReleaseTarget> findReleaseByTenderPlanCode(String tenderPlanCode) throws TenderPlanException {
		try {
			return targetMapper.findReleaseByTenderPlanCode(tenderPlanCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveOrSubmitTarget(List<ReleaseTarget> rtList, String method, String tenderPlanCode)
			throws TenderPlanException {
		// TODO Auto-generated method stub
		//先删除原先保存的发布目标
		targetMapper.deleteByTenderPlanCode(tenderPlanCode);
		for(ReleaseTarget target : rtList){
			if(!target.getTargetName().equals("")){
				target.setReleaseDate(new Date());
				target.setTargetCode(String.valueOf((new Date()).getTime()));
				target.setTenderPlanCode(tenderPlanCode);
				targetMapper.insert(target);
			}
		}
		//如果发布则更新招标计划的状态
		if ("submit".equals(method)) {
			TenderPlanWithBLOBs tender = new TenderPlanWithBLOBs();
			tender.setTenderPlanCode(tenderPlanCode);
			EnumVar status = new EnumVar();
			status.setEnumValue(Constants.TENDER_PLAN_WF_STATUS_RELEASE);
			tender.setStatus(status);
			tenderMapper.updateByPrimaryKeySelective(tender);
		}
	}

}
