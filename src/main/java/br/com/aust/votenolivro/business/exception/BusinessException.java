package br.com.aust.votenolivro.business.exception;

public class BusinessException extends RuntimeException{
	
	private static final long serialVersionUID = 4808206810795989852L;

	public BusinessException( String msg ){
		super( msg );
	}
	
	public BusinessException( Throwable exc ){
		super( exc );
	}
	
	public BusinessException( String msg, Throwable exc ){
		super( msg, exc );
	}

}

