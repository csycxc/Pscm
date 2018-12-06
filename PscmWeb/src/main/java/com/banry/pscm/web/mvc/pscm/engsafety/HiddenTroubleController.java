package com.banry.pscm.web.mvc.pscm.engsafety;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTrouble;
import com.banry.pscm.service.engsafety.HiddenTroubleService;
import com.banry.pscm.service.schedule.EngDivision;
import com.banry.pscm.service.schedule.EngDivisionService;
import com.banry.pscm.service.schedule.ScheduleException;
import com.banry.pscm.service.util.EnumVar;
import com.banry.pscm.service.workflow.ApprovalTrack;
import com.banry.pscm.service.workflow.WorkFlowService;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.workflow.util.ProcessDefinitionKey;

@Controller
@RequestMapping("/hiddenTrouble")
public class HiddenTroubleController {
	
    @Autowired
	private HiddenTroubleService hiddenTroubleService;
	@Autowired
	private EngDivisionService engDivisionService;
	@Autowired
    private RuntimeService runtimeService;
	@Autowired
    private TaskService taskService;
	@Autowired
	private WorkFlowService workFlowService;
//	@Autowired
//	private EnumVarService enumVarService;
	
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder bin) {
		SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
		CustomDateEditor cust =new CustomDateEditor (sdf,true);
		bin.registerCustomEditor(Date.class,cust);
	}

	
	@RequestMapping("/getHiddenTroubleList")
	@ResponseBody
	public Object getHiddenTroubleList(HttpServletRequest request, String divSnCode) {
		DataTableModel data = new DataTableModel();
		try {
			if (divSnCode != null && !"".equals(divSnCode)) {
				String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				List<HiddenTrouble> listGrid = hiddenTroubleService.findHiddenTroubleBySqlWhere("a.division_sn_code='" + divSnCode + "'", parentTenantAccount);
				//查询字典表中的隐患级别
//		        List<EnumVar> tlLst = new ArrayList<EnumVar>();
//		        try {
//					tlLst = enumVarService.findByEnumName("TroubleLevel");
//				} catch (UtilException e) {
//					e.printStackTrace();
//				}
				data.setData(listGrid);
			} else {
				data.setData(new ArrayList<HiddenTrouble>());
			}
			return data;
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
	
	/**
	 * 获取当前待处理的隐患列表
	 * @author Xu Dingkui
	 * @date 2017年11月21日
	 * @param divSnCode
	 * @return
	 */
	@RequestMapping("/getMyTaskList")
	@ResponseBody
	public Object getMyTaskList(HttpServletRequest request, String divSnCode) {
		DataTableModel data = new DataTableModel();
		try {
			if (divSnCode != null && !"".equals(divSnCode)) {
				String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
				String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
				List<HiddenTrouble> results = new ArrayList<HiddenTrouble>();
				//获取自己创建且未提交的
//				List<HiddenTrouble> results = hiddenTroubleService.findHiddenTroubleBySqlWhere("a.status=0 and a.division_sn_code='" + divSnCode + "'");
		        List<Task> tasks = new ArrayList<Task>();
		        // 根据当前人的ID查询
		        List<Task> todoList = taskService.createTaskQuery().processDefinitionKey(ProcessDefinitionKey.HIDDENTROUBLE).taskAssignee(userCode + "-" + tenantCode).list();
		        // 根据当前人未签收的任务
		        List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey(ProcessDefinitionKey.HIDDENTROUBLE).taskCandidateUser(userCode + "-" + tenantCode).list();
		        // 合并
		        tasks.addAll(todoList);
		        tasks.addAll(unsignedTasks);
		        
		        //查询字典表中的隐患级别
//		        List<EnumVar> tlLst = new ArrayList<EnumVar>();
//		        try {
//					tlLst = enumVarService.findByEnumName("TroubleLevel");
//				} catch (UtilException e) {
//					e.printStackTrace();
//				}
		        
		        // 根据流程的业务ID查询实体并关联
		        for (Task task : tasks) {
		            String processInstanceId = task.getProcessInstanceId();
		            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		            String businessKey = processInstance.getBusinessKey();
		            String pdkAndId[] = businessKey.split("-");
			        if (pdkAndId.length != 2) {
			        	continue;
			        }
		            HiddenTrouble entity = hiddenTroubleService.findHiddenTroubleByPrimaryKey(pdkAndId[1], parentTenantAccount);
		            if (entity != null && divSnCode.equals(entity.getDivisionSnCode())) {
			            entity.setTaskId(task.getId());
			            entity.setTaskName(task.getName());
//			            entity.setTaskAssignee(task.getAssignee());
//			            
//			            //将隐患的级别改为字符串显示
//			            for (EnumVar dd : tlLst) {
//							if(entity.getTrobulelevel().equals(String.valueOf(dd.getEnumValue()))) {
//								entity.setTrobulelevel(String.valueOf(dd.getEnumValueName()));
//							}
//						}
			            results.add(entity);
		            }
		        }
		        data.setData(results);
			} else {
				data.setData(new ArrayList<HiddenTrouble>());
			}
			return data;
		}catch (EngsafetyException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 我的个人事务
	 * @return  List<Task>
	 */
	@RequestMapping("/getUnTreatedMes")
	@ResponseBody
	public Object getUnTreatedMes(HttpServletRequest request){
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
			//获取自己创建且未提交的
			List<HiddenTrouble> results = hiddenTroubleService.findHiddenTroubleBySqlWhere("a.status=1", parentTenantAccount);
	        List<Task> tasks = new ArrayList<Task>();
	        // 根据当前人的ID查询
	        List<Task> todoList = taskService.createTaskQuery().processDefinitionKey(ProcessDefinitionKey.HIDDENTROUBLE).taskAssignee(userCode + "-" + tenantCode).list();
	        // 根据当前人未签收的任务
	        List<Task> unsignedTasks = taskService.createTaskQuery().processDefinitionKey(ProcessDefinitionKey.HIDDENTROUBLE).taskCandidateUser(userCode + "-" + tenantCode).list();
	        // 合并
	        tasks.addAll(todoList);
	        tasks.addAll(unsignedTasks);
	        // 根据流程的业务ID查询实体并关联
	        for (Task task : tasks) {
	            String processInstanceId = task.getProcessInstanceId();
	            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	            String businessKey = processInstance.getBusinessKey();
	            HiddenTrouble entity = hiddenTroubleService.findHiddenTroubleByPrimaryKey(businessKey, parentTenantAccount);
	            if (entity != null) {
		            entity.setTaskId(task.getId());
		            entity.setTaskName(task.getName());
//		            entity.setTaskAssignee(task.getAssignee());
		            results.add(entity);
	            }
	        }
	        data.setData(results);
	        return data;
		} catch (Exception e) {
			return new Exception("隐患异常"+e);
		}
	}
	
	/**
	 * 隐患上报
	 * @author Xu Dingkui
	 * @date 2017年8月13日
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/startProcessInstance")
	@ResponseBody
	public Object startProcessInstance(HiddenTrouble htt, String flag, HttpServletRequest request) {
		Map map = new HashMap();
		try {
			String userName = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserName();
			Map<String, Object> variables = new HashMap<String, Object>();
			//新增
			if ("I".equals(flag)) {
				htt.setTroubleCode(String.valueOf((new Date()).getTime()));
				//状态自由
				EnumVar s = new EnumVar();
	        	s.setEnumValue(1);
				htt.setStatus(s);
			}
			//父租户（公司级）
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			//当前租户
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			hiddenTroubleService.startProcessInstance(htt, ProcessDefinitionKey.HIDDENTROUBLE, userName, tenantCode, parentTenantAccount);
			map.put("result", true);
			map.put("retMsg", "");
			map.put("treacode", htt.getTroubleCode());
			return map;
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
//		} catch (ScheduleException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			map.put("result", false);
//			map.put("retMsg", e.getMessage());
//			return map;
		}
	}
	
	/**
     * 签收任务
     */
    @RequestMapping(value = "task/claim/{id}")
    @ResponseBody
    public Map claim(@PathVariable("id") String taskId) {
    	Map map = new HashMap();
    	String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
    	Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
        // 判断任务是否已经被签收
        if(task.getAssignee()==null){
        	taskService.claim(taskId, userCode);
        	map.put("result", true);
    		map.put("retMsg", "签收成功");
        } else {
        	map.put("result", false);
    		map.put("retMsg", "已经被认领");
        }
		return map;
    }

    /**
     * 任务列表
     *
     * @param leave
     */
    @RequestMapping(value = "task/view/{taskId}")
    public ModelAndView showTaskView(HttpServletRequest request, @PathVariable("taskId") String taskId) {
    	try {
	        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
	        if (task != null) {
		        String processInstanceId = task.getProcessInstanceId();
		        String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
		        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		        String pdkAndId[] = processInstance.getBusinessKey().split("-");
		        if (pdkAndId.length == 2) {
		        	HiddenTrouble htt = hiddenTroubleService.findHiddenTroubleByPrimaryKey(pdkAndId[1], parentTenantAccount);
			        ModelAndView mav = new ModelAndView("engsafety/task-" + task.getFormKey());
			        mav.addObject("htt", htt);
			        mav.addObject("task", task);
			        return mav;
		        } else {
		        	ModelAndView mav = new ModelAndView("engsafety/task-error");
		        	return mav;
		        }
	        } else {
	        	ModelAndView mav = new ModelAndView("engsafety/task-error");
	        	return mav;
	        }
	        
	    } catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

    /**
     * 完成任务
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "task/complete/{id}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Map complete(@PathVariable("id") String taskId, //@RequestParam(value = "saveEntity", required = false) String saveEntity,
                           @ModelAttribute("preloadLeave") HiddenTrouble htt, HttpServletRequest request) {
//        boolean saveEntityBoolean = BooleanUtils.toBoolean(saveEntity);
        Map<String, Object> variables = new HashMap<String, Object>();
        Enumeration<String> parameterNames = request.getParameterNames();
        Map map = new HashMap(); 
        try {
        	String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
        	String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
        	HiddenTrouble entity = hiddenTroubleService.findHiddenTroubleByPrimaryKey(htt.getTroubleCode(), parentTenantAccount);
            while (parameterNames.hasMoreElements()) {
                String parameterName = (String) parameterNames.nextElement();
                if (parameterName.startsWith("p_")) {
                    // 参数结构：p_B_name，p为参数的前缀，B为类型，name为属性名称
                    String[] parameter = parameterName.split("_");
                    if (parameter.length == 3) {
                        String paramValue = request.getParameter(parameterName);
                        Object value = paramValue;
                        if (parameter[1].equals("B")) {
                            value = BooleanUtils.toBoolean(paramValue);
                        } else if (parameter[1].equals("DT")) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            value = sdf.parse(paramValue);
                        } else if (parameter[1].equals("L")) {
                            value = Long.parseLong(paramValue);
                        }
                        //整改延期
                        if ("p_L_rectifypostpone".equals(parameterName)) {
                        	entity.setRectifyPostpone(Integer.parseInt(paramValue));
                        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        	Calendar cl = Calendar.getInstance();
                            cl.setTime(entity.getRectifyTimeLimit());
                            //延期后的期限
                            cl.add(Calendar.DATE, Integer.parseInt(paramValue) + entity.getRectifyPostpone());
                        	variables.put("duTime", sdf.format(cl.getTime()) + "T23:59:59");
                        }
                        variables.put(parameter[2], value);
                    } else {
                        throw new RuntimeException("invalid parameter for activiti variable: " + parameterName);
                    }
                } else {
                	if ("flag".equals(parameterName)) {
                		String paramValue = request.getParameter(parameterName);
                		//整改
                		if ("rectify".equals(paramValue)) {
                			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                			String now = sdf.format(new Date());
                			Calendar c = Calendar.getInstance();
                			c.setTime(htt.getRectifyTimeLimit());
                			//整改期限+延期天数
                			c.add(Calendar.DAY_OF_YEAR, htt.getRectifyPostpone());
                			if (c.getTime().before(sdf.parse(now))) {
                				variables.put("timeoutFlag", true);
                			} else {
                				variables.put("timeoutFlag", false);
                			}
                			variables.put("safetyOfficer", userCode);
                			entity.setRectifyTime(sdf.parse(now));
                		}
                	}
                }
            }
            workFlowService.complete(taskId, variables, userCode);
            map.put("result", true);
			map.put("retMsg", "");
			return map;
        } catch (Exception e) {
        	map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
        }
    }

	@RequestMapping("/edit")
	public Object edit(String troubleCode, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("engsafety/hiddenTrouble-edit");
		try {
			//编辑时获取隐患信息
			if (troubleCode != null && !"".equals(troubleCode)) {
				String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				HiddenTrouble r = hiddenTroubleService.findHiddenTroubleByPrimaryKey(troubleCode, parentTenantAccount);
				if (r != null) {
					if (r.getStatus().getEnumValue().intValue() == 0) {
						mv.addObject("r", r);
						mv.addObject("flag", "U");
						mv.addObject("msg", "");
					} else {
						mv.addObject("msg", "隐患已上报");
					}
				} else {
					mv.addObject("msg", "隐患不存在");
				}
				
			} else {
				mv.addObject("flag", "I");
				mv.addObject("msg", "");
			}
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 上报编辑
	 * @author chenshiyu
	 * @date 2018年1月11日
	 * @param troubleCode
	 * @param divsncode, divitemcode
	 * @return
	 */
	@RequestMapping("/editToReport2")
	public Object editToReport2(String troubleCode, String divsncode, String divItemCode,String investContent) {
		ModelAndView mv = new ModelAndView("engsafety/hiddenTrouble-editToReport");
		mv.addObject("troubleCode", troubleCode);//隐患编码
		mv.addObject("divisionSnCode", divsncode);//工序主键
		mv.addObject("divItemCode", divItemCode);//划分项编码
		String takePlace = "";//隐患发生地点
		if(divsncode != null && !"".equals(divsncode)) {//测试时，divsncode的值为  1.1.1.1
			//根据子划分的主键divsncode    查找其父划分至跟节点       例：锁口圈梁    隐患发生地点是      金融街站-竖井及连通道-1号竖井1.1.1.1
			String[] arr = divsncode.split("\\.");
			if(arr.length>2) {
				String s1 = arr[0];
				String s2 = s1+"."+arr[1];
				String s3 = s2+"."+arr[2];
				String divname1 = "";
				String divname2 = "";
				String divname3 = "";
				try {
					List<EngDivision> all = engDivisionService.findAllEngDivision();
					for (EngDivision eng : all) {
						if(eng.getDivisionSnCode().equals(s1)) {
							divname1 = eng.getDivName();
						}
						if(eng.getDivisionSnCode().equals(s2)) {
							divname2 = eng.getDivName();
						}
						if(eng.getDivisionSnCode().equals(s3)) {
							divname3 = eng.getDivName();
						}
					}
					takePlace = divname1+">"+divname2+">"+divname3;
				} catch (ScheduleException e) {
					e.printStackTrace();
				}
			}
		}
		mv.addObject("takePlace", takePlace);
		mv.addObject("investContent", investContent);//排查内容 
		return mv;
	}
	
	/**
	 * 上报编辑
	 * @author Xu Dingkui
	 * @date 2017年9月16日
	 * @param troublecode
	 * @param divsncode, divitemcode
	 * @return
	 */
	@RequestMapping("/editToReport")
	public Object editToReport(String troublecode, String divsncode, String divitemcode) {
		ModelAndView mv = new ModelAndView("engsafety/hiddenTrouble-editToReport");
		mv.addObject("troublecode", troublecode);
		mv.addObject("divsncode", divsncode);
		mv.addObject("divitemcode", divitemcode);
		return mv;
	}
	
	@RequestMapping("/view")
	public Object view(String troubleCode, HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("engsafety/hiddenTrouble-view");
		try {
			//编辑时获取隐患信息
			if (troubleCode != null && !"".equals(troubleCode)) {
				String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
				HiddenTrouble htt = hiddenTroubleService.findHiddenTroubleByPrimaryKey(troubleCode, parentTenantAccount);
				mv.addObject("htt", htt);
			}
		} catch (EngsafetyException e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	/**
	 * 获取审批记录
	 * 
	 * @author Xu Dingkui
	 * @date 2017年8月26日
	 * @param troubleCode
	 * @return
	 */
	@RequestMapping("/getTroubleTrackList")
	@ResponseBody
	public Object getTroubleTrackList(String troubleCode, HttpServletRequest request) {
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			List<ApprovalTrack> listGrid = workFlowService.getHisUserTaskActivityInstanceList(ProcessDefinitionKey.HIDDENTROUBLE + "-" + troubleCode, tenantCode, parentTenantAccount);
			data.setData(listGrid);
			return data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/saveHiddenTrouble")
	@ResponseBody
	public Map saveHiddenTrouble(HiddenTrouble ht, String flag) {
		Map map = new HashMap();
		try {
			//新增
			if ("I".equals(flag)) {
				String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
				ht.setTroubleCode(String.valueOf((new Date()).getTime()));
				EnumVar s = new EnumVar();
				s.setEnumValue(0);
				ht.setStatus(s);;
			}
			hiddenTroubleService.saveHiddenTrouble(ht);
			map.put("result", true);
			map.put("retMsg", "");
			map.put("treacode", ht.getTroubleCode());
			return map;
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			map.put("result", false);
			map.put("retMsg", e.getMessage());
			return map;
		}
	}
	
	@RequestMapping("/deleteHiddenTrouble")
	@ResponseBody
	public String deleteHiddenTrouble(String troubleCodes) {
		try {
			hiddenTroubleService.deleteHiddenTrouble(troubleCodes);
			return "{success:true}";
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "{success:true, retMsg:'" + e.getMessage() + "'}";
		}
	}
	
}
