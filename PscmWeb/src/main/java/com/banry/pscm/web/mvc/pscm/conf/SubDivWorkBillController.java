package com.banry.pscm.web.mvc.pscm.conf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.banry.pscm.service.schedule.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banry.pscm.account.SessionUtil;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.tender.SupplierBidItemRateService;
import com.banry.pscm.service.tender.TenderPlan;
import com.banry.pscm.service.tender.TenderPlanService;
import com.banry.pscm.web.mvc.VO.ResultVO;
import com.banry.pscm.web.mvc.enums.ResultEnum;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.utils.ResultVOUtil;

/**
 * 分项工程清单Controller
 * @author chenJuan
 * @date 2018-6-1
 */
@Controller
@RequestMapping("/workBill")
public class SubDivWorkBillController {
	
	@Autowired
	SubDivWorkBillService workBillService;
	@Autowired
	SubDivWorkChangeBillService workChangeBillService;
	@Autowired
	SupplierBidItemRateService rateService;
	@Autowired
	TenderPlanService tenderPlanService;

	private static Logger log = LoggerFactory.getLogger(SubDivWorkBillController.class);
		
	//提交操作，只是修改了清单表的status，其他操作是否可以进行（如定额数据表的修改更新），要根据status的值判断。
	//http://192.168.1.104:8080/subdivworkbill/submit?divisionsncode=2.1.1.1
	@RequestMapping(value = "/submit")
	public ResponseEntity<?> submitSubDivWorkBill(String divisionsncode) {
		log.info("submitSubDivWorkBill start...");
		try {
			//DynamicDataSourceContextHolder.set("shiyu");//todo 后期注释，解开上面的InitTenant.setCurrentSessionDS(session);
			log.debug("begin submit , set status = 2 .....");
			int i = workBillService.updateSubDivWorkBillStatusSubmit(divisionsncode);
			if(i>0) {
				log.debug("successfully submit...");
				return new ResponseEntity<>(null, HttpStatus.OK);//成功，返回200				
			}else {
				log.debug("fail to submit ...");
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);//失败，返回500
			}
		} catch (Exception e) {
			log.error("submit error:"+e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//删除失败，返回500
		}
	}

