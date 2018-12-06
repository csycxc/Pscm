package com.banry.pscm.service.tender;

public class TenderPlanException extends RuntimeException {
	
	private static final long serialVersionUID = 1476299762910351969L;

	public TenderPlanException(){
		super();
	}
	
	public TenderPlanException(String message){
		super(message);
	}
	
	public TenderPlanException(String message,Throwable e){
		super(message, e);
	}

}
