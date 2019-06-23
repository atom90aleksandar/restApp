package com.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class WorkFlowException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WorkFlowException(String message, Throwable cause) {
		super(message, cause);
	}

	public WorkFlowException(String message) {
		super(message);
	}

	public WorkFlowException(Throwable cause) {
		super(cause);
	}

	
	
}
