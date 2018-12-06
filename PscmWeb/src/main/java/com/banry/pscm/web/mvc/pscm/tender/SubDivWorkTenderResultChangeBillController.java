package com.banry.pscm.web.mvc.pscm.tender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banry.pscm.account.SessionUtil;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.contract.DownContract;
import com.banry.pscm.service.enums.ChangeTypeEnum;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.BidSupplierService;
import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBill;
import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBillKey;
import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBillService;
import com.banry.pscm.service.tender.SupplierBidItemRateService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderResultChangeService;
import com.banry.pscm.web.mvc.VO.ResultVO;
import com.banry.pscm.web.mvc.enums.ResultEnum;
import com.banry.pscm.web.utils.ResultVOUtil;

/**
 * 招标结果变更清单变更明细Controller
 * @author xudk
 * @date 2018-10-10
 */
@Controller
@RequestMapping("/resultChangeBill")
public class SubDivWorkTenderResultChangeBillController {
	
	@Autowired
	SubDivWorkTenderResultChangeBillService resultChangeBillService;
	@Autowired
	BidSupplierService bidSupplierService;
	@Autowired
	SubDivWorkBillService workBillService;
	@Autowired
	TenderResultChangeService resultChangeService;
	@Autowired
	SupplierBidItemRateService rateService;
	@Autowired
	SubDivWorkBillService subDivWorkBillService;
	@Autowired
	ContractService contractService;
	
	private static Logger log = LoggerFactory.getLogger(SubDivWorkTenderResultChangeBillController.class);

	
	/**
	 * 新增或修改结果变更清单
	 * @param changeBill
	 * @return
	 */
	@RequestMapping("/addOrUpdateResultChangeBill")
	@ResponseBody
	public ResultVO addOrUpdateResultChangeBill(SubDivWorkTenderResultChangeBill changeBill, HttpServletRequest request){
		try {
			if (changeBill.getChangeSupplier() != null && !"".equals(changeBill.getChangeSupplier().getSupplierCreditCode())) {
				Map<String, String> queryMap = new HashMap<String, String>();
				String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
				queryMap.put("parentTenantAccount", parentTenantAccount);
				queryMap.put("changeCode", changeBill.getChangeCode());
				//中标单位
				BidSupplier bs = bidSupplierService.findBidSupplierByChangeCode(changeBill.getChangeCode());
				//变更供方与原中标单位编码相同
				if (changeBill.getChangeSupplier().getSupplierCreditCode().equals(bs.getSupplierCreditCode().getSupplierCreditCode())) {
					log.error("变更供方不可与中标供方相同");
					return ResultVOUtil.error(ResultEnum.CHANGE_SUPPLIER_CANNOT_SAME_AS_BID_SUPPLIER.getMessage());
				}
				//查询变更信息
				List<SubDivWorkTenderResultChangeBill> billList = resultChangeBillService.findSubDivWorkTenderResultChangeBillByChangeCode(queryMap);
				for (SubDivWorkTenderResultChangeBill bill : billList) {
					//存在已经变更供方的清单，且和此清单变更的供方不一致
					if (bill.getChangeSupplier() != null 
							&& !bill.getChangeSupplier().equals(changeBill.getChangeSupplier())
							&& !bill.getDivisionSnCode().equals(changeBill.getDivisionSnCode())) {
						log.error("每次变更只能变更成一个供方");
						return ResultVOUtil.error(ResultEnum.CHANGE_SUPPLIER_MUST_ONE.getMessage());
					}
				}
				//判断变更的供方是否存在合同
				List<DownContract> conList = contractService.findContractsBySupplier(changeBill.getChangeSupplier().getSupplierCreditCode());
				if (conList.size() > 0) {
					log.error("变更的供方已经签订合同，请选择其他供方");
					return ResultVOUtil.error(ResultEnum.CHANGE_SUPPLIER_IS_SIGN_CONTRACT.getMessage());
				}
			}
			//
			SubDivWorkBill subDiv = subDivWorkBillService.findSubDivWorkBillByKey(changeBill.getDivisionSnCode());
			if (changeBill.getChangeType().intValue() == ChangeTypeEnum.ENG_NUM_AND_UNIT_PRICE_CHANGE.getCode()) {
				changeBill.setEngNumOld(subDiv.getRawConMapQuan());
				changeBill.setEngNumNew(subDiv.getRawConMapQuan() + changeBill.getEngNumChange());
				changeBill.setUnitPriceOld(subDiv.getContractUnitPrice());
				changeBill.setUnitPriceNew(subDiv.getContractUnitPrice() + changeBill.getUnitPriceChange());
			} else if (changeBill.getChangeType().intValue() == ChangeTypeEnum.ENG_NUM_CHANGE.getCode()) {
				changeBill.setEngNumOld(subDiv.getRawConMapQuan());
				changeBill.setEngNumNew(subDiv.getRawConMapQuan() + changeBill.getEngNumChange());
			} else if (changeBill.getChangeType().intValue() == ChangeTypeEnum.UNIT_PRICE_CHANGE.getCode()) {
				changeBill.setUnitPriceOld(subDiv.getContractUnitPrice());
				changeBill.setUnitPriceNew(subDiv.getContractUnitPrice() + changeBill.getUnitPriceChange());
			}
			SubDivWorkTenderResultChangeBill bill = resultChangeBillService.selectByPrimaryKey(changeBill);
			if (bill != null) {
//				bill.setChangeType(changeBill.getChangeType());
//				bill.setEngNumChange(changeBill.getEngNumChange());
//				bill.setUnitPriceOld(changeBill.get);
//				bill.setUnitPriceChange(changeBill.getUnitPriceChange());
//				bill.setUnitPriceNew(unitPriceOld);
//				bill.setChangeSupplier(changeBill.getChangeSupplier());
				resultChangeBillService.updateByPrimaryKey(changeBill);
				return ResultVOUtil.success(ResultEnum.MODIFY_SUCCESS.getMessage());
			} else {
				resultChangeBillService.saveSubDivWorkTenderResultChangeBillSelective(changeBill);
				return ResultVOUtil.success(ResultEnum.SAVE_SUCCESS.getMessage());
			}
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return ResultVOUtil.error(e.getMessage());
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return ResultVOUtil.error(e.getMessage());
		}
	}
	
	
	/**
	 * 删除招标结果变更清单
	 * @param tenderResultIdChangeCode
	 * @return Map
	 */
	@RequestMapping("/deleteResultChangeBill")
	@ResponseBody
	public ResultVO deleteResultChangeBill(HttpServletRequest request, SubDivWorkTenderResultChangeBillKey key){
		try {
			int i = resultChangeBillService.deleteByPrimaryKey(key);
			if(i==0){
				log.error("删除失败");
				return ResultVOUtil.error(ResultEnum.DELETE_FAIL.getMessage());
			}else{
				return ResultVOUtil.success(ResultEnum.DELETE_SUCCESS.getMessage());
			}
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return ResultVOUtil.error(e.getMessage());
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return ResultVOUtil.error(e.getMessage());
		}
	}
	
