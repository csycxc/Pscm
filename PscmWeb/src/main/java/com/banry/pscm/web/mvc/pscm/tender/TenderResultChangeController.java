package com.banry.pscm.web.mvc.pscm.tender;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.account.SessionUtil;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.contract.ContractService;
import com.banry.pscm.service.contract.DownContract;
import com.banry.pscm.service.enums.ChangeTypeEnum;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.BidSupplierService;
import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBill;
import com.banry.pscm.service.tender.SubDivWorkTenderResultChangeBillService;
import com.banry.pscm.service.tender.SupplierBidItemRateService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderResultChange;
import com.banry.pscm.service.tender.TenderResultChangeService;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.service.util.EnumVarService;
import com.banry.pscm.service.util.UtilException;
import com.banry.pscm.service.workflow.WorkFlowService;
import com.banry.pscm.web.mvc.VO.ResultVO;
import com.banry.pscm.web.mvc.enums.ResultEnum;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.utils.ResultVOUtil;
import com.banry.pscm.workflow.util.ProcessDefinitionKey;

/**
 * 招标结果变更Controller
 * @author chenJuan
 * @date 2018-06-22
 */
@Controller
@RequestMapping("/resultChange")
public class TenderResultChangeController {
	
	@Autowired
	TenderResultChangeService changeService;
	@Autowired
	EnumVarService enumService;
	@Autowired
	WorkFlowService workFlowService;
	@Autowired
    private TaskService taskService;
	@Autowired
	BidSupplierService bidSupplierService;
	@Autowired
	SubDivWorkBillService workBillService;
	@Autowired
	SupplierBidItemRateService rateService;
	@Autowired
	SubDivWorkTenderResultChangeBillService resultChangeBillService;
	@Autowired
	ContractService contractService;
	
	private static Logger log = LoggerFactory.getLogger(TenderResultChangeController.class);
	
