package com.banry.pscm.web.mvc.pscm.tender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.BidSupplierService;
import com.banry.pscm.service.tender.SupplierBidItemRate;
import com.banry.pscm.service.tender.SupplierBidItemRateService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderResultChangeBill;
import com.banry.pscm.service.tender.TenderResultChangeBillService;
import com.banry.pscm.web.mvc.model.DataTableModel;

/**
 * 供方投标清单报价Controller
 * @author chenJuan
 * @date 2018-06-21
 */
@Controller
@RequestMapping("/itemRate")
public class SupplierBidItemRateController {
	
	@Autowired
	SupplierBidItemRateService rateService;
	@Autowired
	SubDivWorkBillService workBillService;
	@Autowired
	TenderResultChangeBillService changeBillService;
	@Autowired
	BidSupplierService bidSupplierService;
	
	private static Logger log = LoggerFactory.getLogger(SupplierBidItemRateController.class);
	
	/**
	 * 根据招标计划编码和招标结果编码查找n个划分对应的n个投标清单
	 * @param tenderPlanCode
	 * @param bidResultCode
	 * @return
	 */
	@RequestMapping("/getSupplierBidItemRateByCode")
	@ResponseBody
	public Object getSupplierBidItemRateByCode(String tenderPlanCode,String bidResultCode){
		DataTableModel data = new DataTableModel();
		try {
			List<SubDivWorkBill> findSubBySqlWhere = workBillService.findSubBySqlWhere("tender_plan_code='"+tenderPlanCode+"' and bid_result_code='"+bidResultCode+"'");
			List<SupplierBidItemRate> rateList = new ArrayList<>();
			for(SubDivWorkBill bill : findSubBySqlWhere){
				SupplierBidItemRate rate = rateService.findSupplierBidItemRateByDivCode(bill.getDivisionSnCode().getDivisionSnCode());
//				if(rate!=null){
//					rate.setName(bill.getName());
//				}
				rateList.add(rate);
			}
			data.setData(rateList);
			return data;
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}
	
	
	/**
	 * 根据招标计划变更编码查询供方清单报价
	 * @param TenderResultIdChangeCode
	 * @return Object
	 */
	@RequestMapping("/getItemRateByChangeCode")
	@ResponseBody
	public Object getSupplierBidItemRateByCode(String tenderResultIdChangeCode){
		DataTableModel data = new DataTableModel();
		try {
			List<TenderResultChangeBill> billBysqlWhere = changeBillService.findChangeBillByChangeCode(tenderResultIdChangeCode);
			List<SupplierBidItemRate> rateList = new ArrayList<>();
			for(TenderResultChangeBill bill : billBysqlWhere){
				SupplierBidItemRate rate = rateService.findSupplierBidItemRateByDivCode(bill.getDivisionSnCode());
//				if(rate!=null){
//					rate.setName(workBillService.findSubDivWorkBillByKey(bill.getDivisionSnCode()).getName());
//				}
				rateList.add(rate);
			}
			data.setData(rateList);
			return data;
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}

	/**
	 * 保存供方报价信息
	 * @param rateJson
	 * @param supplierCreditCode
	 * @param supplierBidCode
	 * @return
	 */
	@RequestMapping("/saveSupplierBidItemRate")
	@ResponseBody
	public Map saveSupplierBidItemRate(String rateJson, String supplierCreditCode, String supplierBidCode){
		Map map = new HashMap();
		try {
			BidSupplier bidSupplier = bidSupplierService.selectByPrimaryKey(supplierBidCode);
			if (bidSupplier.getStatus().intValue() == 0) {
				List<SupplierBidItemRate> supplierList = JSON.parseArray(rateJson, SupplierBidItemRate.class);
				rateService.saveSupplierBidItemRate(supplierList, supplierCreditCode, bidSupplier);
				map.put("result", true);
				map.put("retMsg", "保存成功");
				return map;
			} else {
				map.put("result", false);
				map.put("retMsg", "已经投标不可修改");
				return map;
			}
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
}
