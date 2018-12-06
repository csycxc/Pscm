package com.banry.pscm.web.mvc.pscm.tender;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.account.SessionUtil;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.comm.Constants;
import com.banry.pscm.service.schedule.SubDivWorkBill;
import com.banry.pscm.service.schedule.SubDivWorkBillService;
import com.banry.pscm.service.tender.BidSupplier;
import com.banry.pscm.service.tender.BidSupplierService;
import com.banry.pscm.service.tender.TenderPlan;
import com.banry.pscm.service.tender.TenderPlanException;
import com.banry.pscm.service.tender.TenderPlanService;
import com.banry.pscm.service.tender.TenderPlanWithBLOBs;
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
 * 招标计划Controller
 * @author chenJuan
 * @date 2018-5-30
 */
@Controller
@RequestMapping("/tenderPlan")
public class TenderPlanController {
	
	@Autowired
	TenderPlanService tenderService;
	@Autowired
	EnumVarService enumVarService;
	@Autowired
	WorkFlowService workFlowService;
	@Autowired
    private TaskService taskService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private Environment ev;
	@Autowired
	BidSupplierService bidSupplierService;
	@Autowired
	SubDivWorkBillService workBillService;
	
	private static Logger log = LoggerFactory.getLogger(TenderPlanController.class);
	
