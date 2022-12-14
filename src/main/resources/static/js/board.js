let index = {
	init: function() {
		$("#btn-save").on("click", () => {		// function(){} 를 사용하지않고  ()=>{} 를 사용하는 이유는 this를 바인딩하기 위해서!!
			this.save();									// save 버튼 클릭시 save()호출
		}),
		$("#btn-delete").on("click", () => {
			this.deletebyId();
		}),
		$("#btn-update").on("click", () => {
			this.update();
		});
		$("#btn-reply-save").on("click", () => {
			this.replySave();
		});		
		
	},

	save: function() {
		//alert('user의 save함수 호출');
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		// ajax 통신을 이용해서 데이터를 json으로 변경하여 insert 요청
		// ajax 호출시 default가 비동기 호출  :: 밑의 부분을 실행하다가 ajax요청이 완료되면 그시점부터 done 부분부터 시작된다 (비동기)
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data), 	// http body 데이터
			contentType: "application/json; charset=utf-8", 	//body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" 		// 요청을 서버로해서 응답이 왔을 때 기본적으로 String(문자열)로 온다 (생긴게 json이라면) => javascript
			// 생략해주면 ajax가  통신을 성공하고 서버가 json을 리턴하면 자동으로 자바 오브젝트로 변환해준다 (그래도 적어주자)
		}).done(function(resp) {
			alert("작성된 글이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	
	deletebyId: function() {
		let id = $("#id").text();
		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id,
			dataType: "json" 		// 요청을 서버로해서 응답이 왔을 때 기본적으로 String(문자열)로 온다 (생긴게 json이라면) => javascript
		}).done(function(resp) {
			alert("삭제가 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	update: function() {
		let id = $("#id").val();
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		$.ajax({
			type: "PUT",
			url: "/api/board/"+id,
			data: JSON.stringify(data), 	// http body 데이터
			contentType: "application/json; charset=utf-8", 	//body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" 		// 요청을 서버로해서 응답이 왔을 때 기본적으로 String(문자열)로 온다 (생긴게 json이라면) => javascript
			// 생략해주면 ajax가  통신을 성공하고 서버가 json을 리턴하면 자동으로 자바 오브젝트로 변환해준다 (그래도 적어주자)
		}).done(function(resp) {
			alert("수정이 완료 되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},
	replySave: function() {
		let data = {
			userId: $("#userId").val(),
			boardId: $("#boardId").val(),
			content: $("#reply-content").val()
		};
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data), 	// http body 데이터
			contentType: "application/json; charset=utf-8", 	//body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" 		// 요청을 서버로해서 응답이 왔을 때 기본적으로 String(문자열)로 온다 (생긴게 json이라면) => javascript
		}).done(function(resp) {
			alert("댓글작성이 완료 되었습니다.");
			location.href = `/board/${data.boardId}`;
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},	
	replyDelete: function(boardId,replyId) {
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			//data: JSON.stringify(data), 	// http body 넘어가는 데이타가없기때문에 지워도된다.
			contentType: "application/json; charset=utf-8", 	//body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" 		// 요청을 서버로해서 응답이 왔을 때 기본적으로 String(문자열)로 온다 (생긴게 json이라면) => javascript
		}).done(function(resp) {
			alert("댓글삭제 성공");
			location.href = `/board/${boardId}`;
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});
	},	
}

index.init();