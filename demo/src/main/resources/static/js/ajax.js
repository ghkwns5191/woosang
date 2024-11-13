var AJAX = {
    ajaxData : function(url, method, body, successCallBack, errCallBack) {
        
        $.ajax({
            url : url
            , type : method
            , data : JSON.stringify(body)
            , headers : {              // Http header
                "Content-Type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
              }
            , dataType : 'json'
            , success : function (data) {
                successCallBack(data);
            }
            , error : function(err) {
                errCallBack(err);
            }
            , complete: function() {
                
            }
        });
    }
}



