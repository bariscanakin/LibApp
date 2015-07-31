package com.akin.libapp.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.akin.libapp.constant.ErrorMessages;
import com.akin.libapp.model.RestResponse;

@ControllerAdvice
public class LibAppExceptionResolver {


	@ExceptionHandler(ItemNotFoundException.class)
	@ResponseBody
	public RestResponse resolveItemNotFoundExceptions(ItemNotFoundException e) {
//		logger.error("ItemNotFoundException was caught by specific resolver!!\n" + "Item of class: " + e.getClassName()
//				+ " with given id:" + e.getId() + " does not exist in database", e);

		return RestResponse.error(ErrorMessages.ITEM_NOT_FOUND_ERROR);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public RestResponse resolveAllExceptions(Exception e) {
//		logger.error("Exception was caught by global resolver!!", e);

		return RestResponse.error(ErrorMessages.GENERIC_ERROR);
	}

}
