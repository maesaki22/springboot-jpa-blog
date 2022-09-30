let index = {
	init: function() {
		$("#btn-save").on("click", () => {		// function(){} 를 사용하지않고  ()=>{} 를 사용하는 이유는 this를 바인딩하기 위해서!!
			this.save();									// save 버튼 클릭시 save()호출
		});
		$("#btn-update").on("click", () => {		
			this.update();									
		});		
	},

	save: function() {
		//alert('user의 save함수 호출');
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		// ajax 통신을 이용해서 데이터를 json으로 변경하여 insert 요청
		// ajax 호출시 default가 비동기 호출  :: 밑의 부분을 실행하다가 ajax요청이 완료되면 그시점부터 done 부분부터 시작된다 (비동기)
		$.ajax({
			type: "POST",
			url: "/auth/joinProc",
			data: JSON.stringify(data), 	// http body 데이터
			contentType: "application/json; charset=utf-8", 	//body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" 		// 요청을 서버로해서 응답이 왔을 때 기본적으로 String(문자열)로 온다 (생긴게 json이라면) => javascript
			// 생략해주면 ajax가  통신을 성공하고 서버가 json을 리턴하면 자동으로 자바 오브젝트로 변환해준다 (그래도 적어주자)
		}).done(function(resp) {
			alert("회원가입이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});	
	},
	update: function() {
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		};
		// ajax 통신을 이용해서 데이터를 json으로 변경하여 insert 요청
		// ajax 호출시 default가 비동기 호출  :: 밑의 부분을 실행하다가 ajax요청이 완료되면 그시점부터 done 부분부터 시작된다 (비동기)
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data), 	// http body 데이터
			contentType: "application/json; charset=utf-8", 	//body 데이터가 어떤 타입인지 (MIME)
			dataType: "json" 		// 요청을 서버로해서 응답이 왔을 때 기본적으로 String(문자열)로 온다 (생긴게 json이라면) => javascript
		}).done(function(resp) {
			alert("회원수정이 완료되었습니다.");
			location.href = "/";
		}).fail(function(error) {
			alert(JSON.stringify(error));
		});	
	},	
}

index.init();