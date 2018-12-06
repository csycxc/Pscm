package com.banry.pscm.web.mvc.pscm.tender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.service.tender.Supplier;
import com.banry.pscm.service.tender.SupplierBidRate;
import com.banry.pscm.service.tender.SupplierBidRateService;
import com.banry.pscm.service.tender.SupplierService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderResultChangeBillService;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.mvc.pscm.conf.InitTenant;

/**
 * 临时供方投标清单报价Controller
 * @author xudingkui
 * @date 2018-08-03
 */
@Controller
@RequestMapping("/itemRateTemp")
public class SupplierBidRateController {
	
	@Autowired
	SupplierBidRateService rateService;
	@Autowired
	SubDivWorkBillService workBillService;
	@Autowired
	TenderResultChangeBillService changeBillService;
	@Autowired
	SupplierService supService;
	
	private static Logger log = LoggerFactory.getLogger(SupplierBidRateController.class);
	
	/**
	 * 根据招标结果编码查询供方投标清单报价
	 * @param bidResultCode
	 * @return
	 */
	@RequestMapping("/getSupplierBidRateByResultCode")
	@ResponseBody
	public Object getSupplierBidRateByResultCode(HttpServletRequest request, String bidResultCode){
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			Map<String, String> map = new HashMap<String, String>();
			map.put("parentTenantAccount", parentTenantAccount);
			map.put("bidResultCode", bidResultCode);
			List<SupplierBidRate> rateList = rateService.findSupplierBidRateByResultCode(map);
			data.setData(rateList);
			//查询供方信息
			InitTenant.setCurrentSessionCompDS(request.getSession());
			List<Supplier> supList = supService.findAllSupplier();
			List supOptions = new ArrayList();
			for (Supplier sup : supList) {
				Map label = new HashMap();
				label.put("label", sup.getSupplierName());
				label.put("value", sup.getSupplierCreditCode());
				supOptions.add(label);
			}
			Map<String, List> supMap = new HashMap();
			supMap.put("supplier", supOptions);
			data.setOptions(supMap);
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
	 * 新增报价信息
	 * @param request
	 * @param supBid
	 * @return
	 */
	@PostMapping("/supplierBidRate")
	@ResponseBody
	public Map addSupplierBidRate(HttpServletRequest request, SupplierBidRate supBid){
		Map map = new HashMap();
		try {
			//判断供方是否已经维护
			SupplierBidRate temp = rateService.findSupplierIsExists(supBid);
			if (temp != null) {
				map.put("error", "此供方报价信息已经维护");
			} else {
				supBid.setItemBidCode(String.valueOf((new Date()).getTime()));
				rateService.saveSupplierBidRate(supBid);
				List<SupplierBidRate> rateList = new ArrayList<SupplierBidRate>();
				//查询供方信息
				InitTenant.setCurrentSessionCompDS(request.getSession());
				Supplier sup = supService.selectByPrimaryKey(supBid.getSupplierCreditCode().getSupplierCreditCode());
				supBid.setSupplierCreditCode(sup);
				rateList.add(supBid);
				map.put("data", rateList);
			}
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			map.put("error", e.getMessage());
		} catch (Exception e) {
			log.error("Exception异常：", e);
			map.put("error", e.getMessage());
		}
		return map;
	}
	
	/**
	 * 修改报价信息
	 * @param request
	 * @param supBid
	 * @return
	 */
	@PutMapping("/supplierBidRate")
	@ResponseBody
	public Map updateSupplierBidRate(HttpServletRequest request, SupplierBidRate supBid){
		Map map = new HashMap();
		try {
			//判断供方是否已经维护
			SupplierBidRate temp = rateService.findSupplierIsExists(supBid);
			if (temp != null) {
				map.put("error", "此供方报价信息已经维护");
			} else {
				rateService.updateByPrimaryKey(supBid);
				List<SupplierBidRate> rateList = new ArrayList<SupplierBidRate>();
				//查询供方信息
				InitTenant.setCurrentSessionCompDS(request.getSession());
				Supplier sup = supService.selectByPrimaryKey(supBid.getSupplierCreditCode().getSupplierCreditCode());
				supBid.setSupplierCreditCode(sup);
				rateList.add(supBid);
				map.put("data", rateList);
			}
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			map.put("error", e.getMessage());
		} catch (Exception e) {
			log.error("Exception异常：", e);
			map.put("error", e.getMessage());
		}
		return map;
	}
	
	@DeleteMapping("/deleteSupplierBidRate")
	@ResponseBody
	public Map deleteSupplierBidRate(String bidResultCode){
		Map map = new HashMap();
		try {
			rateService.deleteByPrimaryKey(bidResultCode);
			List<SupplierBidRate> rateList = new ArrayList<SupplierBidRate>();
			map.put("data", rateList);
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			map.put("error", e.getMessage());
		}
		return map;
	}
}
