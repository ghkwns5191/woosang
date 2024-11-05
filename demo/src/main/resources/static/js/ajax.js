var AJAX = {
    ajaxData : function(url, method, data, successCallBack, errCallBack) {
        
        $.ajax({
            url : url
            , data : JSON.stringify(data)
            , type : method
            , headers : {              // Http header
                "Content-Type" : "application/json"
            }
            , success : function (data) {
                successCallBack(data);
            }
            , error : function(err) {
                errCallBack(err);
            }
            , complete: function() {
                
            }
        })
    }
}



