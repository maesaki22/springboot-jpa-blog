package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController 	//  html 파일이아닌 data를 리턴해준다.
public class DummyControllerTest {
	
	@Autowired		// DummyControllerTest가 메모리에 뜰때 Autowired로 인해 메모리에 같이 뜬다.
							// 이행위가 의존성 주입 (DI) 기능이다.
	private UserRepository userRepository;
	@DeleteMapping("/dummy/user/{id}")
	public String deleteUser(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {			// 잘모르겠으면 Excetion e 로  선언해도 가능하다.
			return "삭제에 실패하였습니다. 해당 id "+ id + "가 없습니다.";
		}
		return "삭제 되었습니다. id : " + id;
	}
	
	@Transactional		// 함수종료시 자동 commit  : save를 하지 않아도 update가 되는 @  : 더티 체킹
	@PutMapping("/dummy/user/{id}")
	private User updateUser(@PathVariable int id, @RequestBody User requestUser) {
																		// Json 데이터로 보내도 spring이 자바 오브젝트로 변환해준다 MessageConverter가
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		// 검색해서 찾아온 유저를 user 객체에 저장한다면 가지고 있는 모든 값이 들어간다. 이후 
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		// 받아온 data를  set() 해준다. 
		user.setPassword(requestUser.getPassword());	// user객체가 영속화가 되어있어서 함수가 종료되는시점에서 update가 일어난다.
		user.setEmail(requestUser.getEmail()); 				// Transactional때문에 가능 :: 더티 체킹이라 한다.
		// userRepository.save(user);		// .save는 insert용이지만 id 값이있다면 그걸 업뎃한다.
													//user를 찾아서 user객체에 넣어서 저장하지않으면 나머지 데이터는 null이 되기에 꼭 찾아서 객체를 호출해줘야한다.
		return user;		
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	// 한페이지당 2개의 데이터 받아오기 연습   :: Springboot 에서 page을 찾아서 사용하면된다. 직접만들지않아도 됨
	@GetMapping("/dummy/user")
	// Paging 정보와 db정보를 다가져올경우  // db정보만 가져오려면 아래쪽
	//	public Page<User> pageList(@PageableDefault(size=2,sort="id",direction = Sort.Direction.DESC) Pageable pageable){
//		Page<User> users = userRepository.findAll(pageable);
//		return users;	
//	}
	public List<User> pageList(@PageableDefault(size=2,sort="id",direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return users;	
	}
	
	// {id} 주소로 파라메터를 전달 받을수 있다.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
//		User user = userRepository.findById(id).orElseThrow(()->{
//		return new IllegalArgumentException("해당사용자는 없습니다.");
//		});
// 		람다식 더쉬운 방식이지만 추후 사용해보도록..
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
//			public User get() {
//				return new User();
//			}
//		});		
		// db를 못찾았을 경우  return 이 null이 되면 에러가 되니까
		// Optional로 너의 user객체를 감싸서 가져올테니 null인지 아닌지 니가 판단해서 return 해라
		// supplier를 활용한 오류처리 로직이다. 인터페이스이기때문에 익명의 class를 선언해서 활용해야한다..... 부족한부분
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		// 요청 : 웹브라우저 RestController 라서 데이터를 리턴해준다.
		// user 객체 (자바오프젝트)라서 RestController에선  웹 브라우저가 이해하도록 변환해야한다  ->>Json(Gson 라이브러리)
		// 스프링부트에선 응답시에 MessageConverter가 자동으로 작동한다 
		// 만약에 자바 오브젝트를 리턴하게 ㅔ되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에 던져버린다.
		return user;
	}
	
	// http://localhost:8000/blog/dummy/join ->>요청
	// http의 body에 username password email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {	// key 파라미터로도 오겠지만 오브젝트(user)로 넘겨올수있다 대신 넘어오는 username = user.username 이 같아야한다
			System.out.println(user.getId() +","+user.getUsername() + ","+user.getPassword() + ","+ user.getEmail() + "," + 
						user.getRole() + "," + user.getCreateDate());
			
			user.setRole(RoleType.USER);
			userRepository.save(user); 	// 회원가입정보를  db에 저장한다.
		return "회원가입이 완료가 되었습니다.";
	}

}
