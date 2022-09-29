package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@Service 	//Bean 등록
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	// 스프링이 로그인 요청을 가로챌 때 username password 두개 값을 가로채고
	// password 부분 처리는 알아서 함
	// username 이  DB에 있는지 확인만해주면 된다.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {	// 이함수를 구현하지않으면 로그인 정보를 담아두는 것이 안된다.
		User Principal = userRepository.findByUsername(username)
					.orElseThrow(()->{
						return new UsernameNotFoundException("해당 사용자를 찾을 수가 없습니다. : " + username);
					});
		return new PrincipalDetail(Principal); 		// 시큐리티의 세션에 user정보(Principal)를 저장한다  이때 타입이 UserDetails 이다.
	}
		
}
