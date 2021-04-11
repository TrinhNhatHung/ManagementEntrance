var rowPerPage = $('.row-per-page').val() ;
function getCurrentPage (){
    let currentPage = 1; 
    let queryString = location.href;
    currentPage = queryString.substr(queryString.lastIndexOf('#') +1, queryString.length);
    if (queryString.lastIndexOf('#') == -1){
        return 1;
    }
    if (currentPage == undefined) {
        currentPage = 1;
    }
    return currentPage;
}
function renderPagination (length){
    rowPerPage = $('.row-per-page').val();
    let amountPages = Math.ceil( length / rowPerPage );
    let currentPage = getCurrentPage();
    let listPages =[];
    for (let i = 0; i<= amountPages +1; i++){
        listPages.push(i);
    }   
    ReactDOM.render(
        <Paging pages={listPages} currentPage ={currentPage}  amountPages={amountPages} />, document.getElementById("paging-area"));
    activePage();
}

function renderData (data){
    let currentPage = getCurrentPage();
    let start = rowPerPage * (currentPage - 1);
    let end = rowPerPage * currentPage;
    return data.slice(start, end);
}

function activePage (){
    let currentPage = getCurrentPage();
    $('.page-item .page-link').removeClass('active');
    $('.page-item .page-link.' + currentPage).addClass('active');
}