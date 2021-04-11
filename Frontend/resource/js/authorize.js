var dataForm = {
    authorization_token : localStorage.getItem('token-authorization')
}
var settings = {
  url: domain + authorize,
  method: "POST",
  timeout: 0,
  headers: {
    "Content-Type": "application/x-www-form-urlencoded",
  },
  data: dataForm,
  success: function (data) {
  },
  statusCode: {
    401: function () {
        sessionHasExpired();
    },
  }
};

$.ajax(settings);

function sessionHasExpired (){
    location.href = "login.html?isAuthor=false";
}