function toSignIn() {
    window.location.href = "/login";
}

function toSignUp() {
    window.location.href = "/join";
}

function toResume() {
    window.location.href = "/resume/list";
}

function toLogOut() {
	
	
		$.ajax({
	 	url : "/logout"
	 	, type : 'post'
	 	, headers : {              // Http header
       "Content-Type" : "application/json",
       "X-HTTP-Method-Override" : "POST"
     }
     , dataType : 'json'
	 	, success : function (data) {
			console.log(data);
	 		console.log(data.loginFlag);
		
	 		if (data.logoutInfo) {
	 			console.log(window.location.origin);
	 			window.alert("로그아웃 하였습니다.");
	 			window.location.href="/";
	 		} else if (!data.logoutInfo) {
	 			window.alert("아이디 혹은 비밀번호가 잘못되었습니다.");
	 			document.getElementById("username").value = "";
	 			document.getElementById("password").value = "";
	 		}
		
	 	}
	 	, error : function() {

	 	}
	 	, complete: function() {
		
	 	}
	 });
	
}