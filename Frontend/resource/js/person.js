window.onbeforeunload = function () {
  localStorage.setItem("person-per-page", $(".row-per-page").val());
};

window.onload = function () {
  let rowPerPageInLocalStorage = localStorage.getItem("person-per-page");
  if (rowPerPageInLocalStorage != undefined) {
    $(".row-per-page").val(rowPerPageInLocalStorage);
    $(".row-per-page").trigger("change");
  }
  localStorage.removeItem("person-per-page");
};
var mockData;
getPerson();
$(document).ready(function () {

  // close popup
  $(document).keyup(function (event) {
    if (event.which == "27") {
      $(".popup").removeClass("is-visible");
    }
  });

  $(".popup").click(function (event) {
    if ($(event.target).is(".popup") == true) {
      $(this).removeClass("is-visible");
    }
  });

  $(".popup .cancel-btn").click(function () {
    $('.popup').removeClass("is-visible");
  });

  $(".popup .update-btn").click(function () {
        let dataForm = {};
        dataForm.id = $('.update-form .main-form input[name="id"]').val();
        dataForm.name = $('.update-form .main-form input[name="name"]').val();
        dataForm.gender = $('.update-form .main-form input[name="gender"]').val();
        dataForm.email = $('.update-form .main-form input[name="email"]').val();
        dataForm.phone_number = $('.update-form .main-form input[name="phone-number"]').val();
        console.log(dataForm);
        update(dataForm);
  });

  $(".row-per-page").on("change", () => {
    renderTablePerson(mockData);
  });

  $(".tasks .tab-content span.search input").keyup(() => {
    window.location.href = "#1";
    let searchData = $(".tasks .tab-content span.search input").val();
    renderTablePerson(filterPerson(mockData, searchData));
  });

  $(window).on("hashchange", () => {
    let searchData = $(".tasks .tab-content span.search input").val();
    renderTablePerson(filterPerson(mockData, searchData));
    activePage();
  });

  $(document).on("click", ".edit-action-btn", (event) => {
    let className = $(event.target).attr("class");
    let index = className.substr(className.lastIndexOf(" ") + 1);
    let idNumber = $(".person-table tr." + index + " td.id").html();
    let fullName = $(".person-table tr." + index + " td.name").html();
    let gender = $(".person-table tr." + index + " td.gender").html();
    let email = $(".person-table tr." + index + " td.email").html();
    let phoneNumber = $(".person-table tr." + index + " td.phone-number").html();
    $('.update-form .main-form input[name="id"]').val(idNumber);
    $('.update-form .main-form input[name="name"]').val(fullName);
    $('.update-form .main-form input[name="gender"]').val(gender);
    $('.update-form .main-form input[name="email"]').val(email);
    $('.update-form .main-form input[name="phone-number"]').val(phoneNumber);
    $('.popup').addClass('is-visible');
  });

  $("#add-person .btn .submit-btn").on("click", () => {
    let dataForm = {};
    let id = $('#add-person .form input[name="id-number"]').val();
    let name = $('#add-person .form input[name="name"]').val();
    let phoneNumber = $('#add-person .form input[name="phone-number"]').val();
    let gender = $('#add-person .form select[name="gender"]').val();
    let email = $('#add-person .form input[name="email"]').val();
    dataForm.id = id;
    dataForm.name = name;
    dataForm.phone_number = phoneNumber;
    dataForm.gender = gender;
    dataForm.email = email;
    let settingPost = {
      url: domain + insertPerson,
      method: "POST",
      timeout: 0,
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        Authorization: localStorage.getItem("token-authorization"),
      },
      data: dataForm,
      success: function (data) {
        $("#add-person .btn p.error").css("display", "none");
        notify("success", "Success", "Add successed.");
      },
      statusCode: {
        401: function () {
          sessionHasExpired();
        },
        417: function (data) {
          if (data.responseJSON.message == "Duplicate") {
            $("#add-person .btn p.error").html("* " + "ID number is available");
            $("#add-person .btn p.error").css("display", "block");
          } else if (data.responseJSON.message == "Failed") {
            $("#add-person .btn p.error").html("* " + "Add failed");
            $("#add-person .btn p.error").css("display", "block");
          } else {
            $("#add-person .btn p.error").css("display", "none");
          }
        },
      },
    };

    $.ajax(settingPost);
  });

  $("#add-person .btn .cancel-btn").on("click", () => {
    $("#add-person .form input").val("");
  });
});

// Get person
function getPerson() {
  let settings = {
    url: domain + readPerson,
    method: "GET",
    timeout: 0,
    headers: {
      Authorization: localStorage.getItem("token-authorization")
    },
    success: function (data) {
      mockData = data;
      renderTablePerson(mockData);
    },
    statusCode: {
      401: function () {
        sessionHasExpired();
      },
    },
  };

  $.ajax(settings);
}

function renderTablePerson(mockData) {
  renderPagination(mockData.length);
  let pagingData = renderData(mockData);
  ReactDOM.render(
    <Persons persons={pagingData} />,
    document.getElementById("person-table")
  );
}

function filterPerson(mockData, input) {
  let filterData = mockData.filter((element) => {
    let idPerson = element.id.toString();
    if (idPerson.includes(input)) {
      return true;
    }
    return false;
  });
  return filterData;
}

// Update person
function update(dataForm) {
  let settings = {
    url: domain + updatePerson,
    method: "POST",
    timeout: 0,
    headers: {
      Authorization: localStorage.getItem("token-authorization"),
      "Content-Type": "application/x-www-form-urlencoded"
    },
    data: dataForm,
    success: function (data) {
       notify("success", "Success", "Update successed.");
       $('.popup').removeClass('is-visible');
       getPerson();
    },
    statusCode: {
      401: function () {
        sessionHasExpired();
      },
      417: function (){
        notify("error", "Error", "Update fail.");
      }
    }
  };

  $.ajax(settings);
}
