package com.cos.blog.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cos.blog.dto.ResponseDto;

@ControllerAdvice	// data 반환
@RestControllerAdvice		// 패키지 어디에서든 접근가능한 @
public class GlobalExcetionHandler {
	// Exception 위치에 각 특정 exception을 선언해줘도 된다  IllegalArgumentException
	@ExceptionHandler(value=Exception.class)
	public ResponseDto<String> handleArgumentExcetion(Exception e) {
		return new ResponseDto<String>(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage() );
	}
}