	/**
	 * 查询交易活动类型、招 标 方 式、招标计划状态
	 * @param enumName
	 * @return
	 */
	@RequestMapping("/getBizTypeTenderWayStatusList")
	@ResponseBody
	public Map<String, List<EnumVar>> getBizTypeTenderWayStatusList(HttpServletRequest request) {
		Map<String, List<EnumVar>> map = new HashMap<String, List<EnumVar>>(); 
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			DynamicDataSourceContextHolder.set(parentTenantAccount);
			List<EnumVar> bizTypeList = enumVarService.findByEnumName("BizType");
			map.put("BizType", bizTypeList);
			List<EnumVar> tenderWayList = enumVarService.findByEnumName("TenderWay");
			map.put("TenderWay", tenderWayList);
			List<EnumVar> statusList = enumVarService.findByEnumName("TenderPlanStatus");
			map.put("Status", statusList);
			return map;
		} catch (UtilException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return map;
		}
	}
	
	/**
	 * 获取所有发布的招标计划
	 * @return Object
	 */
	@RequestMapping("/getTotalReleaseTenderPlan")
	public Object getTotalReleaseTenderPlan(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("tenderPlan/releaseTenderList");
		try {
			List<Map<String, String>> dataMap = new ArrayList<Map<String, String>>();
			//获取租户列表信息
			JSONObject data = restTemplate
    				.getForEntity(ev.getProperty("pscm.tenant.rest.url") + "/tenant/getTenantList", JSONObject.class)
    				.getBody();
			JSONArray tenantList = (JSONArray) data.getJSONArray("data");
			//获取公司级租户的实体名称
			Map<String, String> parentTenantReal = new HashMap<String, String>();
			for (int i = 0; i < tenantList.size(); i++) {
				JSONObject tenant = (JSONObject) tenantList.get(i);
				Integer tenantType = (Integer) tenant.get("tenantType");
				//租户编码
				String tenantCode = (String) tenant.get("tenantCode");
				//公司级租户
				if (tenantType.intValue() == 1) {
					//租户实体
					JSONObject tenantReal = (JSONObject) tenant.getJSONObject("tenantReal");
					if (tenantReal != null) {
						parentTenantReal.put(tenantCode, (String) tenantReal.get("name"));
					}
				}
			}
			for (int i = 0; i < tenantList.size(); i++) {
				JSONObject tenant = (JSONObject) tenantList.get(i);
				Integer tenantType = (Integer) tenant.get("tenantType");
				//项目级租户
				if (tenantType.intValue() == 0) {
					//租户实体
					JSONObject tenantReal = (JSONObject) tenant.getJSONObject("tenantReal");
					//租户账号
					String parentCode = (String) tenant.get("parentCode");
					String title = parentTenantReal.get(parentCode) != null ? parentTenantReal.get(parentCode) : "";
					if (tenantReal != null) {
						//项目部名称
						title += (String) tenantReal.get("name");
					}
					//租户账号
					String account = (String) tenant.get("account");
					//切换数据源
					DynamicDataSourceContextHolder.set(account);
					//查询当前发布的招标计划
					List<TenderPlan> tpList = tenderService.findTenderPlanRelease();
					for (TenderPlan tp : tpList) {
						Map<String, String> m = new HashMap<String, String>();
						m.put("tenderPlanCode", tp.getTenderPlanCode());
						m.put("account", account);
						m.put("title", title + tp.getWorkItemName());
						dataMap.add(m);
					}
				}
			}
			mv.addObject("data", dataMap);
			return mv;
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
			return null;
		} catch (Exception e) {
			log.error("Exception异常：", e);
			return null;
		}
	}
	
	/**
	 * 获取全部招标计划
	 * @return Object
	 */
	@RequestMapping("/getAllTenderPlan")
	@ResponseBody
	public Object getAllTenderPlan(HttpServletRequest request){
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			String tenantCode = SessionUtil.getSessionValue(request, SessionUtil.CURRENT_TENANT_CODE);
			String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
			List<TenderPlanWithBLOBs> findAll = tenderService.findAll(parentTenantAccount);
			for (TenderPlanWithBLOBs tp : findAll) {
				//状态是审批中的状态
				if (tp.getStatus().getEnumValue().intValue() > Constants.WF_STATUS_DRAFT && tp.getStatus().getEnumValue().intValue() < Constants.WF_STATUS_FINISH) {
					Task unsignedTasks = taskService.createTaskQuery().processInstanceBusinessKey(ProcessDefinitionKey.TENDER_PLAN + "-" + tp.getTenderPlanCode()).taskCandidateUser(userCode + "-" + tenantCode).singleResult();
		        	if (unsignedTasks != null) {
		        		tp.setTaskId(unsignedTasks.getId());
			        } else {
						Task todoTask = taskService.createTaskQuery().processInstanceBusinessKey(ProcessDefinitionKey.TENDER_PLAN + "-" + tp.getTenderPlanCode()).taskAssignee(userCode + "-" + tenantCode).singleResult();
				        if (todoTask != null) {
				        	tp.setTaskId(todoTask.getId());
				        }
			        }
				}
			}
			data.setData(findAll);
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
	 * 可发布的招标计划
	 * @return Object
	 */
	@RequestMapping("/releaseTenderPlan")
	@ResponseBody
	public Object releaseTenderPlan(HttpServletRequest request){
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			List<TenderPlanWithBLOBs> findAll = tenderService.findAll(parentTenantAccount);
			List<TenderPlanWithBLOBs> relList = new ArrayList<>();
			for(int i=0; i<findAll.size(); i++){
				if(findAll.get(i).getStatus().getEnumValue().intValue() == Constants.WF_STATUS_FINISH || findAll.get(i).getStatus().getEnumValue().intValue() == Constants.TENDER_PLAN_WF_STATUS_RELEASE){
					relList.add(findAll.get(i));
				}
			}
			data.setData(relList);
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
	 * 根据编号获取招标计划
	 * @param tenderPlanCode
	 * @return Object
	 */
	@RequestMapping("/findTenderPlanByCode")
	@ResponseBody
	public Object findTenderPlanByCode(HttpServletRequest request, String tenderPlanCode){
		TenderPlanWithBLOBs bs = new TenderPlanWithBLOBs();
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			Map<String, String> map = new HashMap<String, String>();
			map.put("parentTenantAccount", parentTenantAccount);
			map.put("tenderPlanCode", tenderPlanCode);
			bs = tenderService.selectByPrimaryKey(map);
		} catch (TenderPlanException e) {
			log.error("TenderPlanException异常：", e);
		} catch (Exception e) {
			log.error("Exception异常：", e);
		}
		return bs;
	}
	
	/**
	 * 增加或修改招标计划
	 * @param tenderPlanWithBLOBs
	 * @param flag
	 * @return Map
	 */
	@RequestMapping("/addOrUpdateTenderPlan")
	@ResponseBody
	public ResultVO addOrUpdateTenderPlan(HttpServletRequest request, TenderPlanWithBLOBs tenderPlanWithBLOBs,String flag, String supplierJson){
		try {
			List<BidSupplier> supplierList = JSON.parseArray(supplierJson, BidSupplier.class);
			if("I".equals(flag)){//新增
				EnumVar status = new EnumVar(Constants.WF_STATUS_DRAFT);
				tenderPlanWithBLOBs.setStatus(status);
				tenderPlanWithBLOBs.setTenderPlanCode(String.valueOf((new Date()).getTime()));
				tenderService.saveTenderPlanSelective(tenderPlanWithBLOBs, supplierList);
				return ResultVOUtil.success(ResultEnum.SAVE_SUCCESS.getMessage());
			}else if("U".equals(flag) && (tenderPlanWithBLOBs.getTenderPlanCode()!=null && !"".equals(tenderPlanWithBLOBs.getTenderPlanCode()))){
				String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
				Map<String, String> queryMap = new HashMap<String, String>();
				queryMap.put("parentTenantAccount", parentTenantAccount);
				queryMap.put("tenderPlanCode", tenderPlanWithBLOBs.getTenderPlanCode());
				TenderPlanWithBLOBs bloBs = tenderService.selectByPrimaryKey(queryMap);
				if (bloBs.getStatus().getEnumValue().intValue() != Constants.WF_STATUS_DRAFT) {
					log.error("已经提交不可以修改");
					return ResultVOUtil.error(ResultEnum.SUBMIT_CANNOT_MODIFY.getMessage());
				}
				bloBs.setWorkItemName(tenderPlanWithBLOBs.getWorkItemName());
				bloBs.setBizType(tenderPlanWithBLOBs.getBizType());
				bloBs.setTenderWay(tenderPlanWithBLOBs.getTenderWay());
				bloBs.setPlanSupplierNumber(tenderPlanWithBLOBs.getPlanSupplierNumber());
				bloBs.setTenderPlanDate(tenderPlanWithBLOBs.getTenderPlanDate());
				bloBs.setLatestMarchinDate(tenderPlanWithBLOBs.getLatestMarchinDate());
				bloBs.setMainContents(tenderPlanWithBLOBs.getMainContents());
				bloBs.setRequirements(tenderPlanWithBLOBs.getRequirements());
				tenderService.updateByPrimaryKeyWithBLOBS(bloBs, supplierList);
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
	 * 根据编号删除招标计划
	 * @param tenderPlanCode
	 * @return Map
	 */
	@RequestMapping("/deleteTenderPlan")
	@ResponseBody
	public ResultVO deleteTenderPlan(HttpServletRequest request, String tenderPlanCode){
		try {
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			Map<String, String> queryMap = new HashMap<String, String>();
			queryMap.put("parentTenantAccount", parentTenantAccount);
			queryMap.put("tenderPlanCode", tenderPlanCode);
			TenderPlanWithBLOBs bs = tenderService.selectByPrimaryKey(queryMap);
			if (bs != null) {
				if(bs.getStatus().getEnumValue().intValue()==Constants.WF_STATUS_DRAFT){
					int i = tenderService.deleteByPrimaryKey(tenderPlanCode);
					if(i==0){
						log.error("删除失败");
						return ResultVOUtil.error(ResultEnum.DELETE_FAIL.getMessage());
					}else{
						return ResultVOUtil.success(ResultEnum.DELETE_SUCCESS.getMessage());
					}
				} else {
					log.error("已经提交不可删除");
					return ResultVOUtil.error(ResultEnum.SUBMIT_CANNOT_DELETE.getMessage());
				}
			} else {
				log.error("招标计划不存在");
				return ResultVOUtil.error(ResultEnum.TENDER_PLAN_NOT_EXIST.getMessage());
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
	 * 查询已经发布的招标计划
	 * @return
	 */
	@RequestMapping("/findApprovalFinishTenderPlan")
	@ResponseBody
	public List<TenderPlanWithBLOBs> findApprovalFinishTenderPlan() {
		try {
			String sqlWhere = "status=" + Constants.TENDER_PLAN_WF_STATUS_RELEASE;
			List<TenderPlanWithBLOBs> bs = tenderService.findTenderPlanBySqlWhere(sqlWhere);
			return bs;
		} catch (TenderPlanException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 提交招标计划
	 * @param tenderPlanCode
	 * @param request
	 * @return
	 */
	@RequestMapping("/startProcessInstance")
	@ResponseBody
	public ResultVO startProcessInstance(String tenderPlanCode, HttpServletRequest request) {
		try {
			String userName = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserName();
			Map<String, Object> variables = new HashMap<String, Object>();
			//父租户（公司级）
			String parentTenantAccount = SessionUtil.getSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT);
			//当前租户
			String tenantCode = SessionUtil.getSessionValue(request, SessionUtil.CURRENT_TENANT_CODE);
			variables.put("parentTenantAccount", parentTenantAccount);
			variables.put("tenantCode", tenantCode);
			
			variables.put("tenderPlanCode", tenderPlanCode);
			TenderPlanWithBLOBs bs = tenderService.selectByPrimaryKey(variables);
			if (bs != null) {
				if (bs.getStatus().getEnumValue().intValue() == Constants.WF_STATUS_DRAFT) {
					//判断是否维护供方
					variables.put("sqlWhere", "where tender_plan_code = '" + tenderPlanCode + "'");
					List<BidSupplier> bidList = bidSupplierService.findBidSupplierBySqlWhere(variables);
					if (bidList.size() == 0) {
						log.error("招标计划的供方没有维护");
						return ResultVOUtil.error(ResultEnum.TENDER_PLAN_SUPPLIER_NOT_EXIST.getMessage());
					}
					//判断是否维护清单
					List<SubDivWorkBill> billList = workBillService.findSubBySqlWhere("tender_plan_code='" + tenderPlanCode + "'");
					if (billList.size() == 0) {
						log.error("招标计划的清单没有维护");
						return ResultVOUtil.error(ResultEnum.TENDER_PLAN_BILL_NOT_EXIST.getMessage());
					}
					//上报人
					variables.put("reportUser", userName);
					workFlowService.startProcessInstanceByKey(ProcessDefinitionKey.TENDER_PLAN, ProcessDefinitionKey.TENDER_PLAN + "-" + tenderPlanCode, variables);
					return ResultVOUtil.success(ResultEnum.SUBMIT_SUCCESS.getMessage());
				} else {
					log.error("招标计划已经提交");
					return ResultVOUtil.error(ResultEnum.SUBMIT_CANNOT_SUBMIT.getMessage());
				}
				
			} else {
				log.error("招标计划不存在");
				return ResultVOUtil.error(ResultEnum.TENDER_PLAN_NOT_EXIST.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultVOUtil.error(e.getMessage());
		}
	}
}

