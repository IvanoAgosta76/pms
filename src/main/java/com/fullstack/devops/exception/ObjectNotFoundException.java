package com.fullstack.devops.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public ObjectNotFoundException(String message) {
		super(message);
		logger.error(message);
	}

	public ObjectNotFoundException(String message, Throwable t) {
		super(message, t);
		logger.error(message);
	}
}
