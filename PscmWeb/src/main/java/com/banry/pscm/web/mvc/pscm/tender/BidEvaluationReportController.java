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

import com.alibaba.fastjson.JSON;
import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.account.SessionUtil;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.tender.BidEvaluationReport;
import com.banry.pscm.service.tender.BidEvaluationReportService;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.BidSupplierService;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderPlanService;
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
 * 招标结果（评标报告）Controller
 * @author chenJuan
 * @date 2018-5-30
 */
@Controller
@RequestMapping("/report")
public class BidEvaluationReportController {

	@Autowired
	BidEvaluationReportService reportService;
	@Autowired
	TenderPlanService planService;
	@Autowired
	EnumVarService enumService;
	@Autowired
	WorkFlowService workFlowService;
	@Autowired
    private TaskService taskService;
	@Autowired
	BidSupplierService bidSupplierService;
	
	private static Logger log = LoggerFactory.getLogger(BidEvaluationReportController.class);
	
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
			List<EnumVar> statusList = enumService.findByEnumName("TenderResultStatus");
			map.put("Status", statusList);
			return map;
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return map;
		}
	}
	
	/**
	 * 获取全部评标报告
	 * @return Object
	 */
	@RequestMapping("/getAllReport")
	@ResponseBody
	public Object getAllReport(HttpServletRequest request){
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			String tenantCode = SessionUtil.getSessionValue(request, SessionUtil.CURRENT_TENANT_CODE);
			String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
			
			List<BidEvaluationReport> reportList = reportService.findAllReport(parentTenantAccount);
			for (BidEvaluationReport br : reportList) {
				//状态是审批中的状态
				if (br.getStatus().getEnumValue().intValue() > Constants.WF_STATUS_DRAFT && br.getStatus().getEnumValue().intValue() < Constants.WF_STATUS_FINISH) {
					Task unsignedTasks = taskService.createTaskQuery().processInstanceBusinessKey(ProcessDefinitionKey.TENDER_RESULT + "-" + br.getBidResultCode()).taskCandidateUser(userCode + "-" + tenantCode).singleResult();
		        	if (unsignedTasks != null) {
		        		br.setTaskId(unsignedTasks.getId());
			        } else {
						Task todoTask = taskService.createTaskQuery().processInstanceBusinessKey(ProcessDefinitionKey.TENDER_RESULT + "-" + br.getBidResultCode()).taskAssignee(userCode + "-" + tenantCode).singleResult();
				        if (todoTask != null) {
				        	br.setTaskId(todoTask.getId());
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
	 * 根据招标结果编码查找评标报告
	 * @param bidResultCode
	 * @return Object
	 */
	@RequestMapping("/findReportByCode")
	@ResponseBody
	public Object findReportByCode(String bidResultCode, HttpServletRequest request){
		BidEvaluationReport report = new BidEvaluationReport();
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			Map<String, String> map = new HashMap<String, String>();
			map.put("bidResultCode", bidResultCode);
			map.put("parentTenantAccount", parentTenantAccount);
			report = reportService.selectByPrimaryKey(map);
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
		return report;
	}
	
	/**
	 * 新增或修改评标报告
	 * @param report
	 * @param flag
	 * @return Map
	 */
	@RequestMapping("/addOrUpdateReport")
	@ResponseBody
	public ResultVO addOrUpdateReport(BidEvaluationReport report,String flag, String reportJson){
		try {
			List<BidSupplier> supplierList = JSON.parseArray(reportJson, BidSupplier.class);
			if("I".equals(flag)){
				//判断招标计划是否存在不是退回终止的招标结果
				String sqlWhere = "tender_plan_code='" + report.getTenderPlanCode().getTenderPlanCode() + "' and status <> " + Constants.WF_STATUS_RETURN;
				List<BidEvaluationReport> brList = reportService.findReportBySqlWhere(sqlWhere);
				if (brList.size() > 0) {
					log.error("招标计划已经存在招标结果");
					return ResultVOUtil.error(ResultEnum.TENDER_PLAN_EXIST_TENDER_RESULT.getMessage());
				}
				report.setBidResultCode(String.valueOf((new Date()).getTime()));
				EnumVar s = new EnumVar(Constants.WF_STATUS_DRAFT);
				report.setStatus(s);
				reportService.saveBidEvaluationReportSelective(report, supplierList);
				return ResultVOUtil.success(ResultEnum.SAVE_SUCCESS.getMessage());
			}else if("U".equals(flag) && (report.getBidResultCode()!=null && !"".equals(report.getBidResultCode()))){
				reportService.updateByPrimaryKeySelective(report, supplierList);
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
	 * 根据招标结果编码删除评标报告
	 * @param bidResultCode
	 * @return Map
	 */
	@RequestMapping("/deleteReport")
	@ResponseBody
	public ResultVO deleteReport(HttpServletRequest request, String bidResultCode){
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("parentTenantAccount", parentTenantAccount);
			queryMap.put("bidResultCode", bidResultCode);
			BidEvaluationReport bs = reportService.selectByPrimaryKey(queryMap);
			if (bs != null) {
				//假设1表示起草状态
				if(bs.getStatus().getEnumValue().intValue()==Constants.WF_STATUS_DRAFT){
					int i = reportService.deleteByPrimaryKey(bidResultCode);
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
				log.error("招标结果不存在");
				return ResultVOUtil.error(ResultEnum.BID_EVALUATION_REPORT_NOT_EXIST.getMessage());
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
	 * 提交招标结果
	 * @param bidResultCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/startProcessInstance")
	@ResponseBody
	public ResultVO startProcessInstance(String bidResultCode, HttpServletRequest request) {
		try {
			String userName = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserName();
			Map<String, Object> variables = new HashMap<String, Object>();
			//父租户（公司级）
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			//当前租户
			String tenantCode = SessionUtil.getSessionValue(request, SessionUtil.CURRENT_TENANT_CODE);
			
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("parentTenantAccount", parentTenantAccount);
			queryMap.put("bidResultCode", bidResultCode);
			
			BidEvaluationReport br = reportService.selectByPrimaryKey(queryMap);
			if (br != null) {
			//起草状态
				if(br.getStatus().getEnumValue().intValue()==Constants.WF_STATUS_DRAFT){
					
					//校验是否存在未投标的供方，是否维护所有供方的最终报价
					queryMap.put("sqlWhere", "where tender_plan_code = '" + br.getTenderPlanCode().getTenderPlanCode() + "'");
					List<BidSupplier> bidList = bidSupplierService.findBidSupplierBySqlWhere(queryMap);
					//本次中标单位
					BidSupplier curBid = null;
					for (int i = 0; i < bidList.size(); i++) {
						BidSupplier bs = bidList.get(i);
						//取第一个作为中标单位
						if (i == 0) {
							curBid = bs;
						}
						if (bs.getStatus().intValue() == 0) {
							log.error("存在没有投标的供方，不可提交");
							return ResultVOUtil.error(ResultEnum.EXIST_NOT_BID_SUPPLIER.getMessage());
						}
						if (bs.getEndQuote() == null) {
							log.error("最终报价没有维护，请先维护再提交");
							return ResultVOUtil.error(ResultEnum.END_QUOTE_NOT_EXIST.getMessage());
						}
					}
					//查询所有已经中标单位，判断本次中标单位之前是否中标
					List<BidSupplier> hisBidList = bidSupplierService.findAllBidSupplierList();
					for (BidSupplier bs : hisBidList) {
						if (bs.getSupplierCreditCode().getSupplierCreditCode().
								equals(curBid.getSupplierCreditCode().getSupplierCreditCode())) {
							log.error("排序第一的供方已经中标过，请修改排序字段，设置其他供方中标");
							return ResultVOUtil.error(ResultEnum.SUPPLIER_IS_BID.getMessage());
						}
					}
					variables.put("parentTenantAccount", parentTenantAccount);
					variables.put("tenantCode", tenantCode);
					//上报人
					variables.put("reportUser", userName);
					workFlowService.startProcessInstanceByKey(ProcessDefinitionKey.TENDER_RESULT, ProcessDefinitionKey.TENDER_RESULT + "-" + bidResultCode, variables);
					return ResultVOUtil.success(ResultEnum.SUBMIT_SUCCESS.getMessage());
				} else {
					log.error("已经提交，不可再提交");
					return ResultVOUtil.error(ResultEnum.SUBMIT_CANNOT_SUBMIT.getMessage());
				}
			} else {
				log.error("招标结果不存在");
				return ResultVOUtil.error(ResultEnum.BID_EVALUATION_REPORT_NOT_EXIST.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Exception异常：", e);
			return ResultVOUtil.error(e.getMessage());
		}
	}
	
	/**
	 * 查询已经审批结束的招标结果
	 * 
	 * @return
	 */
	@RequestMapping("/findApprovalFinishReport")
	@ResponseBody
	public List<BidEvaluationReport> findApprovalFinishReport() {
		try {
			List<BidEvaluationReport> bs = reportService.findApprovalFinishReport();
			return bs;
		} catch (TenderPlanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}

