package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(html파일)
//@Controller
// 사용자가 요청 -> 응답(data)   			:: 인터넷 브라우저 요청은 get방식 밖에 되지않습니다 다른 (post/put/delete)는 허용되지 않습니다.

@RestController
public class HttpControllerTest {
	private static final String TAG = "HttpControllerTest : ";
	
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = new Member(1,"jys","1234","jys@nate.com");
		// Member 객체를 생성할때 내부 생성자 순서 상관없이 builder()를 사용해서 넣을수가있다. 들어가지않는값은 자동생성된다.
		// Member객체에서 @Builder 만 사용하면 쓸수 있다.
		Member m1 =Member.builder().email("jjj@nate.com").password("444").username("abc").build();
		
		System.out.println(TAG + "getter :"+ m1.getId());
		m1.setId(5000);
		System.out.println(TAG + "setter :"+ m1.getId());
		return "lombok test 완료";
				
	}
	
	@GetMapping("/http/get")	// http://localhost:8080/http/get		DB:select
	public String getTest(Member m) { 	// ?id=1&username=jys&password=1234&email=ssar@nate.com  (쿼리스트링)

		

		return "get 요청 : "+m.getId()+", " + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	
	
	@PostMapping("http/post")	// http://localhost:8080/http/post		DB:insert
	public String postTest(@RequestBody Member m) { 	// postman 에서  raw 데이터는 text/plain  
																				// application/Json 을 날리면 Member객체에 매핑
																				// 위에 작업을 해주는 것이 MessageConverter ( 스프링부트 )
		return "post 요청 : "+m.getId()+", " + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	
	
	@PutMapping("http/put")		// http://localhost:8080/http/put		DB:update
	public String putTest(@RequestBody Member m) {
		return "put 요청 : "+m.getId()+", " + m.getUsername() + "," + m.getPassword() + "," + m.getEmail();
	}
	@DeleteMapping("http/delete")	// http://localhost:8080/http/delete	DB:delete
	public String deleteTest() {
		return "delete 요청";
	}
}
