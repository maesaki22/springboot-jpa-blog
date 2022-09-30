package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController		// data 리턴
public class UserApiContoroller {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//@Autowired
	//private HttpSession session; 	// DI로 선언해줘도 가능하고 선언없이 함수의 파라메타로 넘겨줘도 가능하다.
	
	// 회원가입 버튼이 눌러지면 user.js에서 호출( POST / url: /api/user  :: data를 json으로 보내 User 객체에 담아준다.
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){
		userService.회원수정(user);
		// 트랜잭션이 종료되기 때문에 DB값은 변경이 되어있다.
		// BUT!! 세션 값은 변경되어 있지 않아서 화면의 세션 정보 변경을 직접해줘야한다.
		// 수정이후 바낀정보를 세션에 등록하기 
	
		Authentication  authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	// 이방식은 전통적인 로그인 방식이다. ( 시큐리티없이 )
	/*
	 * @PostMapping("/api/user/login") public ResponseDto<Integer>
	 * login(@RequestBody User user,HttpSession session){ // HttpSession 위에
	 * private로 @Autowired를 해줘서 사용가능 System.out.println("login 호출됨"); User principal
	 * = userService.로그인(user); // 접근주체 (principal) if(principal != null) {
	 * session.setAttribute("principal", principal); // 로그인이 되면 DI된 세션에 정보 넣는다. }
	 * else System.out.println("user객체가 널이다 : 로그인실패");
	 * 
	 * return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	 * 
	 * }
	 */
}
