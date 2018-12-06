package com.banry.pscm.tender;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.SupplierMapper;
import com.banry.pscm.service.tender.Supplier;
import com.banry.pscm.service.tender.SupplierService;
import com.banry.pscm.service.tender.TenderPlanException;

/**
 * 投标（项目部）实现类
 * @author chenJuan
 * @date 2018-5-29
 */
@Service
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	SupplierMapper supplierMapper;
	
	private static Logger log = LoggerFactory.getLogger(SupplierServiceImpl.class);

	@Override
	public Supplier selectByPrimaryKey(String supplierCreditCode) throws TenderPlanException {
		try {
			return supplierMapper.selectByPrimaryKey(supplierCreditCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<Supplier> findAllSupplier() throws TenderPlanException {
		try {
			return supplierMapper.findAllSupplier();
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveSupplier(Supplier supplier) throws TenderPlanException {
		try {
			supplierMapper.insert(supplier);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveSupplierSelective(Supplier supplier) throws TenderPlanException {
		try {
			supplierMapper.insertSelective(supplier);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(Supplier supplier) throws TenderPlanException {
		try {
			supplierMapper.updateByPrimaryKey(supplier);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(Supplier supplier) throws TenderPlanException {
		try {
			supplierMapper.updateByPrimaryKeySelective(supplier);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteByPrimaryKey(String supplierCreditCode) throws TenderPlanException {
		try {
			return supplierMapper.deleteByPrimaryKey(supplierCreditCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<Supplier> findSupplierBySqlWhere(String sqlWhere) throws TenderPlanException {
		try {
			return supplierMapper.findSupplierBySqlWhere(sqlWhere);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<Supplier> findByTenderResultCode(String tenderPlanCode) {
		return supplierMapper.findByTenderResultCode(tenderPlanCode);
	}

	@Override
	public Supplier selectByUserCodeOrTel(String userCodeOrTel) throws TenderPlanException {
		try {
			return supplierMapper.selectByUserCodeOrTel(userCodeOrTel);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

}
