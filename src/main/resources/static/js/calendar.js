$(document).ready(function () {

    $.ajax({
        type: 'Get',
        url: 'http://localhost:8080/google/getevents',
        contentType: 'application/octet-stream; charset=utf-8',
        success: function(result) {
            //TODO: Error handling
            console.log(result)
            loadCalendar(result)
        }
    });

    function loadCalendar(googleEvents){
        console.log(googleEvents);
        let calendarEvents = googleEvents['0'].items.map(event => ({
            start: event['start'].toISOString(),
            end: event['end'].toISOString()
        }));
        $('#calendar').fullCalendar({
            events: calendarEvents
        });
    }

});