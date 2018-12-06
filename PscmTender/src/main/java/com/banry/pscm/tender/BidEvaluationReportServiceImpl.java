package com.banry.pscm.tender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banry.pscm.persist.dao.BidEvaluationReportMapper;
import com.banry.pscm.persist.dao.BidSupplierMapper;
import com.banry.pscm.service.tender.BidEvaluationReport;
import com.banry.pscm.service.tender.BidEvaluationReportService;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.TenderPlanException;

/**
 * 招标结果（评标报告）实现类
 * @author chenJuan
 * @date 2018-5-29
 */
@Service
public class BidEvaluationReportServiceImpl implements BidEvaluationReportService {
	@Autowired
	BidEvaluationReportMapper reportMapper;
	@Autowired
	BidSupplierMapper bidMapper;
	
	private static Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Override
	public BidEvaluationReport selectByPrimaryKey(Map map) throws TenderPlanException {
		try {
			return reportMapper.selectByPrimaryKey(map);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<BidEvaluationReport> findAllReport(String parentTenantAccount) throws TenderPlanException {
		try {
			return reportMapper.findAllReport(parentTenantAccount);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveBidEvaluationReport(BidEvaluationReport bidEvaluationReport) throws TenderPlanException {
		try {
			reportMapper.insert(bidEvaluationReport);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveBidEvaluationReportSelective(BidEvaluationReport bidEvaluationReport, List<BidSupplier> supplierList) throws TenderPlanException {
		try {
			for (BidSupplier bidSup : supplierList) {
				BidSupplier bs = bidMapper.selectByPrimaryKey(bidSup.getSupplierBidCode());
				if (bs != null) {
					bidMapper.updateByPrimaryKeySelective(bidSup);
				}
			}
			reportMapper.insertSelective(bidEvaluationReport);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(BidEvaluationReport record) throws TenderPlanException {
		try {
			reportMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public void updateByPrimaryKeyWithBLOBs(BidEvaluationReport record) throws TenderPlanException {
		try {
			
			reportMapper.updateByPrimaryKeyWithBLOBs(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(BidEvaluationReport record, List<BidSupplier> supplierList) throws TenderPlanException {
		try {
			for (BidSupplier bidSup : supplierList) {
				BidSupplier bs = bidMapper.selectByPrimaryKey(bidSup.getSupplierBidCode());
				if (bs != null) {
					bidMapper.updateByPrimaryKeySelective(bidSup);
				}
			}
			reportMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteByPrimaryKey(String bidResultCode) throws TenderPlanException {
		try {
			return reportMapper.deleteByPrimaryKey(bidResultCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<BidEvaluationReport> findReportBySqlWhere(String sqlWhere) throws TenderPlanException {
		try {
			return reportMapper.findReportBySqlWhere(sqlWhere);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<BidEvaluationReport> findApprovalFinishReport() {
		try {
			return reportMapper.findApprovalFinishReport();
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateBidResultCodeBidSupplierPriceToSubDivWorkBill(String bidResultCode) throws TenderPlanException {
		try {
			//查询中标供方
			BidSupplier bidSup = bidMapper.findBidSupplierByTenderResultCode(bidResultCode);
			if (bidSup != null) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("supplierCreditCode", bidSup.getSupplierCreditCode().getSupplierCreditCode());
				map.put("tenderResultCode", bidResultCode);
				reportMapper.updateBidResultCodeBidSupplierPriceToSubDivWorkBill(map);
			} else {
				log.error("没有找到中标供方");
				throw new TenderPlanException("没有找到中标供方");
			}
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

}