	/**
	 * 将划分移入变更清单
	 * @param changeCode
	 * @param divisionSnCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/moveIntoResultChangeBill")
	@ResponseBody
	public ResultVO moveIntoResultChangeBill(String changeCode, String divisionSnCode, HttpServletRequest request){
		try {
			//查询工程清单
			SubDivWorkBill bill = workBillService.findSubDivWorkBillByKey(divisionSnCode);
			if (bill.getTenderPlanCode() != null && bill.getTenderResultCode() != null
                    && bill.getSupplier() == null) {//确定清单项是无主清单项
				if (workBillService.checkSubDivWorkBill(divisionSnCode)) {
					SubDivWorkTenderResultChangeBill changeBill = new SubDivWorkTenderResultChangeBill();
					changeBill.setChangeCode(changeCode);
					changeBill.setDivisionSnCode(divisionSnCode);
					changeBill.setChangeType(ChangeTypeEnum.MOVE_IN.getCode());
					resultChangeBillService.saveSubDivWorkTenderResultChangeBillSelective(changeBill);
					StringBuffer data = new StringBuffer("");
					data.append("{\"divisionSnCode.divisionSnCode\":\"" + bill.getDivisionSnCode().getDivisionSnCode() + "\",");
					data.append("\"divisionSnCode.divName\":\"" + bill.getDivisionSnCode().getDivName() + "\",\"name\":\"" + bill.getName() + "\",");
					data.append("\"charactDes\":\"" + bill.getCharactDes() + "\", \"unit\":\"" + bill.getUnit() + "\",");
					data.append("\"rawConMapQuan\":\"" + bill.getRawConMapQuan() + "\", \"compUnitPrice\":\"" + bill.getCompUnitPrice() + "\",");
					if (bill.getCompUnitPrice() != null && bill.getRawConMapQuan() != null) {
						data.append("\"compUnitAmount\":\"" + String.format("%.2f", bill.getRawConMapQuan() * bill.getCompUnitPrice()) + "\"");
					} else {
						data.append("\"compUnitAmount\":\"\"");
					}
					data.append(",\"price0\":\"" + bill.getContractUnitPrice() + "\"");
					if (bill.getRawConMapQuan() != null && bill.getContractUnitPrice() != null) {
						data.append(",\"amount0\":\"" + String.format("%.2f", bill.getRawConMapQuan() * bill.getContractUnitPrice()) + "\"");
					} else {
						data.append(",\"amount0\":\"\"");
					}
					data.append(",\"changeCode" + "\":\"" + changeBill.getChangeCode() + "\"");
					data.append(",\"changeType" + "\":\"" + changeBill.getChangeType() + "\"");
					data.append(",\"engNumChange" + "\":\"\"");
					data.append(",\"engNumNew" + "\":\"\"");
					data.append(",\"unitPriceChange" + "\":\"\"");
					data.append(",\"unitPriceNew" + "\":\"\"");
					data.append(",\"changeSupplier.supplierName" + "\":\"\"");
					data.append(",\"changeSupplier.supplierCreditCode" + "\":\"\"}");
					return ResultVOUtil.success(data, ResultEnum.SUB_DIV_WORK_BILL_MOVE_IN_SUCCESS.getMessage());
				} else {
					log.error("清单项已被移入其他招标结果或合同");
					return ResultVOUtil.error(ResultEnum.SUB_DIV_WORK_BILL_IS_MOVE_IN.getMessage());
                }
			} else {
				log.error("此划分没有关联招标结果或者没有被移出，不可移入");
				return ResultVOUtil.error(ResultEnum.SUB_DIV_WORK_BILL_NOT_RELATION_RESULT_CANNOT_MOVE_IN.getMessage());
			}
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return ResultVOUtil.error(e.getMessage());
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return ResultVOUtil.error(e.getMessage());
		}
	}
}
