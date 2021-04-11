$(document).ready (()=> {
    notifySessionExpired();
    $(window).on('keydown', function(e) {
        if (e.which == 13) {	            	
            submitFormLogin();
            return false;
        }
 });	
    $('#content form button[name="submit"]').on ('click' ,()=> {
        submitFormLogin();
    });
});

function submitFormLogin (){
    let usernameData = $('#username').val();
        let passwordData = $('#password').val();
        if (usernameData == "" || passwordData == ""){
            $('#warning-area').html('<p id="warning">* Username,password is required</p>');
        } else {
            $('#warning-area').html('');
            let dataForm = {
                username : usernameData,
                password : passwordData
            };  
    
            var settings = {
                "url": domain + login,
                "method": "POST",
                "timeout": 0,
                "headers": {
                  "Content-Type": "application/x-www-form-urlencoded"
                },
                data: dataForm,
                success: function(data){
                    localStorage.setItem('token-authorization', data.token);
                    localStorage.setItem('fullname', data.fullname);
                    location.href = "history.html";
                },
                statusCode: {
                401: function() {
                    $('#warning-area').html('<p id="warning">* Username,password is wrong</p>');
                }
              }
              };
              
              $.ajax(settings);  
        }
}

function notifySessionExpired (){
     let url = location.href;
     let queryString = url.substr(url.lastIndexOf('?') + 1, url.length);
     let paramArrStr = queryString.split('&');
     let paramObj = {};
     paramArrStr.forEach ((element)=> {
          let  param = element.split('=');
          paramObj[param[0]] = param[1];
     });

     if (paramObj['isAuthor'] == 'false'){
        notify('other', 'Notify', 'Login session has expired');
     }
}