package com.cos.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


// 빈등록  : 스프링 컨테이너에서 객체를 관리할수있게하는것

@Configuration 	// 빈등록 (IOC)
@EnableWebSecurity	// 시큐리티 필터 적용
@EnableGlobalMethodSecurity(prePostEnabled=true) 	// 특정 주소로 접근시  권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/auth/**")
				.permitAll()
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
				.loginPage("/auth/loginForm");
	}

}

