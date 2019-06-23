package com.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ClientException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ClientException(String message) {
		super(message);
		
	}

	public ClientException(Throwable cause) {
		super(cause);
		
	}

	
	
	
}
