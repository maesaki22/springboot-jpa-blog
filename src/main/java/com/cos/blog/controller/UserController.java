package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 밑에 /auth는 후 에 인증이 안된 사용자들이 출입할수 있는 경로를 /auth/X 허용
// 주소가  /이면 index.jsp 접근 허용
// static 이하에 있는 /js/ , /css , /image 쪽을 접근허용

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// http://localhost:8000/blog/user/joinForm
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		return "user/joinForm"; // http://localhost:8000/blog/user/joinForm.jsp
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // @ResponseBody붙이면 data를 리턶내주는 컨트롤러함수가 된다. :: 지운이유 redirect:/

		// POST 방식으로 key=value 데이터를 요청해야한다. ( kakao에게 )
		// Retrofit2 / OKHttp / RestTemplate 등이 있다.
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// Httpbody 오브젝트 생성 key=value 설정
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "38bee5305fc457b30c1134f129257b07");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader 와 HttpBody를 하나의 오브젝트에 옮긴다.
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청하기 - post 방식으로 response 변수의 응답을 받음
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class);

		// kakaoToken에 담긴 Json 데이타를 자바에 저장 :: Gson, Json Simple, ObjectMapper
		// data 정보가 오류가 있을수 있으니 OAuthToken getter/setter 설정하고 try/catch 사용
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);  // 정보를 담을 class OAuthToken
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("카카오 엑세스토큰 :: " + oauthToken.getAccess_token());

		//////////////////////////////////////////////////////////////////////
		
		RestTemplate rt2 = new RestTemplate();
		//HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer "+ oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader 와 HttpBody를 하나의 오브젝트에 옮긴다.
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers2);

		// Http 요청하기 - post 방식으로 response 변수의 응답을 받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest,
				String.class
				);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class); 	// 유저정보를 받아왔기때문에 KakaoProfile class에 저장
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// 카카오로  login을 완료했을경우  계정 통합을 해야하니  내 프로젝트 와 연결시킨다.
		System.out.println(kakaoProfile.getKakao_account().getEmail());
		// 카카오 아이디를 이용해 내 프로젝트 아이디로 변경
		// ID ::  kakaoProfile getemail + kakaoProfile getid
		// EMAIL  : kakaoProfile getmail
		// PW : cosKey
		
		
		User kakoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		// 가입자 or 비가입자 판단 후  가입여부 판단 이후 세션저장
		
		User originUser = userService.회원찾기(kakoUser.getUsername());
				
		if(originUser.getUsername() == null) { 	// 회원을찾았을때 없다면

			userService.회원가입(kakoUser);
		}

		// kakao ID를 이용한  내프로젝트 ID와 cosKey를 이용하여 세션에저장된다.
		Authentication  authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakoUser.getUsername(),cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "/user/updateForm";
	}
}
