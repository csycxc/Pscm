package com.banry.pscm.tender;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.banry.pscm.persist.dao.BidSupplierMapper;
import com.banry.pscm.persist.dao.TenderResultChangeMapper;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderResultChange;
import com.banry.pscm.service.tender.TenderResultChangeService;

/**
 * 招标结果变更实现类
 * @author chenJuan
 * @date 2018-5-30
 */
@Service
public class TenderResultChangeServiceImpl implements TenderResultChangeService {
	
	@Autowired
	TenderResultChangeMapper changeMapper;
	@Autowired
	BidSupplierMapper bidSupplierMapper;
	
	private static Logger log = LoggerFactory.getLogger(TenderResultChangeServiceImpl.class);

	@Override
	public TenderResultChange selectByPrimaryKey(Map map) throws TenderPlanException {
		try {
			return changeMapper.selectByPrimaryKey(map);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<TenderResultChange> findAllChange(String parentTenantAccount) throws TenderPlanException {
		try {
			return changeMapper.findAllChange(parentTenantAccount);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveChange(TenderResultChange record) throws TenderPlanException {
		try {
			changeMapper.insert(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveChangeSelective(TenderResultChange record) throws TenderPlanException {
		try {
			changeMapper.insertSelective(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(TenderResultChange record) throws TenderPlanException {
		try {
			changeMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(TenderResultChange record) throws TenderPlanException {
		try {
			changeMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteByPrimaryKey(String tenderResultIdChangeCode) throws TenderPlanException {
		try {
			return changeMapper.deleteByPrimaryKey(tenderResultIdChangeCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public TenderResultChange selectNoFinishByResultCode(String resultCode) throws TenderPlanException {
		try {
			return changeMapper.selectNoFinishByResultCode(resultCode, Constants.WF_STATUS_FINISH, Constants.WF_STATUS_RETURN);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}
	
	@Override
	public TenderResultChange selectApprovalByResultCode(String resultCode) throws TenderPlanException {
		try {
			return changeMapper.selectApprovalByResultCode(resultCode, Constants.WF_STATUS_FINISH);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	@Transactional
	public void updateChangeToSubDivWorkBill(String tenderResultIdChangeCode) throws TenderPlanException {
		try {
			//查询中标单位
			BidSupplier bid = bidSupplierMapper.findBidSupplierByChangeCode(tenderResultIdChangeCode);
			changeMapper.updateChangeToSubDivWorkBill(bid.getSupplierCreditCode().getSupplierCreditCode(), tenderResultIdChangeCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

}
