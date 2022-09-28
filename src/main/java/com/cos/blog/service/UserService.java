package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// 스프링이  컴포넌트 스캔을 통해서 Bean 등록을 해줌 ioc를 해준다 (메모리에 띄워준다)
@Service
public class UserService {
	
	@Autowired 		// 의존성 주입 (DI) 기능이다.
	public UserRepository userRepository;
	
	@Transactional 
	public void 회원가입(User user) {
		userRepository.save(user);
	}
	
	/*
	 * @Transactional(readOnly=true) // Select 할 때 트랜잭션 시작, 서비스 종료시에 트랜젝션 종료:: 이과정에
	 * 정합성 유지를 선언 public User 로그인(User user) { return
	 * userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword(
	 * )); // 레파지토리에서 로그인을 위한 함수를 설계해줘야한다. }
	 */
}
