package com.banry.pscm.tender;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banry.pscm.persist.dao.BidSupplierMapper;
import com.banry.pscm.persist.dao.SubDivWorkBillMapper;
import com.banry.pscm.persist.dao.TenderPlanMapper;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.TenderPlan;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderPlanService;
import com.banry.pscm.service.tender.TenderPlanWithBLOBs;

/**
 * 招标计划实现类
 * @author chenJuan
 * @date 2018-5-29
 */
@Service
public class TenderPlanServiceImpl implements TenderPlanService {
	
	@Autowired
	TenderPlanMapper tenderMapper;
	@Autowired
	SubDivWorkBillMapper subDivWorkBillMapper;
	@Autowired
	BidSupplierMapper bidSupplierMapper;
	
	private static Logger log = LoggerFactory.getLogger(TenderPlanServiceImpl.class);
	SimpleDateFormat myFmt = new SimpleDateFormat("yyMMddHHmmss");

	@Override
	public TenderPlanWithBLOBs selectByPrimaryKey(Map map) throws TenderPlanException {
		try {
			return tenderMapper.selectByPrimaryKey(map);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<TenderPlanWithBLOBs> findAll(String parentTenantAccount) throws TenderPlanException {
		try {
			return tenderMapper.findAll(parentTenantAccount);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveTenderPlan(TenderPlanWithBLOBs tenderPlanWithBLOBS) throws TenderPlanException {
		try {
			tenderPlanWithBLOBS.setTenderPlanCode(myFmt.format(new Date()));
			tenderMapper.insert(tenderPlanWithBLOBS);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveTenderPlanSelective(TenderPlanWithBLOBs tenderPlanWithBLOBS, List<BidSupplier> supplierList) throws TenderPlanException {
		try {
			tenderPlanWithBLOBS.setTenderPlanCode(myFmt.format(new Date()));
			tenderMapper.insertSelective(tenderPlanWithBLOBS);
			for (BidSupplier s : supplierList) {
				s.setTenderPlanCode(tenderPlanWithBLOBS.getTenderPlanCode());
				s.setSupplierBidCode(String.valueOf((new Date()).getTime()));
				s.setComeFrom("0");
				s.setStatus(0);
				bidSupplierMapper.insertSelective(s);
			}
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(TenderPlan tenderPlan) throws TenderPlanException {
		try {
			tenderMapper.updateByPrimaryKey(tenderPlan);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeyWithBLOBS(TenderPlanWithBLOBs record, List<BidSupplier> supplierList) throws TenderPlanException {
		try {
			//删除招标计划关联的供方信息
			bidSupplierMapper.deleteByTenderPlanCode(record.getTenderPlanCode());
			for (BidSupplier s : supplierList) {
				s.setTenderPlanCode(record.getTenderPlanCode());
				s.setSupplierBidCode(String.valueOf((new Date()).getTime()));
				s.setComeFrom("0");
				s.setStatus(0);
				bidSupplierMapper.insertSelective(s);
			}
			tenderMapper.updateByPrimaryKeyWithBLOBs(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(TenderPlanWithBLOBs record) throws TenderPlanException {
		try {
			tenderMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public int deleteByPrimaryKey(String tenderPlanCode) throws TenderPlanException {
		try {
			List<SubDivWorkBill> subList = subDivWorkBillMapper.findWorkBillByTenderPlanCode(tenderPlanCode);
			for (SubDivWorkBill sub : subList) {
				sub.setTenderPlanCode(null);
				subDivWorkBillMapper.updateByPrimaryKey(sub);
			}
			return tenderMapper.deleteByPrimaryKey(tenderPlanCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<TenderPlanWithBLOBs> findTenderPlanBySqlWhere(String sqlWhere) throws TenderPlanException {
		try {
			return tenderMapper.findTenderPlanBySqlWhere(sqlWhere);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<TenderPlan> findTenderPlanRelease() throws TenderPlanException {
		try {
			// TODO Auto-generated method stub
			return tenderMapper.findTenderPlanRelease(Constants.TENDER_PLAN_WF_STATUS_RELEASE);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

}
