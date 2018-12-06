package com.banry.pscm.tender;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.BidSupplierMapper;
import com.banry.pscm.persist.dao.SupplierBidItemRateMapper;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.SupplierBidItemRate;
import com.banry.pscm.service.tender.SupplierBidItemRateService;
import com.banry.pscm.service.tender.TenderPlanException;

/**
 * 供方投标清单报价实现类
 * @author chenJuan
 * @date 2018-5-29
 */
@Service
public class SupplierBidItemRateServiceImpl implements SupplierBidItemRateService {
	
	@Autowired
	SupplierBidItemRateMapper rateMapper;
	@Autowired
	BidSupplierMapper bidSupplierMapper;
	
	private static Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Override
	public SupplierBidItemRate selectByPrimaryKey(String itemBidCode) throws TenderPlanException {
		try {
			return rateMapper.selectByPrimaryKey(itemBidCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<SupplierBidItemRate> findAllSupplierBidItemRate() throws TenderPlanException {
		try {
			return rateMapper.findAllSupplierBidItemRate();
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveSupplierBidItemRate(SupplierBidItemRate supplierBidItemRate) throws TenderPlanException {
		try {
			rateMapper.insert(supplierBidItemRate);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveSupplierBidItemRateSelective(SupplierBidItemRate supplierBidItemRate) throws TenderPlanException {
		try {
			rateMapper.insertSelective(supplierBidItemRate);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(SupplierBidItemRate supplierBidItemRate) throws TenderPlanException {
		try {
			rateMapper.updateByPrimaryKey(supplierBidItemRate);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(SupplierBidItemRate supplierBidItemRate) throws TenderPlanException {
		try {
			rateMapper.updateByPrimaryKeySelective(supplierBidItemRate);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteByPrimaryKey(String itemBidCode) throws TenderPlanException {
		try {
			return rateMapper.deleteByPrimaryKey(itemBidCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<SupplierBidItemRate> findRateBySqlWhere(String sqlWhere) throws TenderPlanException {
		try {
			return rateMapper.findRateBySqlWhere(sqlWhere);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public SupplierBidItemRate findSupplierBidItemRateByDivCode(String divisionSnCode) throws TenderPlanException {
		try {
			return rateMapper.findSupplierBidItemRateByDivCode(divisionSnCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<SupplierBidItemRate> getSupBidItemRateByTpCodeAndSupCode(Map map) throws TenderPlanException {
		try {
			return rateMapper.getSupBidItemRateByTpCodeAndSupCode(map);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveSupplierBidItemRate(List<SupplierBidItemRate> itemList, String supplierCreditCode, BidSupplier bidSupplier) throws TenderPlanException {
		// TODO Auto-generated method stub
		try {
			//总报价
			double total = 0;
			for (SupplierBidItemRate item : itemList) {
				total += item.getDivisionSnCode().getRawConMapQuan() * item.getFirstBidUnitRate();
				if (item.getItemBidCode() != null && !"".equals(item.getItemBidCode())) {
					rateMapper.updateByPrimaryKey(item);
				} else {
					item.setItemBidCode(String.valueOf((new Date()).getTime()));
					item.setSupplierCreditCode(supplierCreditCode);
					rateMapper.insert(item);
				}
			}
			//更新供方的一次报价
			bidSupplier.setFirstQuote(total);
			bidSupplierMapper.updateByPrimaryKeySelective(bidSupplier);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<SupplierBidItemRate> findSupplierBidItemRateByTenderPlanCode(String tenderPlanCode)
			throws TenderPlanException {
		try {
			return rateMapper.findSupplierBidItemRateByTenderPlanCode(tenderPlanCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public SupplierBidItemRate findSupplierBidItemRateByDivCodeAndSupplierCode(String divisionSnCode,
			String supplierCreditCode) throws TenderPlanException {
		try {
			return rateMapper.findSupplierBidItemRateByDivCodeAndSupplierCode(divisionSnCode, supplierCreditCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<SupplierBidItemRate> getMoveInOrChangeSupplierSupBidItemRateByChangeCode(String changeCode, String changeSupplier) throws TenderPlanException {
		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("status", Constants.WF_STATUS_FINISH.toString());
			map.put("changeCode", changeCode);
			map.put("changeSupplier", changeSupplier);
			return rateMapper.getMoveInOrChangeSupplierSupBidItemRateByChangeCode(map);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

}
