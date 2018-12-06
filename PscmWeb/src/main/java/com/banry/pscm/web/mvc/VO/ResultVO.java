package com.banry.pscm.web.mvc.VO;


/**
 * http请求返回的最外层对象
 * xudk
 * 2018-10-24
 */
public class ResultVO<T> {

    /** 是否成功. */
    private boolean result;

    /** 提示信息. */
    private String retMsg;

    /** 具体内容. */
    private T data;

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
