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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banry.pscm.service.tender.BidSupplierService;
import com.banry.pscm.service.tender.Supplier;
import com.banry.pscm.service.tender.SupplierService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.mvc.pscm.conf.InitTenant;

@Controller
@RequestMapping("/supplier")
public class SupplierController {

	@Autowired
	SupplierService supService;
	@Autowired
	BidSupplierService bidService;
	
	private static Logger log = LoggerFactory.getLogger(TenderPlanController.class);
	
	/**
	 * 获取全部供方信息
	 * @return Object
	 */
	@RequestMapping("/getAllSupplier")
	@ResponseBody
	public Object getAllSupplier(HttpServletRequest request){
		DataTableModel data = new DataTableModel();
		try {
			InitTenant.setCurrentSessionCompDS(request.getSession());
			List<Supplier> list = supService.findAllSupplier();
			data.setData(list);
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
	 * 根据社会信用代码获取供方
	 * @param supplierCreditCode
	 * @return Object
	 */
	@RequestMapping("/findSupplierByCode")
	@ResponseBody
	public Object findSupplierByCode(String supplierCreditCode){
		Supplier sup = new Supplier();
		try {
			sup = supService.selectByPrimaryKey(supplierCreditCode);
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
		return sup;
	}
	
	/**
	 * 增加或修改供方
	 * @param supplier
	 * @param flag
	 * @return Map
	 */
	@RequestMapping("/addOrUpdateSupplier")
	@ResponseBody
	public Map addOrUpdateTenderPlan(Supplier supplier,String flag){
		Map map = new HashMap();
		try {
			if("I".equals(flag)){//新增
				Supplier key = supService.selectByPrimaryKey(supplier.getSupplierCreditCode());
				if(key!=null){
					map.put("result", false);
					map.put("retMsg", "编号已存在");
					return map;
				}
				supService.saveSupplierSelective(supplier);
				map.put("result", true);
				map.put("retMsg", "添加成功");
				return map;
			}else if("U".equals(flag) && (supplier.getSupplierCreditCode()!=null || supplier.getSupplierCreditCode().equals(""))){
				supService.updateByPrimaryKeySelective(supplier);
				map.put("result", true);
				map.put("retMsg", "修改成功");
				return map;
			}else{
				map.put("result", false);
				map.put("retMsg", "数据错误");
				return map;
			}
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	/**
	 * 根据社会信用代码删除供方
	 * @param supplierCreditCode
	 * @return Map
	 */
	@RequestMapping("/deleteSupplier")
	@ResponseBody
	public Map deleteTenderPlan(String supplierCreditCode){
		Map map = new HashMap();
		try {
			int i = supService.deleteByPrimaryKey(supplierCreditCode);
			if(i==0){
				map.put("result", false);
				map.put("retMsg", "删除失败");
				return map;
			}else{
				map.put("result", true);
				map.put("retMsg", "删除成功");
				return map;
			}
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/findByTenderResult", method = RequestMethod.GET)
	public List<Supplier> findByTenderResult(String tenderResultCode) {
		return supService.findByTenderResultCode(tenderResultCode);
	}
}
