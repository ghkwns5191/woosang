/**
 * 
 */
 
var careerCnt = 0;
var academicCnt = 0;


function addCareer() {
	var tbody = document.getElementById("careerAppPoint");
	var newarea = document.createElement("tbody");
	newarea.classList.add("tablebody");
	newarea.innerHTML = 
	'<tr>'
	+ '<td><label></label></td>'
	+ '<td><label></label></td>'
	+ '</tr>'
	+ '<tr>'
	+ 	'<td><label>Company Name</label></td>'
	+	'<td><input class="form-control careerCompanyName" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>Start Date</label></td>'
	+	'<td><input class="form-control careerStartDate" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>End Date</label></td>'
	+	'<td><input class="form-control careerEndDate" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>Country</label></td>'
	+	'<td><input class="form-control careerCountry" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>City</label></td>'
	+	'<td><input class="form-control careerCity" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>Job Duty</label></td>'
	+	'<td><input class="form-control careerJobDuty" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>Position</label></td>'
	+	'<td><input class="form-control careerPosi" type="text"/></td>'
	+ '</tr>';
	tbody.appendChild(newarea);

	careerCnt++;
}


function addAcademic() {
	var tbody = document.getElementById("academicAppPoint");
	var newarea = document.createElement("tbody");
	newarea.innerHTML = 
	'<tr>'
	+ '<td><label></label></td>'
	+ '<td><label></label></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>School Name</label></td>'
	+	'<td><input class="form-control academicSchoolName" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>Start Date</label></td>'
	+	'<td><input class="form-control academicStartDate" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>End Date</label></td>'
	+	'<td><input class="form-control academicEndDate" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>Country</label></td>'
	+	'<td><input class="form-control academicCountry" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>City</label></td>'
	+	'<td><input class="form-control academicCity" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>Major</label></td>'
	+	'<td><input class="form-control academicMajor" type="text"/></td>'
	+ '</tr>'
	+ '<tr>'
	+	'<td><label>Grade</label></td>'
	+	'<td><input class="form-control academicGrade" type="text"/></td>'
	+ '</tr>';
	tbody.appendChild(newarea);

	academicCnt++;
}

function onInsert() {
	var basicInfo = {
		title: document.getElementById("title").value,
		summary: document.getElementById("summary").value
	}

	

	
	var careerList = [];
	var academicList = [];

	for (let i = 0; i < careerCnt; i++) {
		var careerInfo = {
			company_name : document.getElementsByClassName("careerCompanyName")[i].value,
			start_date : document.getElementsByClassName("careerStartDate")[i].value,
			end_date : document.getElementsByClassName("careerEndDate")[i].value,
			country : document.getElementsByClassName("careerCountry")[i].value,
			city : document.getElementsByClassName("careerCity")[i].value,
			job_duty : document.getElementsByClassName("careerJobDuty")[i].value,
			posi : document.getElementsByClassName("careerPosi")[i].value,
		}
		careerList.add(careerInfo);
	}

	for (let i = 0; i < academicCnt; i++) {
		var academicInfo = {
			company_name : document.getElementsByClassName("academicSchoolName")[i].value,
			start_date : document.getElementsByClassName("academicStartDate")[i].value,
			end_date : document.getElementsByClassName("academicEndDate")[i].value,
			country : document.getElementsByClassName("academicCountry")[i].value,
			city : document.getElementsByClassName("academicCity")[i].value,
			major : document.getElementsByClassName("academicMajor")[i].value,
			grade : document.getElementsByClassName("academicGrade")[i].value,
		}
		academicList.add(careerInfo);
	}


	var inputParams = {
		basicInfo: basicInfo,
		careerList: careerList,
		academicList: academicList,
	}

	AJAX.ajaxData(
		"/resume/insert"
		, "post"
		, inputParams
		, function(data) {
			console.log(data);
			CM.alertMove("신규 입력이 완료되었습니다.",
				function() {
					window.location.href="/resume/list";
				}
			);
			
		}
		, function(err) {
			console.log(err);
		}
	)
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