	@RequestMapping(value = "/findbycode")//findbydivsncode  findbycode
	public SubDivWorkBill findSubDivWorkBillByCode(String divisionsncode) {
		log.info("findSubDivWorkBillByCode call with divisionsncode="+divisionsncode);
		try {
			//DynamicDataSourceContextHolder.set("shiyu");
			SubDivWorkBill sdwb = workBillService.findSubDivWorkBillByKey(divisionsncode);
			log.info(sdwb.toString());
			return sdwb;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return null;
	}
	@RequestMapping(value = "/findbyparentcode")//findbyparentcode
	@ResponseBody
	public List<SubDivWorkBill> findSubDivWorkBillsByParentCode(String parentcode) {
		try {
			//DynamicDataSourceContextHolder.set("shiyu");
			List<SubDivWorkBill> list = workBillService.findSubDivWorkBillsByParentDivisionSnCode(parentcode);//配置管理里面的数据
			for (SubDivWorkBill subDivWorkBill : list) {
				log.info(subDivWorkBill.toString());
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/save")
	public ResponseEntity<?> saveSubDivWorkBill(SubDivWorkBill sdwb) {
		log.info("SubDivWorkBill call with divisionsncode="+sdwb.getDivisionSnCode());
		try {
			log.debug("begin save SubDivWorkBill...");
			Integer status = sdwb.getStatus();
			if(status == null || status == 0 || status == 1) {
				sdwb.setStatus(1);
			}else if(status == 2) {
				return new ResponseEntity<>("该清单已经提交过，不能更改。", HttpStatus.INTERNAL_SERVER_ERROR);//删除失败，返回500
			}
			workBillService.saveSubDivWorkBill(sdwb);
			log.debug("finish save SubDivWorkBill...");
			return new ResponseEntity<>(null, HttpStatus.OK);//保存成功，返回200
		} catch (Exception e) {
			log.error("save error:"+e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//删除失败，返回500
		}
	}

	@RequestMapping(value = "/saveselective")
	public ResponseEntity<?> saveSubDivWorkBillSelective(SubDivWorkBill sdwb) {
		log.info("saveSubDivWorkBillSelective call with divisionsncode="+sdwb.getDivisionSnCode());
		try {
			log.debug("begin saveselective SubDivWorkBill...");
			workBillService.saveSubDivWorkBillSelective(sdwb);
			log.debug("finish saveselective SubDivWorkBill...");
			return new ResponseEntity<>(null, HttpStatus.OK);//保存成功，返回200
		} catch (Exception e) {
			log.error("saveselective error:"+e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//删除失败，返回500
		}
	}
	
	/**
	 * 查询全部分项工程清单
	 * @return Object
	 */
	@RequestMapping("/getAllWorkBill")
	@ResponseBody
	public Object getAllWorkBill(){
		DataTableModel data = new DataTableModel();
		try {
			List<SubDivWorkBill> bill = workBillService.findAllSubDivWorkBill();
			data.setData(bill);
			return data;
		} catch (ScheduleException e) {
			log.error("ScheduleException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}
	
	
	@RequestMapping("/findWorkBillByCode")
	@ResponseBody
	public Object findWorkBillByCode(String divisionSnCode){
		SubDivWorkBill bill = new SubDivWorkBill();
		try {
			bill = workBillService.findSubDivWorkBillByKey(divisionSnCode);
		} catch (ScheduleException e) {
			log.error("ScheduleException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
		return bill;
	}
	
	/**
	 * 根据招标计划编码查询招标计划
	 * @param tenderPlanCode
	 * @return Object
	 */
	@RequestMapping("/findWorkBillByTenderPlanCode")
	@ResponseBody
	public Object findWorkBillByTenderPlanCode(String tenderPlanCode){
		DataTableModel data = new DataTableModel();
		try {
			List<SubDivWorkBill> list = workBillService.findSubBySqlWhere("tender_plan_code='" + tenderPlanCode + "'");
			data.setData(list);
			return data;
		} catch (ScheduleException e) {
			log.error("ScheduleException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}

	@RequestMapping("/findWorkBillByContractCode")
	@ResponseBody
	public Object findWorkBillByContractCode(String contractCode) {
		DataTableModel dataTableModel = new DataTableModel();
		try {
			List<SubDivWorkBill> workBillChanges = workBillService.findSubBySqlWhere("contract_code='" +contractCode+ "'");
			dataTableModel.setData(workBillChanges);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dataTableModel;
	}

	@RequestMapping("/findWorkBillByCodes")
	@ResponseBody
	public Object findWorkBillByCodes(String contractCode, String changeCode) {
		DataTableModel dataTableModel = new DataTableModel();
		try {
			List<SubDivWorkBillChange> bills = workChangeBillService.findWorkBillsByCodes(contractCode, changeCode);
			dataTableModel.setData(bills);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dataTableModel;
	}

	/**
	 * 根据招标计划编码查询招标结果
	 * @param tenderPlanCode
	 * @return Object
	 */
	@RequestMapping("/findResultByTenderPlanCode")
	@ResponseBody
	public Object findResultByTenderPlanCode(String tenderPlanCode){
		DataTableModel data = new DataTableModel();
		HashMap<String, List<HashMap>> resultMap= new HashMap<String, List<HashMap>>();
		try {
			workBillService.findWorkBillByTenderPlanCode(tenderPlanCode);
			
			//data.setData(resultMap);
			return resultMap;
		} catch (ScheduleException e) {
			log.error("ScheduleException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}
	
	@RequestMapping("/addOrUpdateWorkBill")
	@ResponseBody
	public Map addOrUpdateTarget(SubDivWorkBill bill,String flag){
		Map map = new HashMap();
		try {
			if("I".equals(flag)){
				SubDivWorkBill sub = workBillService.findSubDivWorkBillByKey(bill.getDivisionSnCode().getDivisionSnCode());
				if(sub!=null){
					map.put("result", false);
					map.put("retMsg", "编号已存在");
					return map;
				}
				workBillService.saveSubDivWorkBillSelective(bill);
				map.put("result", true);
				map.put("retMsg", "添加成功");
				return map;
		   }else if("U".equals(flag) && (bill.getDivisionSnCode()!=null || bill.getDivisionSnCode().equals(""))){
			    workBillService.updateSubDivWorkBillSelective(bill);
				map.put("result", true);
				map.put("retMsg", "修改成功");
				return map;
			}else{
				map.put("result", false);
				map.put("retMsg", "数据错误");
				return map;
			}
		} catch (ScheduleException e) {
			log.error("ScheduleException异常：", e);
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
	
	@RequestMapping("/deleteWorkBill")
	@ResponseBody
	public ResultVO deleteWorkBill(String divisionSnCode){
		try {
			int i = workBillService.deleteSubDivWorkBillByKey(divisionSnCode);
			if(i==0){
				return ResultVOUtil.error(ResultEnum.DELETE_FAIL.getMessage());
			}else{
				return ResultVOUtil.success(ResultEnum.DELETE_SUCCESS.getMessage());
			}
		} catch (ScheduleException e) {
			log.error("ScheduleException异常：", e);
			return ResultVOUtil.error(e.getMessage());
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return ResultVOUtil.error(e.getMessage());
		}
	}
	
	/**
	 * 招标计划关联清单
	 * @param tenderPlanCode
	 * @param divisionSnCode
	 * @return
	 */
	@RequestMapping("/updateWorkBillTenderCode")
	@ResponseBody
	public ResultVO updateWorkBillTenderCode(HttpServletRequest request, String tenderPlanCode, String divisionSnCode){
		try {
			SubDivWorkBill sub = workBillService.findSubDivWorkBillByKey(divisionSnCode);
			if (sub != null) {
				//已经关联招标计划
				if (sub.getTenderPlanCode() != null && !"".equals(sub.getTenderPlanCode())) {
					String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
					Map<String, String> queryMap = new HashMap<String, String>();
					queryMap.put("parentTenantAccount", parentTenantAccount);
					queryMap.put("tenderPlanCode", sub.getTenderPlanCode());
					TenderPlan tp = tenderPlanService.selectByPrimaryKey(queryMap);
					//如果流程是退回终止
					if (tp == null || tp != null && tp.getStatus().getEnumValue().intValue() == Constants.WF_STATUS_RETURN) {
						sub.setTenderPlanCode(tenderPlanCode);
						workBillService.updateSubDivWorkBillSelective(sub);
						return ResultVOUtil.success(sub, ResultEnum.SUB_DIV_WORK_BILL_RELATION_SUCCESS.getMessage());
					} else {
						return ResultVOUtil.error(ResultEnum.SUB_DIV_WORK_BILL_IS_RELATION_TENDER_PLAN.getMessage());
					}
				} else {
					sub.setTenderPlanCode(tenderPlanCode);
					workBillService.updateSubDivWorkBillSelective(sub);
					return ResultVOUtil.success(sub, ResultEnum.SUB_DIV_WORK_BILL_RELATION_SUCCESS.getMessage());
				}
			} else {
				return ResultVOUtil.error(ResultEnum.SUB_DIV_WORK_BILL_NOT_EXIST.getMessage());
			}
		} catch (ScheduleException e) {
			log.error("ScheduleException异常：", e);
			return ResultVOUtil.error(e.getMessage());
		}
	}

	/**
	 * 招标计划清除清单
	 * @param tenderPlanCode
	 * @param divisionSnCode
	 * @return
	 */
	@RequestMapping("/clearWorkBillTenderCode")
	@ResponseBody
	public Map clearWorkBillTenderCode(String divisionSnCode){
		Map map = new HashMap();
		try {
			SubDivWorkBill sub = workBillService.findSubDivWorkBillByKey(divisionSnCode);
			if (sub != null) {
				map.put("retMsg", "");
				sub.setTenderPlanCode(null);
				workBillService.updateSubDivWorkBill(sub);
				return map;
			} else {
				map.put("retMsg", "分项工程清单不存在");
				return map;
			}
		} catch (ScheduleException e) {
			log.error("ScheduleException异常：", e);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
}
