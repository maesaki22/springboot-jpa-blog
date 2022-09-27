package com.cos.blog.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	

}
