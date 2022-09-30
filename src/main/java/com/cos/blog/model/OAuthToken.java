package com.cos.blog.model;

import lombok.Data;


// kakao 로그인시 인증을 하고 code를 가지고 kakaoToken을 받아와서 json 정보를 저장해두는 DAO
@Data
public class OAuthToken {
	private String access_token;
	private String token_type;
	private String refresh_token;
	private int expires_in;
	private String scope;
	private int refresh_token_expires_in;
}
