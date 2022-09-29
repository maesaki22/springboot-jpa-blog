package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.model.User;


// interface
// DAO 
// 자동으로 bean 등록이 된다.  
// @Repository 가 생략이 가능하다.  :: 선언을 하면 spring 이 메모리에 띄워준다.
// class 선언과 동시에 상속 받은 거자체가  CRUD 기능을 가진다.
public interface UserRepository extends JpaRepository<User, Integer> {
					// user 레파지토리                  user 테이블을 관리하는 레파지토리 / pk = Integer 란 의미  상속받은 class의 기능은 엄청나게 많이있다.
	
	// SELECT * FORM user WHERE username = 1?; :: 1= username (1번파라메타)
	Optional<User> findByUsername(String username);

}

// JPA Naming 쿼리 ( login용 )
// SELECT * FROM user WHRER username = ?1 AND password = ?2;   :: 밑의 함수는 없지만  AaANDBb 로하면 aa=? AND bb=? 쿼리가 나간다
// User findByUsernameAndPassword(String username,String password);  :: 접통적인 로그인 방식으로 할때 사용
// 위 선언을 풀어서 직접한다면 아래 @와 함수를 선언해주면된다.
//@Query(value="SELECT * FROM user WHRER username = ?1 AND password = ?2",nativeQuery = true)
//User login(String username,String password);