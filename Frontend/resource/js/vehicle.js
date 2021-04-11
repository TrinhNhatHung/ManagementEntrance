window.onbeforeunload = function () {
  localStorage.setItem("vehicle-per-page", $(".row-per-page").val());
};

window.onload = function () {
  let rowPerPageInLocalStorage = localStorage.getItem("vehicle-per-page");
  if (rowPerPageInLocalStorage != undefined) {
    $(".row-per-page").val(rowPerPageInLocalStorage);
    $(".row-per-page").trigger("change");
  }
  localStorage.removeItem("vehicle-per-page");
};
var mockData;
getVehicle();
$(document).ready(function () {
  $(".row-per-page").on("change", () => {
    renderTableVehicle(mockData);
  });

  $(".tasks .tab-content span.search input").keyup(() => {
    window.location.href = "#1";
    let searchData = $(".tasks .tab-content span.search input").val();
    renderTableVehicle(filterVehicle(mockData, searchData));
  });

  $(window).on("hashchange", () => {
    let searchData = $(".tasks .tab-content span.search input").val();
    renderTableVehicle(filterVehicle(mockData, searchData));
    activePage();
  });

  $("#add-vehicle .btn .submit-btn").on("click", () => {
    let dataForm = {};
    let idNumber = $('#add-vehicle .form input[name="person_id"]').val();
    let licensePlate = $('#add-vehicle .form input[name="number_plate"]').val();
    let brand = $('#add-vehicle .form input[name="car_manufacturer"]').val();
    let modelCode = $('#add-vehicle .form input[name="name_vehicle"]').val();
    let color = $('#add-vehicle .form input[name="color"]').val();
    dataForm.number_plate = licensePlate;
    dataForm.car_manufacturer = brand;
    dataForm.person_id = idNumber;
    dataForm.color = color;
    dataForm.name_vehicle = modelCode;
    let settingPost = {
      url: domain + insertVehicle,
      method: "POST",
      timeout: 0,
      headers: {
        "Content-Type": "application/x-www-form-urlencoded",
        Authorization: localStorage.getItem("token-authorization"),
      },
      data: dataForm,
      success: function (data) {
        $("#add-vehicle .btn p.error").css("display", "none");
        notify("success", "Success", "Add successed.");
      },
      statusCode: {
        401: function () {
          sessionHasExpired();
        },
        417: function (data) {
          if (data.responseJSON.message == "Non-IDNumber") {
            $("#add-vehicle .btn p.error").html(
              "* " + "Owner's ID number isn't available"
            );
            $("#add-vehicle .btn p.error").css("display", "block");
          } else if (data.responseJSON.message == "Duplicate-Vehicle") {
            $("#add-vehicle .btn p.error").html(
              "* " + "License plate is available"
            );
            $("#add-vehicle .btn p.error").css("display", "block");
          } else {
            $("#add-vehicle .btn p.error").css("display", "none");
          }
        },
      },
    };

    $.ajax(settingPost);
  });
});

function getVehicle() {
  var settings = {
    url: domain + readVehicle,
    method: "GET",
    timeout: 0,
    headers: {
      Authorization: localStorage.getItem("token-authorization"),
    },
    success: function (data) {
      mockData = data;
      renderTableVehicle(mockData);
    },
    statusCode: {
      401: function () {
        sessionHasExpired();
      },
    },
  };

  $.ajax(settings);
}

function renderTableVehicle(mockData) {
  renderPagination(mockData.length);
  let pagingData = renderData(mockData);
  ReactDOM.render(
    <Vehicles vehicles={pagingData} />,
    document.getElementById("vehicle-table")
  );
}

function filterVehicle(mockData, input) {
  let filterData = mockData.filter((element) => {
    let numberPlate = element.numberPlate.toString();
    if (numberPlate.includes(input)) {
      return true;
    }
    return false;
  });
  return filterData;
}
