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
			if (data.loginFlag) {
				CM.alertMove("로그인에 성공하였습니다.", function() {
					window.location.href="/";
				});
			} else {
				CM.alertMove("아이디 혹은 비밀번호가 잘못되었습니다.", function() {
					document.getElementById("username").value = "";
					document.getElementById("password").value = "";
				})
				
			}
		}
		, function(err) {
			console.lorg(err);
		}
	)
	
	//  $.ajax({
	//  	url : "/login"
	//  	, data : JSON.stringify(loginInfo)
	//  	, type : 'post'
	//  	, headers : {              // Http header
    //    "Content-Type" : "application/json",
    //    "X-HTTP-Method-Override" : "POST"
    //  }
    //  , dataType : 'json'
	//  	, success : function (data) {
	// 		console.log(data);
	//  		console.log(data.loginFlag);
		
	//  		if (data.loginFlag) {
	//  			console.log(window.location.origin);
	//  			window.alert("로그인에 성공하였습니다.");
	//  			window.location.href="/";
	//  		} else if (!data.loginFlag) {
	//  			window.alert("아이디 혹은 비밀번호가 잘못되었습니다.");
	//  			document.getElementById("username").value = "";
	//  			document.getElementById("password").value = "";
	//  		}
		
	//  	}
	//  	, error : function() {

	//  	}
	//  	, complete: function() {
		
	//  	}
	//  })
}


// 개인회원가입
function onJoin() {
	var joinInfo = {
		username : document.getElementById("username").value
		, password : document.getElementById("password").value
		, first_name : document.getElementById("firstName").value
		, middle_name : document.getElementById("middleName").value
		, last_name : document.getElementById("lastName").value
		, phone : document.getElementById("phone").value
		, email : document.getElementById("email").value
		, nationality : document.getElementById("nationality").value
	}

	// url, method, data, 성공 callback, 에러 callback
	AJAX.ajaxData(
		"/join"
		, "post"
		, joinInfo
		, function(data) {
			console.log(data);
			if (data == "success") {
				CM.alertMove("회원가입이 완료되었습니다. \n로그인 후 이용하세요.", function() {
					CM.moveToUrl("/login");
				});
				
			} else if (data == "fail") {
				CM.alertMove("아이디가 중복 되었습니다.", function() {
					document.getElementById("username1").value = "";
					document.getElementById("password1").value = "";
				})
				
			}
		}
		, function(err) {
			console.log(err);
		}
	);
}

// 기업회원가입
function onJoin2() {
	var basicInfo = {
		username : document.getElementById("username").value
		, password : document.getElementById("password").value
		, first_name : document.getElementById("firstName").value
		, middle_name : document.getElementById("middleName").value
		, last_name : document.getElementById("lastName").value
		, phone : document.getElementById("phone").value
		, email : document.getElementById("email").value
		, nationality : document.getElementById("nationality").value
	}

	var compInfo = {
		company_name : document.getElementById("companyName").value
		, ceo : document.getElementById("ceo").value
		, website : document.getElementById("website").value
	}

	var joinInfo = {
		basicInfo : basicInfo
		, compInfo : compInfo
	}

	// url, method, data, 성공 callback, 에러 callback
	AJAX.ajaxData(
		"/join2"
		, "post"
		, joinInfo
		, function(data) {
			console.log(data);
			if (data == "success") {
				CM.alertMove("회원가입이 완료되었습니다. \n로그인 후 이용하세요.", function() {
					CM.moveToUrl("/login");
				});
				
			} else if (data == "fail") {
				CM.alertMove("아이디가 중복 되었습니다.", function() {
					document.getElementById("username1").value = "";
					document.getElementById("password1").value = "";
				})
				
			}
		}
		, function(err) {
			console.log(err);
		}
	);

	
	
	//  $.ajax({
	//  	url : "/join"
	//  	, data : JSON.stringify(joinInfo)
	//  	, type : 'post'
	//  	, headers : {              // Http header
    //    "Content-Type" : "application/json",
    //    "X-HTTP-Method-Override" : "POST"
    //  }
    //  , dataType : 'text'
	//  	, success : function (data) {
		
	//  		if (data == "success") {
	//  			window.alert("회원가입이 완료되었습니다. \n로그인 후 이용하세요.");
	//  			window.location.href="/login"
	//  		} else if (data == "fail") {
	//  			window.alert("아이디가 중복 되었습니다.");
	//  			document.getElementById("username1").value = "";
	//  			document.getElementById("password1").value = "";
	//  		}
		
	//  	}
	//  	, error : function() {

	//  	}, complete: function() {
		
	//  	}
	//  })
}


function onToJoin() {
	CM.moveToUrl("/join");
}


function onToLogin() {
	CM.moveToUrl("/login");
}


// 뒤로가기(홈화면으로)
function onBack() {
	CM.moveToUrl("/");
}


function onJoinInd() {
	CM.moveToUrl("/join/user");
}

function onJoinComp() {
	CM.moveToUrl("/join2/comp");
}