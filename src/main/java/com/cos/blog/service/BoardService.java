package com.cos.blog.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

// 스프링이  컴포넌트 스캔을 통해서 Bean 등록을 해줌 ioc를 해준다 (메모리에 띄워준다)
@Service
public class BoardService {
	
	@Autowired 		// 의존성 주입 (DI) 기능이다.
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional 
	public void 글저장(Board board,User user) { 	// title , content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	@Transactional(readOnly = true)
	public Page<Board>글목록(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	@Transactional(readOnly = true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패.");
				});
	}
	
	@Transactional
	public void 삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	@Transactional
	public void 글수정하기(int id,Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패");
				}); 	// 영속화 
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시  트랜잭션이 종료됨 ->> 이순간 더티체킹이 일어나서 자동 업데이트가 일어남 DB flush
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
			
		//  userid boardid 를 넘겨 받았기 때문에 user/board 객체를 찾아준다.  :: reply객체에 user/board 객체를 넣어준다
		/*
		 * User user = userRepository.findById(replySaveRequestDto.getUserId())
		 * .orElseThrow(()->{ return new
		 * IllegalArgumentException("댓글쓰기 실패: 유저 id를 찾을 수 없습니다."); }); // 영속화
		 * 
		 * Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
		 * .orElseThrow(()->{ return new
		 * IllegalArgumentException("댓글쓰기 실패: 게시글 id를 찾을 수 없습니다."); }); // 영속화
		 * 
		 * Reply reply = Reply.builder() .user(user) .board(board)
		 * .content(replySaveRequestDto.getContent()) .build();
		 * 
		 	replyRepository.save(reply);
		 	*/
		// 위방식을 사용하지 않고 네거티브 쿼리를 사용한다면 user/board를 다시 찾는 일을 줄여준다.
		// 레파지토리에 사용할 함수를 ( 쿼리를 날릴 )  선언해준다.
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(),replySaveRequestDto.getBoardId(),replySaveRequestDto.getContent());
	}
	
	public void 댓글삭제(int reply) {
		replyRepository.deleteById(reply);
	}
}
