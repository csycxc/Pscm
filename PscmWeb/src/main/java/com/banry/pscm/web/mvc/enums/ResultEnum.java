package com.banry.pscm.web.mvc.enums;

public enum ResultEnum {

	 SAVE_SUCCESS(0, "保存成功"),
	 MODIFY_SUCCESS(1, "修改成功"),
	 DELETE_SUCCESS(2, "删除成功"),
	 SUBMIT_SUCCESS(3, "提交成功"),
	 SAVE_FAIL(4, "保存失败"),
	 MODIFY_FAIL(5, "修改失败"),
	 DELETE_FAIL(6, "删除失败"),
	 SUBMIT_FAIL(7, "提交失败"),
	 SUBMIT_CANNOT_MODIFY(8, "已经提交不可以修改"),
	 SUBMIT_CANNOT_DELETE(9, "已经提交不可以删除"),
	 SUBMIT_CANNOT_SUBMIT(10, "已经提交不可以再次提交"),
	 DATA_ERROR(11, "数据错误"),
	 
	 SUB_DIV_WORK_BILL_NOT_EXIST(21, "分项工程清单不存在"),
	 SUB_DIV_WORK_BILL_IS_RELATION_TENDER_PLAN(22, "已经关联招标计划"),
	 SUB_DIV_WORK_BILL_RELATION_SUCCESS(23, "关联成功"),
	 
	 TENDER_PLAN_NOT_EXIST(31, "招标计划不存在"),
	 RELEASE_SUCCESS(32, "发布成功"),
	 TENDER_PLAN_EXIST_TENDER_RESULT(33, "招标计划已经存在招标结果"),
	 EXIST_NOT_BID_SUPPLIER(34, "存在没有投标的供方，不可提交"),
	 END_QUOTE_NOT_EXIST(35, "最终报价没有维护，请先维护再提交"),
	 SUPPLIER_IS_BID(36, "排序第一的供方已经中标过，请修改排序字段，设置其他供方中标"),
	 BID_EVALUATION_REPORT_NOT_EXIST(37, "招标结果不存在"),
	 TENDER_PLAN_SUPPLIER_NOT_EXIST(38, "招标计划的供方没有维护"),
	 TENDER_PLAN_BILL_NOT_EXIST(39, "招标计划的清单没有维护"),
	 TENDER_RESULT_CHANGE_NOT_EXIST(40, "招标结果变更不存在"),
	 TENDER_RESULT_EXIST_NOT_FINISH_CHANGE(41, "招标结果存在未结束的变更，请审批完成再新增"),
	 TENDER_RESULT_RELATION_CONTRACT_CANNOT_ADD(42, "招标结果已经关联合同，不可新增招标结果变更"),
	 TENDER_RESULT_RELATION_CONTRACT_CANNOT_SUBMIT(42, "招标结果已经关联合同，不可提交招标结果变更"),
	 
	 CHANGE_SUPPLIER_CANNOT_SAME_AS_BID_SUPPLIER(51, "变更供方不可与中标供方相同"),
	 CHANGE_SUPPLIER_MUST_ONE(52, "每次变更只能变更成一个供方"),
	 SUB_DIV_WORK_BILL_IS_MOVE_IN(53, "清单项已被移入其他招标结果或合同"),
	 SUB_DIV_WORK_BILL_MOVE_IN_SUCCESS(54, "移入成功"),
	 CHANGE_SUPPLIER_IS_SIGN_CONTRACT(55, "变更的供方已经签订合同，请选择其他供方"),
	 SUB_DIV_WORK_BILL_NOT_RELATION_RESULT_CANNOT_MOVE_IN(58, "此划分没有关联招标结果或者没有被移出，不可移入"),
	 ;
	
	private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
    
    
}
