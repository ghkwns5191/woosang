/**
 * 
 */
 
var careerCnt = 0;
var academicCnt = 0;


function addCareer() {
	var tbody = document.getElementById("careerAppPoint");
	var newarea = document.createElement("div");
	newarea.classList.add("card");
	newarea.innerHTML = 
	'<div class="row card-body">'
	+ '<div class="col-md-12">'
    +              '<label class="form-label">Company Name</label>'
    +             '<input type="text" class="form-control careerCompanyName" placeholder="Company Name">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label startDate">Start Date</label>'
    +                '<input type="date" class="form-control careerStartDate">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label endDate">End Date</label>'
    +                '<input type="date" class="form-control careerEndDate">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label">Country</label>'
    +              '<select class="form-select careerCountry">'
    +                '<option selected>Select Country</option>'
    +                '<option value="US">United States</option>'
    +                '<option value="KR">South Korea</option>'
    +                '<option value="TH">Thailand</option>'
    +              '</select>'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label city">City</label>'
    +                '<input type="text" class="form-control careerCity" placeholder="City">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label jobDuty">Job Duty</label>'
    +                '<input type="text" class="form-control careerJobDuty" placeholder="Job Duty">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label posi">Positioin</label>'
    +                '<input type="text" class="form-control careerPosi" placeholder="Positioin">'
    +            '</div>'
	+               '<div class="col-12">'
	+				'<button onclick="deleteCareer()" class="btn btn-primary">Add Career</button>'
	+				'</div>'
	+            '</div>';

	
	tbody.appendChild(newarea);

	careerCnt++;
}


function deleteCareer(data) {
	document.getElementById("careerAppPoint").removeChild(data.closest(".card"));
	careerCnt--;
}


function addAcademic() {
	var tbody = document.getElementById("academicAppPoint");
	var newarea = document.createElement("div");
	newarea.classList.add("card");
	newarea.innerHTML = 
	'<div class="row card-body">'
	+ '<div class="col-md-12">'
    +              '<label class="form-label">School Name</label>'
    +             '<input type="text" class="form-control academicSchoolName" placeholder="School Name">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label startDate">Start Date</label>'
    +                '<input type="date" class="form-control academicStartDate">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label endDate">End Date</label>'
    +                '<input type="date" class="form-control academicEndDate">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label">Country</label>'
    +              '<select class="form-select academicCountry">'
    +                '<option selected>Select Country</option>'
    +                '<option value="US">United States</option>'
    +                '<option value="KR">South Korea</option>'
    +                '<option value="TH">Thailand</option>'
    +              '</select>'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label city">City</label>'
    +                '<input type="text" class="form-control academicCity" placeholder="City">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label">Major</label>'
    +                '<input type="text" class="form-control academicMajor" placeholder="Major">'
    +            '</div>'
    +            '<div class="col-md-6">'
    +              '<label class="form-label">Grade</label>'
    +                '<input type="text" class="form-control academicGrade" placeholder="Grade">'
    +            '</div>'
	+				'<div class="col-12">'
	+				'<button onclick="deleteAcademic()" class="btn btn-primary">Add Career</button>'
	+				'</div>'
	+            '</div>';
	tbody.appendChild(newarea);

	academicCnt++;
}


function deleteAcademic(data) {
	document.getElementById("academicAppPoint").removeChild(data.closest(".card"));
	academicCnt--;
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
		careerList.push(careerInfo);
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
		academicList.push(academicInfo);
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
					CM.moveToUrl("/resume/list");
				}
			);
			
		}
		, function(err) {
			console.log(err);
		}
	)
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

function onUpdate() {
	var pathName = window.location.pathname;
	var id = pathName.substring(pathName.lastIndexOf("/") + 1, pathName.length);
	console.log(id);
	CM.moveToUrl("/resume/revise/" + id);
}