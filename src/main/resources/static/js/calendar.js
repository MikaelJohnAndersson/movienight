$(document).ready(function () {

    $.ajax({
        type: 'Get',
        url: 'http://localhost:8080/google/getevents',
        contentType: 'application/octet-stream; charset=utf-8',
        success: function(result) {
            //TODO: Error handling
            loadCalendar(result)
        }
    });

    function loadCalendar(googleEvents){
        $('#calendar').fullCalendar({
            events: googleEvents
        });
    }

});