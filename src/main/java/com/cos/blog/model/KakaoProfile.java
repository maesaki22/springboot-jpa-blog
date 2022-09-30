package com.cos.blog.model;

import lombok.Data;

@Data
public class KakaoProfile {

	public Long id;
	public String connected_at; 	// Jsonshematopojo 사용하면 connectedAt 로 선언되지만 카카오에선 connected_at 으로 선언되어있다. 변경해줘야함
	public Properties properties;
	public KakaoAccount kakao_account;

	@Data
	public class Properties {
		public String nickname;
	}

	@Data
	public class KakaoAccount {

		public Boolean profile_nickname_needs_agreement;
		public Profile profile;
		public Boolean has_email;
		public Boolean email_needs_agreement;
		public Boolean is_email_valid;
		public Boolean is_email_verified;
		public String email;

		@Data
		public class Profile {
			public String nickname;
		}
	}
}
