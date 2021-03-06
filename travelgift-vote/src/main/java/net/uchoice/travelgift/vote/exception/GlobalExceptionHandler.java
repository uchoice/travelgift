/**
 * @Title: GlobalExceptionHandler.java
 * @Package com.zbj.alg.chaos.exceptions
 */
package net.uchoice.travelgift.vote.exception;

import java.security.InvalidParameterException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author yangxiaobin
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler
	public Object businessExceptionHandler(Exception exception, HttpServletRequest req) {
		logger.error("[INTERNAL_SERVER_ERROR]", exception);
		ResponseEntity<String> response;
		if (exception instanceof InvalidParameterException) {
			response = new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		} else if (exception instanceof MissingServletRequestParameterException) {
			response = new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		} else if (exception instanceof HttpRequestMethodNotSupportedException) {
			response = new ResponseEntity<String>(exception.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
		} else if (exception instanceof ForbiddenRequestException) {
			response = new ResponseEntity<String>(exception.getMessage(), HttpStatus.FORBIDDEN);
		} else if (exception instanceof ArticleNotExistsException) {
			response = new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
		} else {
			response = new ResponseEntity<String>("服务器异常", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	/**
	 * 判断请求是否是Ajax请求
	 * 
	 * @param req
	 * @return
	 */
	public boolean isAjax(HttpServletRequest req) {
		String contentTypeHeader = req.getHeader("Content-Type");
		String acceptHeader = req.getHeader("Accept");
		String xRequestedWith = req.getHeader("X-Requested-With");
		return (contentTypeHeader != null && contentTypeHeader.contains("application/json"))
				|| (acceptHeader != null && acceptHeader.contains("application/json"))
				|| "XMLHttpRequest".equalsIgnoreCase(xRequestedWith);
	}
}
