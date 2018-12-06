package com.banry.pscm.web.mvc.pscm.activiti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDiagramCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.persistence.entity.HistoricProcessInstanceEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.banry.pscm.account.CustomUserDetails;
import com.banry.pscm.service.workflow.ApprovalTrack;
import com.banry.pscm.service.workflow.WorkFlowService;
import com.banry.pscm.web.mvc.model.DataTableModel;
import com.banry.pscm.web.mvc.model.DeploymentResponse;
import com.banry.pscm.workflow.util.ProcessDefinitionKey;

@Controller
@RequestMapping("/activiti")
public class ActivitiController {

	@Autowired
    private RepositoryService repositoryService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private FormService formService;
	@Autowired
	private WorkFlowService workFlowService;

	/**
	 * 列出所有流程模板
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(ModelAndView mav) {
		mav.addObject("list", com.banry.pscm.workflow.Util.list());
		mav.setViewName("process/template");
		return mav;
	}

	/**
	 * 部署流程
	 */
	@RequestMapping("deploy")
	public ModelAndView deploy(String processName, ModelAndView mav) {

		RepositoryService service = processEngine.getRepositoryService();

		if (null != processName)
			service.createDeployment()
					.addClasspathResource("diagrams/" + processName).deploy();

		List<ProcessDefinition> list = service.createProcessDefinitionQuery()
				.list();

		mav.addObject("list", list);
		mav.setViewName("process/deployed");
		return mav;
	}

	/**
	 * 启动一个流程实例
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("start")
	public ModelAndView start(String id, ModelAndView mav) {

		RuntimeService service = processEngine.getRuntimeService();

		service.startProcessInstanceById(id);

		List<ProcessInstance> list = service.createProcessInstanceQuery()
				.list();

		mav.addObject("list", list);
		mav.setViewName("process/started");

		return mav;
	}

	/**
	 * 所有已启动流程实例
	 */
	@RequestMapping("started")
	public ModelAndView started(ModelAndView mav) {

		RuntimeService service = processEngine.getRuntimeService();

		List<ProcessInstance> list = service.createProcessInstanceQuery()
				.list();

		mav.addObject("list", list);
		mav.setViewName("process/started");

		return mav;
	}
	
	@RequestMapping("task")
	public ModelAndView task(ModelAndView mav){
		TaskService service=processEngine.getTaskService();
		List<Task> list=service.createTaskQuery().list();
		mav.addObject("list", list);
		mav.setViewName("process/task");
		return mav;
	}
	
	@RequestMapping("complete")
	public ModelAndView complete(ModelAndView mav,String id){
		
		TaskService service=processEngine.getTaskService();
		
		service.complete(id);
		
		return new ModelAndView("redirect:task");
	}

