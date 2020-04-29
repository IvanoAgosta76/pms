package com.fullstack.devops.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public RecordNotFoundException(String message) {
		super(message);
		logger.error(message);
	}
	
	public RecordNotFoundException(String message, Throwable t) {
		super(message, t);
		logger.error(message);
	}
}