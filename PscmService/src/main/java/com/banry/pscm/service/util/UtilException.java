package com.banry.pscm.service.util;

public class UtilException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3172522550584884646L;

	public UtilException(){
		super();
	}
	
	public UtilException(String message){
		super(message);
	}
	
	public UtilException(String message,Throwable e){
		super(message, e);
	}
}