	/**
	 * 所有已启动流程实例
	 * 
	 * @throws IOException
	 */
	@RequestMapping("graphics")
	public void graphics(String definitionId, String instanceId,
			String taskId, ModelAndView mav, HttpServletResponse response)
			throws IOException {
		
		response.setContentType("image/png");
		Command<InputStream> cmd = null;

		if (definitionId != null) {
			cmd = new GetDeploymentProcessDiagramCmd(definitionId);
		}

		if (instanceId != null) {
			cmd = new ProcessInstanceDiagramCmd(instanceId);
		}

		if (taskId != null) {
			Task task = processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
			cmd = new ProcessInstanceDiagramCmd(
					task.getProcessInstanceId());
		}

		if (cmd != null) {
			InputStream is = processEngine.getManagementService().executeCommand(cmd);
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = is.read(b, 0, 1024)) != -1) {
				response.getOutputStream().write(b, 0, len);
			}
		}
	}
	
	/**
	 * 模型列表
	 * @return
	 */
	@RequestMapping("/modelList")
	@ResponseBody
	public Object modelList() {
		DataTableModel data = new DataTableModel();
		try {
			List<Model> modelList1 = repositoryService.createModelQuery()
					.list();
			data.setData(modelList1);
			return data;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return data;
	}
	
	/**
	 * 已部署流程列表
	 */
	@RequestMapping("deploymentList")
	@ResponseBody
	public Object deployed() {
		DataTableModel data = new DataTableModel();
		try {
			List<Deployment> deployments = repositoryService.createDeploymentQuery()
					.list();
			List<DeploymentResponse> list = new ArrayList<>();
	        for(Deployment deployment: deployments){
	            list.add(new DeploymentResponse(deployment));
	        }
			data.setData(list);
			return data;
		} catch (Exception e) {
			e.getStackTrace();
		}
		return data;
	}
	
	@RequestMapping(value = "/read-resource")
	public void readResource(String processDefinitionId, String resourceName,String businessKey, HttpServletResponse response)
	        throws Exception {
	    // 设置页面不缓存
	    response.setHeader("Pragma", "No-cache");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setDateHeader("Expires", 0);
	    businessKey = processDefinitionId + "-" + businessKey;
	    ProcessDefinitionQuery pdq = repositoryService.createProcessDefinitionQuery();
	    ProcessDefinition pd = pdq.processDefinitionId(processDefinitionId).singleResult();

	    if(resourceName.endsWith(".png") && StringUtils.isEmpty(businessKey) == false)
	    {
	    	RuntimeService service = processEngine.getRuntimeService();
	    	ProcessInstance processInstance = service.createProcessInstanceQuery().processInstanceBusinessKey(businessKey).singleResult();
	    	String processInstanceId = null;
			if (processInstance != null) {
				processInstanceId = processInstance.getProcessInstanceId();
			} else {
				//流程已经结束查询历史实例
				HistoricProcessInstanceEntity hisProcessInstance = (HistoricProcessInstanceEntity) historyService.createHistoricProcessInstanceQuery()
						.processInstanceBusinessKey(businessKey).singleResult();
				if (hisProcessInstance != null) {
					//历史流程实例id
					processInstanceId = hisProcessInstance.getProcessInstanceId();
				}
			}
	    	getActivitiProccessImage(processInstanceId,response);
	    }
	    else
	    {
	        // 通过接口读取
	        InputStream resourceAsStream = repositoryService.getResourceAsStream(pd.getDeploymentId(), resourceName);

	        // 输出资源内容到相应对象
	        byte[] b = new byte[1024];
	        int len = -1;
	        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
	            response.getOutputStream().write(b, 0, len);
	        }
	    }
	}


	/**
	 * 获取流程图像，已执行节点和流程线高亮显示
	 */
	public void getActivitiProccessImage(String pProcessInstanceId, HttpServletResponse response)
	{
	    try {
	        //  获取历史流程实例
	        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
	                .processInstanceId(pProcessInstanceId).singleResult();

	        if (historicProcessInstance != null) 
	        {
	            // 获取流程定义
	            ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
	                    .getDeployedProcessDefinition(historicProcessInstance.getProcessDefinitionId());

	            // 获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
	            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
	                    .processInstanceId(pProcessInstanceId).orderByHistoricActivityInstanceId().asc().list();

	            // 已执行的节点ID集合
	            List<String> executedActivityIdList = new ArrayList<String>();
	            int index = 1;
	            //获取已经执行的节点ID
	            for (HistoricActivityInstance activityInstance : historicActivityInstanceList) {
	                executedActivityIdList.add(activityInstance.getActivityId());                
	                index++;
	            }

	            // 已执行的线集合
	            List<String> flowIds = new ArrayList<String>();
	            // 获取流程走过的线 
	            flowIds = getHighLightedFlows(processDefinition, historicActivityInstanceList);


	            BpmnModel bpmnModel = repositoryService
	                    .getBpmnModel(historicProcessInstance.getProcessDefinitionId());
	            // 获取流程图图像字符流
//	            ProcessDiagramGenerator pec = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator();
	            //配置字体
	            InputStream imageStream = new DefaultProcessDiagramGenerator().generateDiagram(bpmnModel, "png", executedActivityIdList, flowIds,
	            		processEngine.getProcessEngineConfiguration().getActivityFontName(),
	            		processEngine.getProcessEngineConfiguration().getLabelFontName(), 
	            		processEngine.getProcessEngineConfiguration().getAnnotationFontName(), null, 1.0);

	            response.setContentType("image/png");
	            OutputStream os = response.getOutputStream();
	            int bytesRead = 0;
	            byte[] buffer = new byte[8192];
	            while ((bytesRead = imageStream.read(buffer, 0, 8192)) != -1) {
	                os.write(buffer, 0, bytesRead);
	            }
	            os.close();
	            imageStream.close();
	        }        
	    } catch (Exception e) {        
	    }
	}


	/**
	 * *获取流程走过的线 

	 */
	public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinitionEntity, List<HistoricActivityInstance> historicActivityInstances) {
		List<String> highFlows = new ArrayList<String>();// 用以保存高亮的线flowId    
		for (int i = 0; i < historicActivityInstances.size() - 1; i++) {
			// 对历史流程节点进行遍历       
			ActivityImpl activityImpl = processDefinitionEntity.findActivity(historicActivityInstances.get(i).getActivityId());
			// 得到节点定义的详细信息   
			List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();
			// 用以保存后需开始时间相同的节点       
			ActivityImpl sameActivityImpl1 = processDefinitionEntity.findActivity(historicActivityInstances.get(i + 1).getActivityId());
			// 将后面第一个节点放在时间相同节点的集合里      
			sameStartTimeNodes.add(sameActivityImpl1);        
			for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
				HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);
				// 后续第一个节点            
				HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);
				// 后续第二个节点     
				if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {
					// 如果第一个节点和第二个节点开始时间相同保存       
					ActivityImpl sameActivityImpl2 = processDefinitionEntity.findActivity(activityImpl2.getActivityId());
					sameStartTimeNodes.add(sameActivityImpl2);
				} else {
					// 有不相同跳出循环           
					break;
				}
			}      
			List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();
			// 取出节点的所有出去的线        
			for (PvmTransition pvmTransition : pvmTransitions) {
				// 对所有的线进行遍历            
				ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();
				// 如果取出的线的目标节点存在时间相同的节点里，保存该线的id，进行高亮显示          
				if (sameStartTimeNodes.contains(pvmActivityImpl)) {
					highFlows.add(pvmTransition.getId());
				}
			}
		}
		return highFlows;
	}
	
	/**
	 * 显示审批页面
	 * @param request
	 * @param taskId
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = "showTaskView/{taskId}")
	@ResponseBody
	public ModelAndView showTaskView(HttpServletRequest request, @PathVariable("taskId") String taskId, ModelAndView mav) {
		TaskFormData taskFromData = (TaskFormData)formService.getTaskFormData(taskId);
		if (taskFromData != null) {
			List<FormProperty> formProperties = taskFromData.getFormProperties();
			mav.addObject("list", formProperties);
			for (FormProperty formProperty : formProperties) {
				if("enum".equals(formProperty.getType().getName())){
		            Map<String, String> values;
		            values = (Map<String, String>) formProperty.getType().getInformation("values");
		            if (values != null) {
		                mav.addObject("enum_" + formProperty.getId(), values);
		            }
		        } else if("date".equals(formProperty.getType().getName())){
		        	mav.addObject("pattern_"+formProperty.getId(), (String)formProperty.getType().getInformation("datePattern"));
		        }
			}
		}
		mav.addObject("taskId", taskId);
		mav.setViewName("activiti/dynamicForm");
		return mav;
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
    public Map complete(@PathVariable("id") String taskId, HttpServletRequest request) {
//        boolean saveEntityBoolean = BooleanUtils.toBoolean(saveEntity);
        Map<String, Object> variables = new HashMap<String, Object>();
        Enumeration<String> parameterNames = request.getParameterNames();
        Map map = new HashMap(); 
        try {
        	String userCode = ((CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCode();
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
                        } else if (parameter[1].equals("S")) {
                            value = paramValue;
                        }
                        variables.put(parameter[2], value);
                    } else {
                        throw new RuntimeException("invalid parameter for activiti variable: " + parameterName);
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
    
    /**
	 * 获取审批记录
	 * 
	 * @author Xu Dingkui
	 * @date 2017年8月26日
	 * @param businessKey
	 * @return
	 */
	@RequestMapping("/getTrackList")
	@ResponseBody
	public Object getTrackList(String businessKey, HttpServletRequest request) {
		DataTableModel data = new DataTableModel();
		try {
			String parentTenantAccount = request.getSession().getAttribute("COMPANY_LEVEL_TENANT_ACCOUNT").toString();
			String tenantCode = request.getSession().getAttribute("CURRENT_TENANT_CODE").toString();
			List<ApprovalTrack> listGrid = workFlowService.getHisUserTaskActivityInstanceList(businessKey, tenantCode, parentTenantAccount);
			data.setData(listGrid);
			return data;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return data;
		}
	}
}
