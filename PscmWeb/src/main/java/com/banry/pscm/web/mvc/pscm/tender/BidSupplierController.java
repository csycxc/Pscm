package com.banry.pscm.web.mvc.pscm.tender;

import java.util.Date;
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

import com.alibaba.fastjson.JSON;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.BidSupplierService;
import com.banry.pscm.service.tender.SupplierBidItemRate;
import com.banry.pscm.service.tender.SupplierBidItemRateService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.web.mvc.model.DataTableModel;

/**
 * 投标供方（项目部）Controller
 * @author chenJuan
 * @date 2018-5-31
 */
@Controller
@RequestMapping("/bidSupplier")
public class BidSupplierController {

	@Autowired
	BidSupplierService bidSupplierService;
	@Autowired
	SubDivWorkBillService workBillService;
	@Autowired
	SupplierBidItemRateService rateService;
	
	private static Logger log = LoggerFactory.getLogger(BidSupplierController.class);
	
	/**
	 * 查询全部投标供方
	 * @return Object
	 */
	@RequestMapping("/getAllBidSupplier")
	@ResponseBody
	public Object getAllBidSupplier(){
		DataTableModel data = new DataTableModel();
		try {
			List<BidSupplier> supplier = bidSupplierService.findAllBidSupplier();
			data.setData(supplier);
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
	 * 根据招标计划编号查找供方
	 * @param tenderPlanCode
	 * @return Object
	 */
	@RequestMapping("/getSupplierByTenderPlanCode")
	@ResponseBody
	public Object getSupplierByTenderPlanCode(String tenderPlanCode, String comeFrom, HttpServletRequest request){
		DataTableModel data = new DataTableModel();
		try {
			Map map = new HashMap();
			String tenantcode = (String) request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT");
			map.put("parentTenantAccount", tenantcode);
			map.put("sqlWhere", "where tender_plan_code = '" + tenderPlanCode + "'" 
					+ (comeFrom != null && !"".equals(comeFrom) ? " and come_from='" + comeFrom + "'" : ""));
			List<BidSupplier> bidList = bidSupplierService.findBidSupplierBySqlWhere(map);
			data.setData(bidList);
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
	 * 根据供方投标编码查询投标供方
	 * @param supplierBidCode
	 * @return Object
	 */
	@RequestMapping("/findBidSupplierByCode")
	@ResponseBody
	public Object findBidSupplierByCode(String supplierBidCode){
		BidSupplier supp = new BidSupplier();
		try {
			supp = bidSupplierService.selectByPrimaryKey(supplierBidCode);
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
		return supp;
	}
	
	/**
	 * 新增或修改投标供方
	 * @param bidSup
	 * @param flag
	 * @return Map
	 */
	@RequestMapping("/addOrUpdateBidSupplier")
	@ResponseBody
	public Map addOrUpdateBidSupplier(BidSupplier bidSup,String flag){
		Map map = new HashMap();
		try {
			if("I".equals(flag)){
				BidSupplier bidSupplier = bidSupplierService.selectByPrimaryKey(bidSup.getSupplierBidCode());
				if(bidSupplier!=null){
					map.put("result", false);
					map.put("retMsg", "编号已存在");
					return map;
				}
				bidSupplierService.saveBidSupplierSelective(bidSup);
				map.put("result", true);
				map.put("retMsg", "添加成功");
				return map;
			}else if("U".equals(flag) && (bidSup.getSupplierBidCode()!=null || bidSup.getSupplierBidCode().equals(""))){
				bidSupplierService.updateByPrimaryKeySelective(bidSup);
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
	 * 批量保存
	 * @param supplierJson
	 * @param tenderPlanCode
	 * @return
	 */
	@RequestMapping("/saveBidSupplier")
	@ResponseBody
	public Map saveBidSupplier(String supplierJson, String tenderPlanCode){
		Map map = new HashMap();
		try {
			List<BidSupplier> supplierList = JSON.parseArray(supplierJson, BidSupplier.class);
			bidSupplierService.saveBidSupplier(supplierList, tenderPlanCode);
			map.put("result", true);
			map.put("retMsg", "添加成功");
			return map;
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	/**
	 * 根据供方投标编码删除投标供方
	 * @param supplierBidCodes
	 * @return Map
	 */
	@RequestMapping("/deleteBidSupplier")
	@ResponseBody
	public Map deleteBidSupplier(String supplierBidCodes){
		Map map = new HashMap();
		try {
			int i = bidSupplierService.deleteByPrimaryKey(supplierBidCodes);
			if(i==0){
				map.put("result", true);
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
	
	/**
	 * 供方投标
	 * @param supplierBidCode
	 * @return
	 */
	@RequestMapping("/submitBidSupplier")
	@ResponseBody
	public Map submitBidSupplier(String supplierBidCode, String supplierCreditCode){
		Map map = new HashMap();
		try {
			BidSupplier bs = bidSupplierService.selectByPrimaryKey(supplierBidCode);
			//校验所有的划分价格是否已经维护
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("tenderPlanCode", bs.getTenderPlanCode());
			queryMap.put("supplierCreditCode", supplierCreditCode);
			List<SupplierBidItemRate> rateList = rateService.getSupBidItemRateByTpCodeAndSupCode(queryMap);
			for (SupplierBidItemRate item : rateList) {
				if (item.getFirstBidUnitRate() == null) {
					map.put("result", false);
					map.put("retMsg", "请先保存清单价格再点击投标");
					return map;
				}
			}
			if(bs != null){
				if (bs.getStatus().intValue() == 0) {
					bs.setStatus(1);
					//投标日期
					bs.setBidDate(new Date());
					bidSupplierService.updateByPrimaryKeySelective(bs);
					map.put("result", true);
					map.put("retMsg", "投标成功");
					return map;
				} else {
					map.put("result", false);
					map.put("retMsg", "已经投标");
					return map;
				}
			}else{
				map.put("result", false);
				map.put("retMsg", "未找到投标供方");
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
	 * 根据招标计划编码查询各个供方的报价信息明细
	 * @param tenderPlanCode
	 * @return Object
	 */
	@RequestMapping("/findSupplierBidItemRateByTenderPlanCode")
	@ResponseBody
	public Object findSupplierBidItemRateByTenderPlanCode(String tenderPlanCode, HttpServletRequest request){
		Map retMap =new HashMap();
		try {
			StringBuffer row1 = new StringBuffer("");
			StringBuffer row2 = new StringBuffer("");
			StringBuffer data = new StringBuffer("");
			row1.append("[{\"title\":\"位置\", \"field\":\"divisionSnCode.divName\", \"rowspan\":2},{\"title\":\"项目名称\", \"field\":\"name\", rowspan:2},");
			row1.append("{\"title\":\"项目特征及工作内容\", \"field\":\"charactDes\", rowspan:2},{\"title\":\"单位\", \"field\":\"unit\", rowspan:2},{\"title\":\"工程数量\", \"field\":\"rawConMapQuan\", rowspan:2},");
			row1.append("{\"title\":\"指导价\", colspan:2}");
			row2.append("[{\"title\":\"单价\", \"field\":\"compUnitPrice\"},{\"title\":\"合价\", \"field\":\"compUnitAmount\"}");
			//清单
			List<SubDivWorkBill> list = workBillService.findSubBySqlWhere("tender_plan_code=\"" + tenderPlanCode + "\"");
			Map map = new HashMap();
			String tenantcode = (String) request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT");
			map.put("parentTenantAccount", tenantcode);
			map.put("sqlWhere", "where tender_plan_code = \"" + tenderPlanCode + "\"");
			//招标计划的所有供方
			List<BidSupplier> bidList = bidSupplierService.findBidSupplierBySqlWhere(map);
			for (BidSupplier bs : bidList) {
				row1.append(",{\"title\":\"" + bs.getSupplierCreditCode().getSupplierName() + "报价\", colspan:2}");
				row2.append(",{\"title\":\"单价\", \"field\":\"price" + bs.getSupplierCreditCode().getSupplierCreditCode() + "\"},{\"title\":\"合价\", \"field\":\"amount" + bs.getSupplierCreditCode().getSupplierCreditCode() + "\"}");
			}
			row1.append("]");
			row2.append("]");
			//供方按照划分报价明细
			List<SupplierBidItemRate> sbList = rateService.findSupplierBidItemRateByTenderPlanCode(tenderPlanCode);
			for (SubDivWorkBill sd : list) {
				if (!"".equals(data.toString())) {
					data.append(",");
				}
				data.append("{\"divisionSnCode.divName\":\"" + sd.getDivisionSnCode().getDivName() + "\",\"name\":\"" + sd.getName() + "\",");
				data.append("\"charactDes\":\"" + sd.getCharactDes() + "\", \"unit\":\"" + sd.getUnit() + "\",");
				data.append("\"rawConMapQuan\":\"" + sd.getRawConMapQuan() + "\", \"compUnitPrice\":\"" + sd.getCompUnitPrice() + "\",");
				if (sd.getCompUnitPrice() != null && sd.getRawConMapQuan() != null) {
					data.append("\"compUnitAmount\":\"" + String.format("%.2f", sd.getRawConMapQuan() * sd.getCompUnitPrice()) + "\"");
				} else {
					data.append("\"compUnitAmount\":\"\"");
				}
				for (SupplierBidItemRate sb : sbList) {
					if (sd.getDivisionSnCode().getDivisionSnCode().equals(sb.getDivisionSnCode().getDivisionSnCode().getDivisionSnCode())) {
						data.append(",\"price" + sb.getSupplierCreditCode() + "\":\"" + sb.getFirstBidUnitRate() + "\"");
						if (sd.getRawConMapQuan() != null && sb.getFirstBidUnitRate() != null) {
							data.append(",\"amount" + sb.getSupplierCreditCode() + "\":\"" + String.format("%.2f", sd.getRawConMapQuan() * sb.getFirstBidUnitRate()) + "\"");
						} else {
							data.append(",\"amount" + sb.getSupplierCreditCode() + "\":\"\"");
						}
					}
				}
				data.append("}");
			}
			retMap.put("column", "[" + row1 + "," + row2 + "]");
			retMap.put("data", "[" + data + "]");
			return retMap;
		} catch (TenderPlanException e) {
			log.error("ScheduleException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}
}