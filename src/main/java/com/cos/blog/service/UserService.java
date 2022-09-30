package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이  컴포넌트 스캔을 통해서 Bean 등록을 해줌 ioc를 해준다 (메모리에 띄워준다)
@Service
public class UserService {

	@Autowired // 의존성 주입 (DI) 기능이다.
	public UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); // user가 설정한 비밀번호
		String encPassword = encoder.encode(rawPassword); // 해쉬로 변경
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional
	public void 회원수정(User requestUser) {
		// 수정시 영속성 컨텍스트 User 오프젝트를 영속화시키고 영속화된 User 오브젝트를 DB에서 가져오면 영속화 되고 함수가 종료되면 자동으로 DB update가 된다.
		User persistance = userRepository.findById(requestUser.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("회원 찾기 실패");
		});
		String rawPassword = requestUser.getPassword(); // user가 설정한 비밀번호
		String encPassword = encoder.encode(rawPassword); // 해쉬로 변경
		persistance.setPassword(encPassword);
		persistance.setEmail(requestUser.getEmail());
		// 회원수정 종료시 ->서비스종료 = 트랜잭션 종료  DB update 자동 ( 자동 commit )		
	}	
	/*
	 * @Transactional(readOnly=true) // Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜젝션 종료:: 이과정에
	 * 정합성 유지를 선언 public User 로그인(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword(
	 * )); // 레파지토리에서 로그인을 위한 함수를 설계해줘야한다. }
	 */
}
