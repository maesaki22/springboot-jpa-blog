package com.cos.blog.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice	// data 반환
@RestControllerAdvice		// 패키지 어디에서든 접근가능한 @
public class GlobalExcetionHandler {
	// Excetion 위치에 각 특정 excetion을 선언해줘도 된다  IllegalArgumentException
	@ExceptionHandler(value=Exception.class)
	public String handleArgumentExcetion(Exception e) {
		return "<h1>" + e.getMessage() + "</h1>";
	}
}
