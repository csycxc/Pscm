package com.banry.pscm.service.comm;

public interface Constants {

	//流程状态-起草
	Integer WF_STATUS_DRAFT = 1;
	//流程状态-提交
	Integer WF_STATUS_SUBMIT = 2;
	//流程状态-审阅中
	Integer WF_STATUS_CHECKING = 3;
	//流程状态-已结束
	Integer WF_STATUS_FINISH = 50;
	//流程状态-已退回
	Integer WF_STATUS_RETURN = 99;
	
	//招标计划流程状态-已发布
	Integer TENDER_PLAN_WF_STATUS_RELEASE = 56;
}
