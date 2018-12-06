package com.banry.pscm.workflow;


import com.banry.pscm.service.engsafety.EngsafetyException;
import com.banry.pscm.service.engsafety.HiddenTrouble;
import com.banry.pscm.service.engsafety.HiddenTroubleService;
import com.banry.pscm.workflow.util.ProcessDefinitionKey;
import com.banry.pscm.workflow.util.SpringUtil;
import org.activiti.engine.delegate.event.ActivitiEntityWithVariablesEvent;
import org.activiti.engine.delegate.event.ActivitiEvent;
import org.activiti.engine.delegate.event.ActivitiEventListener;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 
 * 任务监听器，实现ActivitiEventListener接口
 * 
 * */
@Component
public class TaskCompletedListener implements ActivitiEventListener {
	private HiddenTroubleService hiddenTroubleService;

	private static Logger logger = LoggerFactory.getLogger(TaskCompletedListener.class);
	
    @Override
    public void onEvent(ActivitiEvent event){
    	try {
    		ActivitiEntityWithVariablesEvent entityEvent = (ActivitiEntityWithVariablesEvent)event;
	        Object entity = entityEvent.getEntity();
	        if(entity instanceof TaskEntity){
	            TaskEntity task = (TaskEntity) entity;
				//获取“业务键”
//		        String businessKey = task.getExecution().getProcessInstanceBusinessKey();//pi.getBusinessKey();
	            String businessKey = task.getExecution().getProcessBusinessKey();
		        String pdkAndId[] = businessKey.split("-");
		        if (pdkAndId.length != 2) {
		        	return;
		        }
		        String processDefinitionKey = pdkAndId[0];
		        String key = pdkAndId[1];
		        //隐患处理
		        if (ProcessDefinitionKey.HIDDENTROUBLE.equals(processDefinitionKey)) {
		        	hiddenTroubleService = (HiddenTroubleService) SpringUtil.getObject(HiddenTroubleService.class);
		        	if (hiddenTroubleService != null) {
		        		HiddenTrouble htt = hiddenTroubleService.selectByKeyWithoutBill(key);
				        if (htt != null) {
				        	Map<String, String> map = entityEvent.getVariables();
				        	//分配责任人
				        	if ("task2".equals(task.getExecution().getActivityId())) {
				        		//安全责任人
				        		String e = map.get("safetyOfficer");
					        	if (e != null) {
					        		htt.setSafetyChargeUser(e);
					        		hiddenTroubleService.saveHiddenTrouble(htt);
					        	}
				        	} else if ("task3".equals(task.getExecution().getActivityId())) {
					        	//延期整改
				        		String e = String.valueOf(map.get("rectifypostpone"));
					        	if (e != null) {
					        		htt.setRectifyPostpone(htt.getRectifyPostpone() + Integer.parseInt(e));
					        		hiddenTroubleService.saveHiddenTrouble(htt);
					        	}
				        	} else if ("task4".equals(task.getExecution().getActivityId())) {
					        	//整改措施
				        		String e = map.get("rectifysteps");
				        		//安全责任人
				        		String ve = map.get("safetyOfficer");
					        	if (ve != null) {
					        		htt.setSafetyChargeUser(ve);
					        	}
					        	if (e != null) {
					        		htt.setRectifySteps(e);
					        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				        			String now = sdf.format(new Date());
					        		htt.setRectifyTime(sdf.parse(now));
					        		hiddenTroubleService.saveHiddenTrouble(htt);
					        	}
				        	}
			            }
		        	}
		        }
	        }
		} catch (EngsafetyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
	/* (non-Javadoc)
	 * @see org.activiti.engine.delegate.event.ActivitiEventListener#isFailOnException()
	 */
	@Override
	public boolean isFailOnException() {
		// TODO Auto-generated method stub
		return false;
	}
}
