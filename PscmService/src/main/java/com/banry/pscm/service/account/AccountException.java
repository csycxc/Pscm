package com.banry.pscm.service.account;

public class AccountException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3172522550584884646L;

	public AccountException(){
		super();
	}
	
	public AccountException(String message){
		super(message);
	}
	
	public AccountException(String message,Throwable e){
		super(message, e);
	}
}
