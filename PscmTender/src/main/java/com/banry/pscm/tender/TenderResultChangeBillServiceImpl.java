package com.banry.pscm.tender;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banry.pscm.persist.dao.TenderResultChangeBillMapper;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderResultChangeBill;
import com.banry.pscm.service.tender.TenderResultChangeBillService;

/**
 * 招标结果变更清单实现类
 * @author chenJuan
 * @date 2018-5-30
 */
@Service
public class TenderResultChangeBillServiceImpl implements TenderResultChangeBillService {
	
	@Autowired
	TenderResultChangeBillMapper changeBillMapper;
	
	private static Logger log = LoggerFactory.getLogger(TenderPlanServiceImpl.class);

	@Override
	public TenderResultChangeBill selectByPrimaryKey(String divisionSnCode) throws TenderPlanException {
		try {
			return changeBillMapper.selectByPrimaryKey(divisionSnCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<TenderResultChangeBill> findAllChangeBill() throws TenderPlanException {
		try {
			return changeBillMapper.findAllChangeBill();
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveChangeBill(TenderResultChangeBill record) throws TenderPlanException {
		try {
			changeBillMapper.insert(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void saveChangeBillSelective(TenderResultChangeBill record) throws TenderPlanException {
		try {
			changeBillMapper.insertSelective(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKeySelective(TenderResultChangeBill record) throws TenderPlanException {
		try {
			changeBillMapper.updateByPrimaryKeySelective(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public void updateByPrimaryKey(TenderResultChangeBill record) throws TenderPlanException {
		try {
			changeBillMapper.updateByPrimaryKey(record);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public int deleteByPrimaryKey(String divisionSnCode) throws TenderPlanException {
		try {
			return changeBillMapper.deleteByPrimaryKey(divisionSnCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

	@Override
	public List<TenderResultChangeBill> findChangeBillByChangeCode(String tenderResultIdChangeCode) throws TenderPlanException {
		try {
			return changeBillMapper.findChangeBillByChangeCode(tenderResultIdChangeCode);
		} catch (Exception e) {
			log.debug("Exception异常：", e);
			throw new TenderPlanException(e.getMessage(),e);
		}
	}

}
