package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;


// interface
// DAO 
// 자동으로 bean 등록이 된다.  
// @Repository 가 생략이 가능하다.  :: 선언을 하면 spring 이 메모리에 띄워준다.
// class 선언과 동시에 상속 받은 거자체가  CRUD 기능을 가진다.
public interface UserRepository extends JpaRepository<User, Integer> {
					// user 레파지토리                  user 테이블을 관리하는 레파지토리 / pk = Integer 란 의미  상속받은 class의 기능은 엄청나게 많이있다.
	
	
}
