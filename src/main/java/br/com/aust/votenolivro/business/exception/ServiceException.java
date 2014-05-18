package br.com.aust.votenolivro.business.exception;

public class ServiceException extends BusinessException{
	
	private static final long serialVersionUID = 4808206810795989852L;

	public ServiceException( String msg ){
		super( msg );
	}
	
	public ServiceException( Throwable exc ){
		super( exc );
	}
	
	public ServiceException( String msg, Throwable exc ){
		super( msg, exc );
	}

}
