package com.banry.pscm.service.enums;


/**
 * 
 * @author xudk
 * 2018-10-23
 */
public enum ChangeTypeEnum implements CodeEnum {
    MOVE_IN(1, "移入"),
    MOVE_OUT(2, "移出"),
    ENG_NUM_CHANGE(3, "工程量变更"),
    UNIT_PRICE_CHANGE(4, "单价变更"),
    ENG_NUM_AND_UNIT_PRICE_CHANGE(5, "工程量和单价同时变更"),
    ;

    private Integer code;

    private String message;

    ChangeTypeEnum(Integer code, String message) {
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

    @Override
    public String toString() {
        return message;
    }
}
