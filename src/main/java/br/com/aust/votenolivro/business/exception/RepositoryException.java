package br.com.aust.votenolivro.business.exception;

public class RepositoryException extends BusinessException{
	
	private static final long serialVersionUID = 4808206810795989852L;

	public RepositoryException( String msg ){
		super( msg );
	}
	
	public RepositoryException( Throwable exc ){
		super( exc );
	}
	
	public RepositoryException( String msg, Throwable exc ){
		super( msg, exc );
	}

}

