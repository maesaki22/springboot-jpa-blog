package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
@Data	//getter+setter
//@AllArgsConstructor	//생성자
@NoArgsConstructor	//빈생성자
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder	// 오보로딩된 생성자 사용가능 (lombok 기능 )
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
}
