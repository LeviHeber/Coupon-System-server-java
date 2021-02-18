package couponsSystem.core.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import couponsSystem.core.exception.CouponsSystemException;

@ControllerAdvice
public class CouponsSystemExceptionAdvice {

	// add a exception handler method

	@ResponseBody
	@ExceptionHandler(CouponsSystemException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String couponsSystemException(CouponsSystemException e) {
		return e.getMessage();
	}

}
