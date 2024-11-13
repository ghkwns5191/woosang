var CM = {
    alertMove: function(msg, callback) {
        window.alert(msg);
        callback();
    },

    moveToUrl: function(url) {
        window.location.href = url;
    }
}



