package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import javax.crypto.Cipher;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Getter;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인 완료가 된다면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션 저장소에 저장 밑에선언한 PrincipalDetail 객체를 저장한다.
@Getter
public class PrincipalDetail implements UserDetails{

		private User user;
		
		public PrincipalDetail(User user) {	// 로그인시 해당사용자 정보를 저장해두기위한 생성자
			this.user = user;
		}
		@Override
		public String getPassword() {
			return user.getPassword();
		}

		@Override
		public String getUsername() {
			return user.getUsername();
		}
		
		@Override
		public boolean isAccountNonExpired() { 	//계정이 만료여부를 리턴 (true : 만료안됨)
			return true;
		}

		@Override
		public boolean isAccountNonLocked() { 	//계정이 잠겨있는지를 리턴 (true : 잠기지 않음)
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {	// 비밀번호가 만료되지 않았는지를 리턴( true : 만료안됨)
			return true;
		}

		@Override
		public boolean isEnabled() { 	// 계정이 활성화 (사용가능) 를 리턴 ( true :활성화 )
			return true;
		}
		
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() { 	// 계정의 권한을 리턴한다. 
				
			Collection<GrantedAuthority> collectors = new ArrayList<>();
			/*
			 * collectors.add(new GrantedAuthority() {
			 * 
			 * @Override public String GrantedAuthority() {
			 * 
			 * return "ROLE_"+user.getRole(); // ex) ROLE_USER 스프링의 규칙 ROLE_ 를 붙여줘야한다. } });
			 */
			// 위의 내용을 새로운 문법으로 정리하면
			// add함수안에 들어가는 파라미터는 어차피 GrantedAuthority 객체이고 이 객체는 GrantedAuthority 함수가 하나뿐이다 
			// 그걸 축소시킨다면 ()->{return xxx";}  로 던져주면된다.
			collectors.add(()->{return "ROLE_"+user.getRole();});
			
			return collectors;
		}		
}
