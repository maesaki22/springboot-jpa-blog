package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //가  붙은것은 밑의 메소드는 파일을 리턴한다.
public class TempControllerTest {
	
	// http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴 기본경로 : src/main/resources/static
		// 리턴명 : /home.html
		// 리턴명이 home.html 이면 에러가 날것이다 기본경로+리턴명   : src/main/resources/static + home.heml 그래서 리턴명에 / 를 붙여준다.
		
		// static 폴더에서 정적파일들을 읽어 내서 리턴해주는 것이기때문에 우리는 jsp 파일을 사용하면 찾을수가없다.
		return "/home.html";
	}
	
	@GetMapping("/temp/img")  	//  이미지는 정적인 파일이라 찾을수 있다 하지만 jsp 파일을 동적인(컴파일이되어야하는)파일이라 못찾는다 
											// 그래서 경로를 src/main/webapp/WEB-INF/views 폴더를 만들어서 jsp 파일을 저장한다
											// application.yml 파일에 경로를 설정해준다.
											// prefix: /WEB-INF/views/ 
											// suffix: .jsp
											// 위말은  prefix + return명 + .jsp
	public String tempImg() {
		return "/bs.jpg";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//prefix : /WEB-INF/views/
		//suffix : .jsp
		// 풀경로 : /WEB-INF/views/ + 리턴명 + .jsp
		//return "test.jsp"; 	이렇게 리턴하면 에러가 뜬다 이유는 바로 위 주석
		return "test";
	}
}
