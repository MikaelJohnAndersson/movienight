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
            events: googleEvents,
            selectable: true,
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            dayClick: function(date, jsevent, view) {
                alert('clicked ' + date.format());
            },
            select: function(startDate, endDate) {
                alert('selected ' + startDate.format() + ' to ' + endDate.format());
            }
        });
    }

});

