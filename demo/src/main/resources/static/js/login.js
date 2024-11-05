/**
 * 
 */
 

// 로그인
function onLogin() {
	var loginInfo = {
		username : document.getElementById("username").value
		, password : document.getElementById("password").value
	}
	// url, method, data, 성공 callback, 에러 callback
	AJAX.ajaxData(
		"/login"
		, "post"
		, loginInfo
		, function(data) {
			if (data == "success") {
				window.alert("로그인에 성공하였습니다.");
				window.location.href="/";
			} else if (data == "fail") {
				window.alert("아이디 혹은 비밀번호가 잘못되었습니다.");
				document.getElementById("username").value = "";
				document.getElementById("password").value = "";
			}
		}
		, function(err) {
			console.lorg(err);
		}
	)
	
	// $.ajax({
	// 	url : "/login"
	// 	, data : JSON.stringify(loginInfo)
	// 	, type : 'post'
	// 	, headers : {              // Http header
    //   "Content-Type" : "application/json",
    //   "X-HTTP-Method-Override" : "POST"
    // }
    // , dataType : 'text'
	// 	, success : function (data) {
	// 		console.log(data);
			
	// 		if (data == "success") {
	// 			console.log(window.location.origin);
	// 			window.alert("로그인에 성공하였습니다.");
	// 			window.location.href="/";
	// 		} else if (data == "fail") {
	// 			window.alert("아이디 혹은 비밀번호가 잘못되었습니다.");
	// 			document.getElementById("username").value = "";
	// 			document.getElementById("password").value = "";
	// 		}
			
	// 	}
	// 	, error : function() {

	// 	}
	// 	, complete: function() {
			
	// 	}
	// })
}


// 회원가입
function onJoin() {
	var joinInfo = {
		username : document.getElementById("username1").value
		, password : document.getElementById("password1").value
	}

	// url, method, data, 성공 callback, 에러 callback
	AJAX.ajaxData(
		"/join"
		, "post"
		, joinInfo
		, function(data) {
			if (data == "success") {
				window.alert("회원가입이 완료되었습니다. \n로그인 후 이용하세요.");
				window.location.href="/login"
			} else if (data == "fail") {
				window.alert("아이디가 중복 되었습니다.");
				document.getElementById("username1").value = "";
				document.getElementById("password1").value = "";
			}
		}
		, function(err) {
			console.log(err);
		}
	);

	
	
	// $.ajax({
	// 	url : "/join"
	// 	, data : JSON.stringify(joinInfo)
	// 	, type : 'post'
	// 	, headers : {              // Http header
    //   "Content-Type" : "application/json",
    //   "X-HTTP-Method-Override" : "POST"
    // }
    // , dataType : 'text'
	// 	, success : function (data) {
			
	// 		if (data == "success") {
	// 			window.alert("회원가입이 완료되었습니다. \n로그인 후 이용하세요.");
	// 			window.location.href="/login"
	// 		} else if (data == "fail") {
	// 			window.alert("아이디가 중복 되었습니다.");
	// 			document.getElementById("username1").value = "";
	// 			document.getElementById("password1").value = "";
	// 		}
			
	// 	}
	// 	, error : function() {

	// 	}, complete: function() {
			
	// 	}
	// })
}


function onToJoin() {
	window.location.href = "/join";
}


function onToLogin() {
	window.location.href = "/login";
}


// 뒤로가기(홈화면으로)
function onBack() {
	window.location.href="/";
}