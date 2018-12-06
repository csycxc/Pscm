package com.banry.pscm.tender;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.SupplierBidRateMapper;
import com.banry.pscm.service.tender.SupplierBidRate;
import com.banry.pscm.service.tender.SupplierBidRateService;
import com.banry.pscm.service.tender.TenderPlanException;

/**
 * 临时供方投标清单报价实现类
 * @author xudingkui
 * @date 2018-8-3
 */
@Service
public class SupplierBidRateServiceImpl implements SupplierBidRateService {
	
	@Autowired
	SupplierBidRateMapper rateMapper;
	
	private static Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Override
	public SupplierBidRate selectByPrimaryKey(String itemBidCode) throws TenderPlanException {
		try {
			return rateMapper.selectByPrimaryKey(itemBidCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<SupplierBidRate> findAllSupplierBidRate(String parentTenantAccount) throws TenderPlanException {
		try {
			return rateMapper.findAllSupplierBidRate(parentTenantAccount);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveSupplierBidRate(SupplierBidRate supplierBidRate) throws TenderPlanException {
		try {
			rateMapper.insert(supplierBidRate);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveSupplierBidRateSelective(SupplierBidRate supplierBidRate) throws TenderPlanException {
		try {
			rateMapper.insertSelective(supplierBidRate);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(SupplierBidRate supplierBidRate) throws TenderPlanException {
		try {
			rateMapper.updateByPrimaryKey(supplierBidRate);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(SupplierBidRate supplierBidRate) throws TenderPlanException {
		try {
			rateMapper.updateByPrimaryKeySelective(supplierBidRate);
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
	public List<SupplierBidRate> findSupplierBidRateByResultCode(Map map) throws TenderPlanException {
		try {
			return rateMapper.findSupplierBidRateByResultCode(map);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public SupplierBidRate findSupplierIsExists(SupplierBidRate record) throws TenderPlanException {
		try {
			return rateMapper.findSupplierIsExists(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

}
