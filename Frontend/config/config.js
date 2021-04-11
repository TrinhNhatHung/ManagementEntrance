function readTextFile(file, callback) {
    var rawFile = new XMLHttpRequest();
    rawFile.overrideMimeType("application/json");
    rawFile.open("GET", file, true);
    rawFile.onreadystatechange = function () {
        if (rawFile.readyState === 4 && rawFile.status == "200") {
            callback(rawFile.responseText);
        }
    }
    rawFile.send(null);
}

var domain;
var readHistory;
var readPerson;
var insertPerson;
var readVehicle;
var insertVehicle;
var login;
var authorize;
var history_image;

readTextFile("config/config.json", function (text) {
    var data = JSON.parse(text);
    domain = data.api.domain;
    readHistory = data.api.history_api.read;
    readPerson = data.api.person_api.read;
    insertPerson = data.api.person_api.insert;
    updatePerson = data.api.person_api.update;
    readVehicle = data.api.vehicle_api.read;
    insertVehicle = data.api.vehicle_api.insert;
    login = data.api.authenticate.login;
    authorize = data.api.authenticate.authorize;
    history_image = data.api.image.history;
});