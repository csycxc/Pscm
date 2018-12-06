package com.banry.pscm.service.workflow;

public class WorkFlowException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7039418052928882914L;

	public WorkFlowException(String message){
		super(message);
	}
	
	public WorkFlowException(String message, Throwable cause) {
		super(message,cause);
	}
}
