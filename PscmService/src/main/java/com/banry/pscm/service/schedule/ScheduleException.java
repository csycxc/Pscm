package com.banry.pscm.service.schedule;

public class ScheduleException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1335737891087266715L;

	public ScheduleException(){
		super();
	}
	
	public ScheduleException(String message){
		super(message);
	}

	public ScheduleException(String message,Throwable e){
		super(message, e);
	}
}
