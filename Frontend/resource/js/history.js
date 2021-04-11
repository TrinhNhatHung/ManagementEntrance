window.onbeforeunload = function() {
    localStorage.setItem("history-per-page", $('.row-per-page').val()); 
}

window.onload = function() {
    let rowPerPageInLocalStorage = localStorage.getItem('history-per-page');
    if (rowPerPageInLocalStorage != undefined){
        $('.row-per-page').val(rowPerPageInLocalStorage); 
        $('.row-per-page').trigger('change');
    }
    localStorage.removeItem('history-per-page');
}
var dataForm = {};
getHistories(dataForm);
var mockData;
$(document).ready(function (){
    $('.date-picker').datepicker({
        dateFormat: "dd/mm/yy",
        maxDate: '-0D'
    });

    var twelveHour = $('.timepicker-12-hr').wickedpicker();
        $('.time').text('//JS Console: ' + twelveHour.wickedpicker('time'));
        $('.timepicker-24-hr').wickedpicker({
            twentyFour: true
        });
        $('.timepicker-12-hr-clearable').wickedpicker({
            clearable: true
        });

    $('.wickedpicker__controls__control-up').html('<i class="fa fa-chevron-up" aria-hidden="true"></i>');
    $('.wickedpicker__controls__control-down').html('<i class="fa fa-chevron-down" aria-hidden="true"></i>');
    $('.time-to').val('');
    $('.time-from').val('');

    $('.wrap-time-from i').on('click',()=>{
        $('.wrap-time-from input').val('');
    }); 

    $('.wrap-time-to i').on('click',()=>{
        $('.wrap-time-to input').val('');
    }); 

    $('.wrap-date-from i').on('click',()=>{
        $('.wrap-date-from input').val('');
    });

    $('.wrap-date-to i').on('click',()=>{
        $('.wrap-date-to input').val('');
    });

    $('.wrap-numberplate i').on('click',()=>{
        $('.wrap-numberplate input').val('');
    });

    $('.history-table').magnificPopup({
        type: 'image',
		delegate: 'a.history-image',
		gallery: {
			enabled: false
		},
    });

    $('.row-per-page').on('change', ()=> {
       renderTableHistory(mockData);
    });

    $(window).on('hashchange', ()=> {      
        renderTableHistory(mockData);
        activePage();
    });

    $('.search button').on('click', () => {

        let dateFromString = $('.search .date-from').val();
        if (dateFromString != "") {
            let dateFromArr = dateFromString.split("/");           
            dataForm.date_from = dateFromArr[2] + "-" + dateFromArr[1] + "-" + dateFromArr[0]
        }
        let dateToString = $('.search .date-to').val();
        if (dateToString != "") {
            let dateToArr = dateToString.split("/");           
            dataForm.date_to = dateToArr[2] + "-" + dateToArr[1] + "-" + dateToArr[0]
        }
    
        let timeFromString = $('.search .time-from').val();
        if (timeFromString != ""){
            let timeFromArr = timeFromString.split(" : ");
            dataForm.time_from = timeFromArr[0] + ":" + timeFromArr[1] + ":00"; 
        }
        
        let timeToString = $('.search .time-to').val();
        if (timeToString != ""){
            let timeToArr = timeToString.split(" : ");
            dataForm.time_from = timeToArr[0] + ":" + timeToArr[1] + ":59"; 
        }
    
        let numberPlate = $('.search .number-plate').val();
        if (numberPlate != ""){
            dataForm.number_plate = numberPlate;
        }
        let isOut = $("input[name='is-out']:checked").val();
        if (isOut == "true"){
            dataForm.is_out = true;
        } else {
            dataForm.is_out = false;
        }

        window.location.href = '#1';
        
        getHistories(dataForm);
    });
})

function getHistories (dataForm){
    let settings = {
        url: domain + readHistory,
        method: "POST",
        timeout: 0,
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            "Authorization": localStorage.getItem('token-authorization')
        },
        data : dataForm,
        success: function (data) {
            mockData = data;
            renderTableHistory(mockData);
          },
          statusCode: {
            401: function () {
              sessionHasExpired();
            },
        }
    };

    $.ajax(settings);
}

function renderTableHistory (mockData) {
    renderPagination(mockData.length);
    let pagingData = renderData(mockData);
    ReactDOM.render(
        <Histories histories={pagingData} />, document.getElementById("history-table"));
}