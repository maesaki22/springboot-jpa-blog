package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// pk 자동 번호 : auto_increment
	private int id;
	
	@Column(nullable=false,length=100)
	private String title;
	@Lob		// 대용량 데이터 사용 @
	private String content; 	// 섬머노트 라이버리사용 (디자인된글) <html> 태그가 적용된 디자인
	@ColumnDefault("0")
	private int count;	// 조회수
	
	
	@ManyToOne(fetch=FetchType.EAGER)	// many= board / one= user    :: 한 유저 당 여러개의 글
	@JoinColumn(name="userId")
	private User user;	// DB는 오브젝트 저장xx .. 자바는 오브젝트가능  포린키를 설정해준다. 개편함;; 자동
	
	@OneToMany(mappedBy="board",fetch=FetchType.EAGER)// one= board / many = reply ::mappedBy 연관관계의 주인이 아니다 DB에 column생성하지마라.    
													// JoinColumn이 필요없는 것은 board는 replyid를 가지고 있을필요가없다 reply 가 boardid를 가지기때문에
													// mappedBy="many쪽에 자신의 필드이름ex)board"
													//FetchType.LAZY = 필요할때만 가져오는 방식  / EAGER = 무조곤사용할때 다가져오는방식 
	private List<Reply> reply;	// 하나의 게시글엔 여러개의 댓글이 생길수 있다. 그래서 List를 사용
	
	@CreationTimestamp		// date 가 생성되거나 업뎃될때마다 들어간다
	private Timestamp createDate;
	
	

}
