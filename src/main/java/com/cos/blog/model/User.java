package com.cos.blog.model;

import java.sql.Timestamp;

//import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 	// getter setter
@NoArgsConstructor 	// 빈생성자
@AllArgsConstructor	// 풀생성자
@Builder		// 유동적인 생성자
@Entity		// class를  table로 변경하기위한 @ :: User class가 MySQL에 테이블이 생성이 된다.
//@DynamicInsert		// insert 당시에 null값 인것들은 제외하고 인서트 문을 날린다.
public class User {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)		// 프로젝트에서 연결된 DM의 넘버링 전략을 사용하겠다. PK로 유용
		private int id; 	// 오라클연결시 시퀀스 / MYSQL 사용시 auto_increment   :: yml파일에서 use-new-id-generator-mappings: false 선언이 jpa를 따르지않겠다는 의미
		@Column(nullable=false,length=30)		// 값이 null 이면 안되도록 설정 길이는 30
		private String username;		// id
		@Column(nullable=false,length=100)	// pw 추후 해쉬로 변경해서 암호화할것이라 100까지 설정
		private String password;
		@Column(nullable=false,length=50)
		private String email;
		//@ColumnDefault("'user'")		// insert 할때 값을 넣어주는데 null 값으로 설정되어서 user로 등록되지 않아서 DynamicInsert를 사용하면된다.
												// 하지만 @을 많이사용하면 임기응변일뿐
		@Enumerated(EnumType.STRING)// DB에선 RoleType 은 없기때문에 선언해준 @
		private RoleType role;		//enum type을 사용하려했지만? ADMIN or USER
		
		@CreationTimestamp		// 시간이 자동 입력 db에 저장될때 적용됨
		private Timestamp createDate; 	// java sql이 가진걸 호출
}
