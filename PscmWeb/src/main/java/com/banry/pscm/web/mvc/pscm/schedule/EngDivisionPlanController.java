package com.banry.pscm.web.mvc.pscm.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banry.pscm.service.schedule.EngDivisionPlan;
import com.banry.pscm.service.schedule.EngDivisionPlanService;
import com.banry.pscm.service.schedule.ScheduleException;

@RestController
@RequestMapping(value="/engDivisionPlan")
public class EngDivisionPlanController {
	
	@Autowired
	private EngDivisionPlanService engDivisionPlanService;
	
	@RequestMapping(value="/findEngDivisionPlanByPrimaryKey")
	public EngDivisionPlan findEngDivisionPlanByPrimaryKey(String divisionsncode, String divversion) {
		try {
			EngDivisionPlan query = new EngDivisionPlan();
			query.setDivisionSnCode(divisionsncode);
			query.setDivVersion(divversion);
			EngDivisionPlan plan = engDivisionPlanService.findEngDivisionPlanByPrimaryKey(query);
			return plan;
		} catch (ScheduleException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	} 
}
