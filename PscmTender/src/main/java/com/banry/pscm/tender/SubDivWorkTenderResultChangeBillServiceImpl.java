package com.banry.pscm.tender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.SubDivWorkTenderResultChangeBillMapper;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBill;
import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBillKey;
import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBillService;
import com.banry.pscm.service.tender.TenderPlanException;

/**
 * 分项工程招标结果变更清单
 * @author xudk
 * @date 2018-9-28
 */
@Service
public class SubDivWorkTenderResultChangeBillServiceImpl implements SubDivWorkTenderResultChangeBillService {
	
	@Autowired
	SubDivWorkTenderResultChangeBillMapper billMapper;
	
	private static Logger log = LoggerFactory.getLogger(SubDivWorkTenderResultChangeBillServiceImpl.class);

	@Override
	public SubDivWorkTenderResultChangeBill selectByPrimaryKey(SubDivWorkTenderResultChangeBillKey key)
			throws TenderPlanException {
		try {
			return billMapper.selectByPrimaryKey(key);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveSubDivWorkTenderResultChangeBill(SubDivWorkTenderResultChangeBill bill) throws TenderPlanException {
		try {
			billMapper.insert(bill);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveSubDivWorkTenderResultChangeBillSelective(SubDivWorkTenderResultChangeBill bill)
			throws TenderPlanException {
		try {
			billMapper.insertSelective(bill);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(SubDivWorkTenderResultChangeBill bill) throws TenderPlanException {
		try {
			billMapper.updateByPrimaryKey(bill);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(SubDivWorkTenderResultChangeBill bill) throws TenderPlanException {
		try {
			billMapper.updateByPrimaryKeySelective(bill);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteByPrimaryKey(SubDivWorkTenderResultChangeBillKey key) throws TenderPlanException {
		try {
			return billMapper.deleteByPrimaryKey(key);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<SubDivWorkTenderResultChangeBill> findSubDivWorkTenderResultChangeBillByChangeCode(Map map)
			throws TenderPlanException {
		try {
			return billMapper.findSubDivWorkTenderResultChangeBillByChangeCode(map);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public SubDivWorkTenderResultChangeBill findMoveInResultChangeBillByDivisionSnCode(String divisionSnCode)
			throws TenderPlanException {
		try {
			Map map = new HashMap();
			map.put("divisionSnCode", divisionSnCode);
			map.put("status", Constants.WF_STATUS_RETURN);
			return billMapper.findMoveInResultChangeBillByDivisionSnCode(map);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}


}
