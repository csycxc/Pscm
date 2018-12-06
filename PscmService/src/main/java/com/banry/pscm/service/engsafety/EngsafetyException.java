package com.banry.pscm.service.engsafety;

public class EngsafetyException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1335737891087266715L;

	public EngsafetyException(){
		super();
	}
	
	public EngsafetyException(String message){
		super(message);
	}

	public EngsafetyException(String message,Throwable e){
		super(message, e);
	}
}
