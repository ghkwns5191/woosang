/**
 * 
 */
 
 
 const signInBtn = document.getElementById("signIn");
const signUpBtn = document.getElementById("signUp");
const fistForm = document.getElementById("form1");
const secondForm = document.getElementById("form2");
const container = document.querySelector(".container");

signInBtn.addEventListener("click", () => {
  container.classList.remove("right-panel-active");
});

signUpBtn.addEventListener("click", () => {
  container.classList.add("right-panel-active");
});

fistForm.addEventListener("submit", (e) => e.preventDefault());
secondForm.addEventListener("submit", (e) => e.preventDefault());




function onLogin() {
	var loginInfo = {
		username : document.getElementById("username").value
		, password : document.getElementById("password").value
	}
	
	$.ajax({
		url : "/login"
		, data : JSON.stringify(loginInfo)
		, type : 'post'
		, headers : {              // Http header
      "Content-Type" : "application/json",
      "X-HTTP-Method-Override" : "POST"
    }
    , dataType : 'text'
		, success : function (data) {
			console.log(data);
			
			if (data == "success") {
				console.log(window.location.origin);
				window.alert("로그인에 성공하였습니다.");
				window.location.href="http://localhost:8090/room";
			} else if (data == "fail") {
				window.alert("아이디 혹은 비밀번호가 잘못되었습니다.");
				document.getElementById("username").value = "";
				document.getElementById("password").value = "";
			}
			
		}
		, error : function() {

		}, complete: function() {
			
		}
	})
}


function onJoin() {
	var joinInfo = {
		username : document.getElementById("username1").value
		, password : document.getElementById("password1").value
	}
	
	
	$.ajax({
		url : "/join"
		, data : JSON.stringify(joinInfo)
		, type : 'post'
		, headers : {              // Http header
      "Content-Type" : "application/json",
      "X-HTTP-Method-Override" : "POST"
    }
    , dataType : 'text'
		, success : function (data) {
			console.log(data);
			
			if (data == "success") {
				window.alert("회원가입이 완료되었습니다. \n로그인 후 이용하세요.");
			} else if (data == "fail") {
				window.alert("아이디가 중복 되었습니다.");
				document.getElementById("username1").value = "";
				document.getElementById("password1").value = "";
			}
			
		}
		, error : function() {

		}, complete: function() {
			
		}
	})
}