package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//Spring이 com.cos.blog 패키지 이하를 스캔해서 모든 파일을 메모리에 new 하는 것이 아니다.
//특정 @이 붙은있는 class 파일들을 new해서  스프링 컨테이너를 관린해준다 ->> Ioc (제어의 역전)
import org.springframework.web.bind.annotation.RestController;

@RestController				
public class BlogControllerTest {
	@GetMapping("/test/hello") 	// http://localhost:8080/test/hello
	public String hello() {
		return "<h1>Hello Springboot</h1>"; 
	}

}