	/**
	 * 查询交易活动类型、招标结果状态
	 * @param enumName
	 * @return
	 */
	@RequestMapping("/getBizTypeStatusList")
	@ResponseBody
	public Map<String, List<EnumVar>> getBizTypeStatusList(HttpServletRequest request) {
		Map<String, List<EnumVar>> map = new HashMap<String, List<EnumVar>>(); 
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<EnumVar> bizTypeList = enumService.findByEnumName("BizType");
			map.put("BizType", bizTypeList);
			List<EnumVar> statusList = enumService.findByEnumName("TenderResultChangeStatus");
			map.put("Status", statusList);
			return map;
		} catch (UtilException e) {
			e.printStackTrace();
			return map;
		}
	}

	/**
	 * 获取所有招标计划下的招标结果变更
	 * @return
	 */
	@RequestMapping("/getAllResultChange")
	@ResponseBody
	public Object getAllResultChange(HttpServletRequest request){
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			String tenantCode = SessionUtil.getSessionValue(request, SessionUtil.CURRENT_TENANT_CODE);
			String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
			List<TenderResultChange> reportList = changeService.findAllChange(parentTenantAccount);
			for (TenderResultChange br : reportList) {
				//状态是审批中的状态
				if (br.getStatus().getEnumValue().intValue() > Constants.WF_STATUS_DRAFT && br.getStatus().getEnumValue().intValue() < Constants.WF_STATUS_FINISH) {
					Task todoTask = taskService.createTaskQuery().processInstanceBusinessKey(ProcessDefinitionKey.TENDER_RESULT_CHANGE + "-" + br.getTenderResultIdChangeCode()).taskAssignee(userCode + "-" + tenantCode).singleResult();
			        if (todoTask != null) {
			        	br.setTaskId(todoTask.getId());
			        } else {
			        	Task unsignedTasks = taskService.createTaskQuery().processInstanceBusinessKey(ProcessDefinitionKey.TENDER_RESULT_CHANGE + "-" + br.getTenderResultIdChangeCode()).taskCandidateUser(userCode + "-" + tenantCode).singleResult();
			        	if (unsignedTasks != null) {
			        		br.setTaskId(unsignedTasks.getId());
				        }
			        }
				}
			}
			data.setData(reportList);
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
	 * 新增或修改结果变更
	 * @param report
	 * @param flag
	 * @return Map
	 */
	@RequestMapping("/addOrUpdateResultChange")
	@ResponseBody
	public ResultVO addOrUpdateResultChange(TenderResultChange change,String flag){
		try {
			if("I".equals(flag)){
				//判断招标结果是否存在未结束的招标结果变更
				TenderResultChange c = changeService.selectNoFinishByResultCode(change.getBidResultCode().getBidResultCode());
				if (c != null) {
					log.error("招标结果存在未结束的变更，请审批完成再新增");
					return ResultVOUtil.error(ResultEnum.TENDER_RESULT_EXIST_NOT_FINISH_CHANGE.getMessage());
				}
				//判断是否关联了审批通过的合同
				DownContract con = contractService.findDownContractByTenderResultCode(change.getBidResultCode().getBidResultCode());
				if (con != null) {
					log.error("招标结果已经关联合同，不可新增招标结果变更");
					return ResultVOUtil.error(ResultEnum.TENDER_RESULT_RELATION_CONTRACT_CANNOT_ADD.getMessage());
				}
				change.setTenderResultIdChangeCode(String.valueOf((new Date()).getTime()));
				EnumVar s = new EnumVar(Constants.WF_STATUS_DRAFT);
				change.setStatus(s);
				changeService.saveChangeSelective(change);
				return ResultVOUtil.success(ResultEnum.SAVE_SUCCESS.getMessage());
			}else if("U".equals(flag) && (change.getTenderResultIdChangeCode()!=null && !"".equals(change.getTenderResultIdChangeCode()))){
				changeService.updateByPrimaryKeySelective(change);
				return ResultVOUtil.success(ResultEnum.MODIFY_SUCCESS.getMessage());
			}else{
				log.error("数据错误");
				return ResultVOUtil.error(ResultEnum.DATA_ERROR.getMessage());
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
	 * 删除招标结果变更
	 * @param tenderResultIdChangeCode
	 * @return Map
	 */
	@RequestMapping("/deleteResultChange")
	@ResponseBody
	public ResultVO deleteResultChange(HttpServletRequest request, String tenderResultIdChangeCode){
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("parentTenantAccount", parentTenantAccount);
			queryMap.put("tenderResultIdChangeCode", tenderResultIdChangeCode);
			TenderResultChange trc = changeService.selectByPrimaryKey(queryMap);
			if (trc != null) {
			//假设1表示起草状态
				if(trc.getStatus().getEnumValue().intValue()==Constants.WF_STATUS_DRAFT){
					int i = changeService.deleteByPrimaryKey(tenderResultIdChangeCode);
					if(i==0){
						log.error("删除失败");
						return ResultVOUtil.error(ResultEnum.DELETE_FAIL.getMessage());
					}else{
						return ResultVOUtil.success(ResultEnum.DELETE_SUCCESS.getMessage());
					}
				} else {
					log.error("已经提交，不能删除");
					return ResultVOUtil.error(ResultEnum.SUBMIT_CANNOT_DELETE.getMessage());
				}
			} else {
				log.error("招标结果变更不存在");
				return ResultVOUtil.error(ResultEnum.TENDER_RESULT_CHANGE_NOT_EXIST.getMessage());
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
	 * 提交招标结果变更
	 * @param tenderResultIdChangeCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/startProcessInstance")
	@ResponseBody
	public ResultVO startProcessInstance(String tenderResultIdChangeCode, HttpServletRequest request) {
		try {
			String userName = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserName();
			Map<String, Object> variables = new HashMap<String, Object>();
			//父租户（公司级）
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			//当前租户
			String tenantCode = SessionUtil.getSessionValue(request, SessionUtil.CURRENT_TENANT_CODE);
			
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("parentTenantAccount", parentTenantAccount);
			queryMap.put("tenderResultIdChangeCode", tenderResultIdChangeCode);
			
			TenderResultChange trc = changeService.selectByPrimaryKey(queryMap);
			if (trc != null) {
				if(trc.getStatus().getEnumValue().intValue()==Constants.WF_STATUS_DRAFT){
					//判断是否关联了审批通过的合同
					DownContract con = contractService.findDownContractByTenderResultCode(trc.getBidResultCode().getBidResultCode());
					if (con != null) {
						log.error("招标结果已经关联合同，不可提交招标结果变更");
						return ResultVOUtil.error(ResultEnum.TENDER_RESULT_RELATION_CONTRACT_CANNOT_SUBMIT.getMessage());
					}
					variables.put("parentTenantAccount", parentTenantAccount);
					variables.put("tenantCode", tenantCode);
					//上报人
					variables.put("reportUser", userName);
					workFlowService.startProcessInstanceByKey(ProcessDefinitionKey.TENDER_RESULT_CHANGE, ProcessDefinitionKey.TENDER_RESULT_CHANGE + "-" + tenderResultIdChangeCode, variables);
					return ResultVOUtil.success(ResultEnum.SUBMIT_SUCCESS.getMessage());
				} else {
					log.error("已经提交，不可再次提交");
					return ResultVOUtil.error(ResultEnum.SUBMIT_CANNOT_SUBMIT.getMessage());
				}
			} else {
				log.error("招标结果变更不存在");
				return ResultVOUtil.error(ResultEnum.TENDER_RESULT_CHANGE_NOT_EXIST.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultVOUtil.error(e.getMessage());
		}
	}
	
	/**
	 * 根据招标结果变更编码,招标计划编码查询清单的变更信息
	 * @param tenderPlanCode
	 * @return Object
	 */
	@RequestMapping("/findChangedSupplierBidItemRate")
	@ResponseBody
	public Object findChangedSupplierBidItemRate(String tenderResultIdChangeCode, String tenderPlanCode, HttpServletRequest request){
		Map<String, String> retMap =new HashMap<String, String>();
		try {
			//父租户（公司级）
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("parentTenantAccount", parentTenantAccount);
			queryMap.put("changeCode", tenderResultIdChangeCode);
			queryMap.put("tenderResultIdChangeCode", tenderResultIdChangeCode);
			TenderResultChange trc = changeService.selectByPrimaryKey(queryMap);
			//是否可以编辑
			boolean editable = false;
			//起草状态可编辑
			if (trc.getStatus().getEnumValue().intValue() == Constants.WF_STATUS_DRAFT) {
				editable = true;
			}
			
			//中标单位
			BidSupplier bs = bidSupplierService.findBidSupplierByChangeCode(tenderResultIdChangeCode);
			//中标单位为空
			if (bs == null) {
				log.error("TenderPlanException异常：", "没有维护供方");
				return null;
			}
			StringBuffer row1 = new StringBuffer("");
			StringBuffer row2 = new StringBuffer("");
			StringBuffer data = new StringBuffer("");
			row1.append("[{\"title\":\"划分编码\", \"field\":\"divisionSnCode.divisionSnCode\", \"rowspan\":2, \"visible\" : false},{\"title\":\"位置\", \"field\":\"divisionSnCode.divName\", \"rowspan\":2},{\"title\":\"项目名称\", \"field\":\"name\", rowspan:2},");
			row1.append("{\"title\":\"项目特征及工作内容\", \"field\":\"charactDes\", rowspan:2},{\"title\":\"单位\", \"field\":\"unit\", rowspan:2},{\"title\":\"工程数量\", \"field\":\"rawConMapQuan\", rowspan:2},");
			row1.append("{\"title\":\"指导价\", colspan:2}");
			row2.append("[{\"title\":\"单价\", \"field\":\"compUnitPrice\"},{\"title\":\"合价\", \"field\":\"compUnitAmount\"}");
			//清单(包含已经变更移入的或者别的变更存在变更为此中标单位的变更)
			List<SubDivWorkBill> list = workBillService.findSubBySqlWhere(
					//招标计划查询原始关联的划分
					//"tender_plan_code='" + tenderPlanCode
					//此次招标结果变更移入的划分
					"division_sn_code in ("
					+ " select division_sn_code from sub_div_work_tender_result_change_bill a where a.change_code = '" + tenderResultIdChangeCode + "' and a.change_type = " + ChangeTypeEnum.MOVE_IN.getCode() + ")"
					//从其他招标结果直接变更供方的划分
					+ " or supplier_credit_code = '" + bs.getSupplierCreditCode().getSupplierCreditCode() + "'");
			
			//查询变更信息
			List<SubDivWorkTenderResultChangeBill> billList = resultChangeBillService.findSubDivWorkTenderResultChangeBillByChangeCode(queryMap);
			
			retMap.put("bidSupplier", bs.getSupplierCreditCode().getSupplierCreditCode());
			row1.append(",{\"title\":\"" + bs.getSupplierCreditCode().getSupplierName() + "报价\", colspan:2},{\"title\":\"操作\", \"colspan\":9}");
			row2.append(",{\"title\":\"单价\", \"field\":\"price0\"},{\"title\":\"合价\", \"field\":\"amount0\"}");
			row2.append(",{\"title\":\"变更编码\",\"field\":\"changeCode\",\"visible\": false}");
			row2.append(",{\"title\":\"变更类型\",\"field\":\"changeType\",\"formatter\": changeTypeFormatter}");
//			row2.append(",{\"title\":\"变更类型\",\"field\":\"changeTypeName\"}");
			row2.append(",{\"title\":\"工程量变更量\",\"field\":\"engNumChange\" " + (editable ? ",\"editable\":engNumChangeEdit}" : "}"));
			row2.append(",{\"title\":\"单价变更量\",\"field\":\"unitPriceChange\" " + (editable ? ",\"editable\":unitPriceChangeEdit}" : "}"));
			row2.append(",{\"title\":\"供方变更\",\"field\":\"changeSupplier.supplierName\" " + (editable ? ",\"editable\": changeSupplierEdit}" : "}"));
			row2.append(",{\"title\":\"工程量变更后值\",\"field\":\"engNumNew\"}");
			row2.append(",{\"title\":\"单价变更后值\",\"field\":\"unitPriceNew\"}");
			row2.append(",{\"title\":\"供方变更编码\",\"field\":\"changeSupplier.supplierCreditCode\",\"visible\": false}");
			row2.append(",{\"title\":\"操作\", \"events\": operateEvents" + (editable ? ", \"formatter\": operateFormatter}" : "}"));
			
			row1.append("]");
			row2.append("]");
			//是否被变更
			boolean isChange;
			for (SubDivWorkBill sd : list) {
				if (!"".equals(data.toString())) {
					data.append(",");
				}
				data.append("{\"divisionSnCode.divisionSnCode\":\"" + sd.getDivisionSnCode().getDivisionSnCode() + "\",");
				data.append("\"divisionSnCode.divName\":\"" + sd.getDivisionSnCode().getDivName() + "\",\"name\":\"" + sd.getName() + "\",");
				data.append("\"charactDes\":\"" + sd.getCharactDes() + "\", \"unit\":\"" + sd.getUnit() + "\",");
				data.append("\"rawConMapQuan\":\"" + sd.getRawConMapQuan() + "\", \"compUnitPrice\":\"" + sd.getCompUnitPrice() + "\",");
				if (sd.getCompUnitPrice() != null && sd.getRawConMapQuan() != null) {
					data.append("\"compUnitAmount\":\"" + String.format("%.2f", sd.getRawConMapQuan() * sd.getCompUnitPrice()) + "\"");
				} else {
					data.append("\"compUnitAmount\":\"\"");
				}
				//中标单价
				data.append(",\"price0\":\"" + sd.getContractUnitPrice() + "\"");
				if (sd.getRawConMapQuan() != null && sd.getContractUnitPrice() != null) {
					data.append(",\"amount0\":\"" + String.format("%.2f", sd.getRawConMapQuan() * sd.getContractUnitPrice()) + "\"");
				} else {
					data.append(",\"amount0\":\"\"");
				}
				isChange = false;
				//操作列, 显示已经变更的记录
				for (SubDivWorkTenderResultChangeBill changeBill : billList) {
					if (sd.getDivisionSnCode().getDivisionSnCode().equals(changeBill.getDivisionSnCode())) {
						isChange = true;
						data.append(",\"changeCode" + "\":\"" + changeBill.getChangeCode() + "\"");
						data.append(",\"changeType" + "\":\"" + changeBill.getChangeType() + "\"");
//						data.append(",\"changeTypeName" + "\":\"" + changeBill.getChangeTypeEnum().getMessage() + "\"");
						data.append(",\"engNumChange" + "\":\"" + (changeBill.getEngNumChange() != null ? changeBill.getEngNumChange() : "") + "\"");
						data.append(",\"unitPriceChange" + "\":\"" + (changeBill.getUnitPriceChange() != null ? changeBill.getUnitPriceChange() : "") + "\"");
						data.append(",\"engNumNew" + "\":\"" + (changeBill.getEngNumNew() != null ? changeBill.getEngNumNew() : "") + "\"");
						data.append(",\"unitPriceNew" + "\":\"" + (changeBill.getUnitPriceNew() != null ? changeBill.getUnitPriceNew() : "") + "\"");
						if (changeBill.getChangeSupplier() != null) {
							data.append(",\"changeSupplier.supplierName" + "\":\"" + changeBill.getChangeSupplier().getSupplierName() + "\"");
							data.append(",\"changeSupplier.supplierCreditCode" + "\":\"" + changeBill.getChangeSupplier().getSupplierCreditCode() + "\"");
						} else {
							data.append(",\"changeSupplier.supplierName" + "\":\"\"");
							data.append(",\"changeSupplier.supplierCreditCode" + "\":\"\"");
						}
					}
				}
				if (!isChange) {
					data.append(",\"changeCode" + "\":\"" + tenderResultIdChangeCode + "\"");
					data.append(",\"changeType" + "\":\"\"");
//					data.append(",\"changeTypeName" + "\":\"\"");
					data.append(",\"engNumChange" + "\":\"\"");
					data.append(",\"unitPriceChange" + "\":\"\"");
					data.append(",\"engNumNew" + "\":\"\"");
					data.append(",\"unitPriceNew" + "\":\"\"");
					data.append(",\"changeSupplier.supplierName" + "\":\"\"");
					data.append(",\"changeSupplier.supplierCreditCode" + "\":\"\"");
				}
				data.append("}");
			}
			retMap.put("column", "[" + row1 + "," + row2 + "]");
			retMap.put("data", "[" + data + "]");
			return retMap;
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}
	
//	/**
//	 * 根据招标结果变更编码,招标计划编码查询清单的变更信息
//	 * @param tenderPlanCode
//	 * @return Object
//	 */
//	@RequestMapping("/findChangedSupplierBidItemRate")
//	@ResponseBody
//	public Object findChangedSupplierBidItemRate(String tenderResultIdChangeCode, String tenderPlanCode, HttpServletRequest request){
//		Map<String, String> retMap =new HashMap<String, String>();
//		try {
//			//父租户（公司级）
//			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
//			Map<String, String> queryMap = new HashMap<String, String>();
//			queryMap.put("parentTenantAccount", parentTenantAccount);
//			queryMap.put("tenderResultIdChangeCode", tenderResultIdChangeCode);
//			
//			TenderResultChange trc = changeService.selectByPrimaryKey(queryMap);
//			//是否可以编辑
//			boolean editable = false;
//			//起草状态可编辑
//			if (trc.getStatus().getEnumValue().intValue() == Constants.WF_STATUS_DRAFT) {
//				editable = true;
//			}
//			Map<String, String> map = new HashMap<String, String>();
//			String tenantcode = (String) request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT");
//			map.put("parentTenantAccount", tenantcode);
//			map.put("changeCode", tenderResultIdChangeCode);
//			//中标单位
//			BidSupplier bs = bidSupplierService.findBidSupplierByChangeCode(map);
//			
//			StringBuffer row1 = new StringBuffer("");
//			StringBuffer row2 = new StringBuffer("");
//			StringBuffer data = new StringBuffer("");
//			row1.append("[{\"title\":\"划分编码\", \"field\":\"divisionSnCode.divisionSnCode\", \"rowspan\":2, \"visible\" : false},{\"title\":\"位置\", \"field\":\"divisionSnCode.divName\", \"rowspan\":2},{\"title\":\"项目名称\", \"field\":\"name\", rowspan:2},");
//			row1.append("{\"title\":\"项目特征及工作内容\", \"field\":\"charactDes\", rowspan:2},{\"title\":\"单位\", \"field\":\"unit\", rowspan:2},{\"title\":\"工程数量\", \"field\":\"rawConMapQuan\", rowspan:2},");
//			row1.append("{\"title\":\"指导价\", colspan:2}");
//			row2.append("[{\"title\":\"单价\", \"field\":\"compUnitPrice\"},{\"title\":\"合价\", \"field\":\"compUnitAmount\"}");
//			//清单(包含已经变更移入的或者别的变更存在变更为此中标单位的变更)
//			List<SubDivWorkBill> list = workBillService.findSubBySqlWhere("tender_plan_code='" + tenderPlanCode + "' or division_sn_code in ("
//					+ " select division_sn_code from sub_div_work_tender_result_change_bill a where a.change_code = '" + tenderResultIdChangeCode + "' and a.change_type = 3"
//					+ " union all " 
//					+ " select division_sn_code" 
//					+ " from sub_div_work_tender_result_change_bill a join tender_result_change b on a.change_code = b.tender_result_id_change_code" 
//					+ " where b.status = " + Constants.WF_STATUS_FINISH + " and a.change_supplier = '" + bs.getSupplierCreditCode().getSupplierCreditCode() + "')");
//			
//			//判断是否存在变更移入划分或者别的变更存在变更为此中标单位的变更
//			boolean isExistsMoveInChangeSupplier = false;
//			for (SubDivWorkBill bill : list) {
//				//与当前招标计划不相同则是移入的或者存在别的变更存在变更为此中标单位的变更
//				if (!tenderPlanCode.equals(bill.getTenderPlanCode())) {
//					isExistsMoveInChangeSupplier = true;
//					break;
//				}
//			}
//			
//			//查询变更信息
//			List<SubDivWorkTenderResultChangeBill> billList = resultChangeBillService.findSubDivWorkTenderResultChangeBillByChangeCode(map);
//			//中标单位为空
//			if (bs == null) {
//				log.error("TenderPlanException异常：", "没有维护供方");
//				return null;
//			}
//			retMap.put("bidSupplier", bs.getSupplierCreditCode().getSupplierCreditCode());
//			row1.append(",{\"title\":\"" + bs.getSupplierCreditCode().getSupplierName() + "报价\", colspan:2},{\"title\":\"操作\", \"colspan\":7}");
//			row2.append(",{\"title\":\"单价\", \"field\":\"price" + bs.getSupplierCreditCode().getSupplierCreditCode() + "\"},{\"title\":\"合价\", \"field\":\"amount" + bs.getSupplierCreditCode().getSupplierCreditCode() + "\"}");
//			row2.append(",{\"title\":\"变更编码\",\"field\":\"changeCode\",\"visible\": false}");
//			row2.append(",{\"title\":\"变更类型\",\"field\":\"changeType\",\"formatter\": changeTypeFormatter}");
//			row2.append(",{\"title\":\"工程量变更\",\"field\":\"engNumChange\" " + (editable ? ",\"editable\":engNumChangeEdit}" : "}"));
//			row2.append(",{\"title\":\"单价变更\",\"field\":\"unitPriceChange\" " + (editable ? ",\"editable\":unitPriceChangeEdit}" : "}"));
//			row2.append(",{\"title\":\"供方变更\",\"field\":\"changeSupplier.supplierName\" " + (editable ? ",\"editable\": changeSupplierEdit}" : "}"));
//			row2.append(",{\"title\":\"供方变更编码\",\"field\":\"changeSupplier.supplierCreditCode\",\"visible\": false}");
//			row2.append(",{\"title\":\"操作\", \"events\": operateEvents" + (editable ? ", \"formatter\": operateFormatter}" : "}"));
//			
//			row1.append("]");
//			row2.append("]");
//			//依据招标计划编码和中标供方编码查询报价明细
//			Map<String, String> rateMap = new HashMap<String, String>();
//			rateMap.put("tenderPlanCode", tenderPlanCode);
//			rateMap.put("supplierCreditCode", bs.getSupplierCreditCode().getSupplierCreditCode());
//			List<SupplierBidItemRate> sbList = rateService.getSupBidItemRateByTpCodeAndSupCode(rateMap);
//			//存在移入的划分，需要获取原先中标的报价
//			if (isExistsMoveInChangeSupplier) {
//				List<SupplierBidItemRate> moveInList = rateService.getMoveInOrChangeSupplierSupBidItemRateByChangeCode(tenderResultIdChangeCode, bs.getSupplierCreditCode().getSupplierCreditCode());
//				sbList.addAll(moveInList);
//			}
//			//是否被变更
//			boolean isChange;
//			for (SubDivWorkBill sd : list) {
//				if (!"".equals(data.toString())) {
//					data.append(",");
//				}
//				data.append("{\"divisionSnCode.divisionSnCode\":\"" + sd.getDivisionSnCode().getDivisionSnCode() + "\",");
//				data.append("\"divisionSnCode.divName\":\"" + sd.getDivisionSnCode().getDivName() + "\",\"name\":\"" + sd.getName() + "\",");
//				data.append("\"charactDes\":\"" + sd.getCharactDes() + "\", \"unit\":\"" + sd.getUnit() + "\",");
//				data.append("\"rawConMapQuan\":\"" + sd.getRawConMapQuan() + "\", \"compUnitPrice\":\"" + sd.getCompUnitPrice() + "\",");
//				if (sd.getCompUnitPrice() != null && sd.getRawConMapQuan() != null) {
//					data.append("\"compUnitAmount\":\"" + String.format("%.2f", sd.getRawConMapQuan() * sd.getCompUnitPrice()) + "\"");
//				} else {
//					data.append("\"compUnitAmount\":\"\"");
//				}
//				for (SupplierBidItemRate sb : sbList) {
//					if (sd.getDivisionSnCode().getDivisionSnCode().equals(sb.getDivisionSnCode().getDivisionSnCode().getDivisionSnCode())) {
//						data.append(",\"price" + bs.getSupplierCreditCode().getSupplierCreditCode() + "\":\"" + sb.getFirstBidUnitRate() + "\"");
//						if (sd.getRawConMapQuan() != null && sb.getFirstBidUnitRate() != null) {
//							data.append(",\"amount" + bs.getSupplierCreditCode().getSupplierCreditCode() + "\":\"" + String.format("%.2f", sd.getRawConMapQuan() * sb.getFirstBidUnitRate()) + "\"");
//						} else {
//							data.append(",\"amount" + bs.getSupplierCreditCode().getSupplierCreditCode() + "\":\"\"");
//						}
//					}
//				}
//				isChange = false;
//				//操作列, 显示已经变更的记录
//				for (SubDivWorkTenderResultChangeBill changeBill : billList) {
//					if (sd.getDivisionSnCode().getDivisionSnCode().equals(changeBill.getDivisionSnCode())) {
//						isChange = true;
//						data.append(",\"changeCode" + "\":\"" + changeBill.getChangeCode() + "\"");
//						data.append(",\"changeType" + "\":\"" + changeBill.getChangeType() + "\"");
//						data.append(",\"engNumChange" + "\":\"" + (changeBill.getEngNumChange() != null ? changeBill.getEngNumChange() : "") + "\"");
//						data.append(",\"unitPriceChange" + "\":\"" + (changeBill.getUnitPriceChange() != null ? changeBill.getUnitPriceChange() : "") + "\"");
//						if (changeBill.getChangeSupplier() != null) {
//							data.append(",\"changeSupplier.supplierName" + "\":\"" + changeBill.getChangeSupplier()/*.getSupplierName()*/ + "\"");
//							data.append(",\"changeSupplier.supplierCreditCode" + "\":\"" + changeBill.getChangeSupplier()/*.getSupplierCreditCode()*/ + "\"");
//						} else {
//							data.append(",\"changeSupplier.supplierName" + "\":\"\"");
//							data.append(",\"changeSupplier.supplierCreditCode" + "\":\"\"");
//						}
//					}
//				}
//				if (!isChange) {
//					data.append(",\"changeCode" + "\":\"" + tenderResultIdChangeCode + "\"");
//					data.append(",\"changeType" + "\":\"\"");
//					data.append(",\"engNumChange" + "\":\"\"");
//					data.append(",\"unitPriceChange" + "\":\"\"");
//					data.append(",\"changeSupplier.supplierName" + "\":\"\"");
//					data.append(",\"changeSupplier.supplierCreditCode" + "\":\"\"");
//				}
//				data.append("}");
//			}
//			retMap.put("column", "[" + row1 + "," + row2 + "]");
//			retMap.put("data", "[" + data + "]");
//			return retMap;
//		} catch (TenderPlanException e) {
//			log.error("TenderPlanException异常：", e);
//			return null;
//		} catch (Exception e) {
//			log.error("Exception异常：", e);
//			return null;
//		}
//	}
}
