/**
 * 
 */
 


function toDetail(id) {
	CM.moveToUrl("/resume/detail/" + id);
}


function onToJoin() {
	CM.moveToUrl("/join");
}


function onToLogin() {
	CM.moveToUrl("/login");
}


// 뒤로가기(홈화면으로)
function onBack() {
	window.history.back();
}

function onNew() {
	CM.moveToUrl("/resume/detail/new");
}