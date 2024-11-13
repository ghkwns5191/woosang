function toSignIn() {
	CM.moveToUrl("/login");
}

function toSignUp() {
	CM.moveToUrl("/join");
}

function toResume() {
	CM.moveToUrl("/resume/list");
}

function toLogOut() {
	
	AJAX.ajaxData(
		"/logout"
		, 'post'
		, {nothing: 'nothing'}
		, function (data) {
		
			if (data.logoutInfo) {
				CM.alertMove("로그아웃 하였습니다.", function() {
					CM.moveToUrl("/");
			   });
				
			} 
	   
		}
	)


	// 	$.ajax({
	//  	url : "/logout"
	//  	, type : 'post'
	//  	, headers : {              // Http header
    //    "Content-Type" : "application/json",
    //    "X-HTTP-Method-Override" : "POST"
    //  }
    //  , dataType : 'json'
	//  	, success : function (data) {
		
	//  		if (data.logoutInfo) {
	// 			CM.alertMove("로그아웃 하였습니다.", function() {
	// 				window.location.href="/";
	// 			});
	 			
	//  		} 
		
	//  	}
	//  	, error : function() {

	//  	}
	//  	, complete: function() {
		
	//  	}
	//  });
	
}