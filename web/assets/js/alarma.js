/**
 * Created by gmartin on 01/06/2016.
 * En el presente archivo JS se implementaran las peticiones GET contra el WS
 */
var status = false;
var URL_WS_ALARM_STATUS = "http://192.168.1.72:8080/app/rest/alarm/status";
var URL_WS_ALARM_ON = "http://192.168.1.72:8080/app/rest/alarm/on";
var URL_WS_ALARM_OFF = "http://192.168.1.72:8080/app/rest/alarm/off";
var URL_WS_TEMP_READ = "http://192.168.1.72:8080/app/rest/temperature/read";
var URL_WS_TEMP_LIMITS = "http://192.168.1.72:8080/app/rest/temperature/limits";
var URL_WS_TEMP_SET_LIMITS = "http://192.168.1.72:8080/app/rest/temperature/setlimits";


$(document).ready(function () {

    getAlarmStatusFromWS();

    var elem = document.querySelector('.js-switch');
    new Switchery(elem, { color: '#1AB394' });

    setInterval( function() {
        if(status){
            getConfigValuesFromWS();
            getTemperatureValueFromWS();
        }
    }, 1500);

});

function switchOnOff(){
    status = $("#switch_on_off").is();
    if(status) {
        powerOnAlarm();
    } else {
        powerOffAlarm();
    }
}

function getAlarmStatusFromWS() {
    $.get(URL_WS_ALARM_STATUS, function(data, status){
        // alert("Data: " + data + "\nStatus: " + status);
    });
}

function powerOnAlarm() {
    $.get(URL_WS_ALARM_ON, function(data, status){
        // alert("Data: " + data + "\nStatus: " + status);
    });
}

function powerOffAlarm() {
    $.get(URL_WS_ALARM_OFF, function(data, status){
        // alert("Data: " + data + "\nStatus: " + status);
    });
}

function getTemperatureValueFromWS() {
    $.get(URL_WS_TEMP_READ, function(data, status){
        // alert("Data: " + data + "\nStatus: " + status);
        $("#temperature").text(data);
    });
}

function getConfigValuesFromWS() {
    $.get(URL_WS_TEMP_LIMITS, function(data, status){
        // alert("Data: " + data + "\nStatus: " + status);
        $("#min-temp").text(data.min);
        $("#max-temp").text(data.max);
    });
}

function setConfigValuesInWS() {
    var min = $("#rangeMin").val();
    var max = $("#rangeMax").val();
    var URL = URL_WS_TEMP_SET_LIMITS + "?min=" + min + "&max=" + max;
    $.get(URL, function(data, status){
        // alert("Data: " + data + "\nStatus: " + status);
    });
}