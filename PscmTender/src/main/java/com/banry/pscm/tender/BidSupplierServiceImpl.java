package com.banry.pscm.tender;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.BidSupplierMapper;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.BidSupplierService;
import com.banry.pscm.service.tender.TenderPlanException;

/**
 * 投标供方（项目部）实现类
 * @author chenJuan
 * @date 2018-5-29
 */
@Service
public class BidSupplierServiceImpl implements BidSupplierService {
	@Autowired
	BidSupplierMapper bidMapper;
	
	private static Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Override
	public BidSupplier selectByPrimaryKey(String supplierBidCode) throws TenderPlanException {
		try {
			return bidMapper.selectByPrimaryKey(supplierBidCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<BidSupplier> findAllBidSupplier() throws TenderPlanException {
		try {
			return bidMapper.findAllBidSupplier();
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveBidSupplier(BidSupplier bidSupplier) throws TenderPlanException {
		try {
			bidMapper.insert(bidSupplier);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveBidSupplierSelective(BidSupplier bidSupplier) throws TenderPlanException {
		try {
			bidMapper.insertSelective(bidSupplier);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(BidSupplier bidSupplier) throws TenderPlanException {
		try {
			bidMapper.updateByPrimaryKey(bidSupplier);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(BidSupplier bidSupplier) throws TenderPlanException {
		try {
			bidMapper.updateByPrimaryKeySelective(bidSupplier);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteByPrimaryKey(String supplierBidCodes) throws TenderPlanException {
		try {
			String[] supplierBidCode = supplierBidCodes.split(",");
			int i = 0;
			for (String sb : supplierBidCode) {
				i += bidMapper.deleteByPrimaryKey(sb);
			}
			return i;
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}
	
	@Override
	public int deleteByTenderPlanCode(String tenderPlanCode) throws TenderPlanException {
		try {
			return bidMapper.deleteByTenderPlanCode(tenderPlanCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<BidSupplier> findBidSupplierBySqlWhere(Map sqlWhere) throws TenderPlanException {
		try {
			return bidMapper.findBidSupplierBySqlWhere(sqlWhere);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveBidSupplier(List<BidSupplier> bidSupplier, String tenderPlanCode) throws TenderPlanException {
		try {
			for (BidSupplier bidSup : bidSupplier) {
				BidSupplier bs = bidMapper.selectByPrimaryKey(bidSup.getSupplierBidCode());
				if (bs != null) {
					bidMapper.updateByPrimaryKeySelective(bidSup);
				} else {
					bidSup.setTenderPlanCode(tenderPlanCode);
					bidSup.setStatus(0);
					bidMapper.insertSelective(bidSup);
				}
			}
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public BidSupplier selectBySupplierCreditCodeAndTenderPlanCode(Map map) throws TenderPlanException {
		try {
			return bidMapper.selectBySupplierCreditCodeAndTenderPlanCode(map);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public BidSupplier findBidSupplierByChangeCode(String tenderResultIdChangeCode) throws TenderPlanException {
		try {
			return bidMapper.findBidSupplierByChangeCode(tenderResultIdChangeCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<BidSupplier> findAllBidSupplierList() throws TenderPlanException {
		try {
			//排除起草和流程退回终止状态
			String sqlWhere = "status <> " + Constants.WF_STATUS_DRAFT + " and status <> " + Constants.WF_STATUS_RETURN;
			return bidMapper.findAllBidSupplierList(sqlWhere);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public BidSupplier findBidSupplierByTenderResultCode(String tenderResultCode) throws TenderPlanException {
		try {
			return bidMapper.findBidSupplierByTenderResultCode(tenderResultCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

}
