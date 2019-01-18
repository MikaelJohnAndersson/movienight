$(document).ready(function () {

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/google/getevents',
        contentType: 'application/octet-stream; charset=utf-8',
        success: function(result) {
            //TODO: Error handling
            loadCalendar(result)
        }
    });
    let createMN = $('#create-movienight');

    function loadCalendar(googleEvents){
        $('#calendar').fullCalendar({
            events: googleEvents,
            selectable: true,
            header: {
                left: 'prev,next today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
          /*  dayClick: function(date, jsevent, view) {
                //alert('clicked ' + date.format());
                createMN.find('span.date').text(date.format());
            },*/
            select: function(startDate, endDate) {

                createMN.find('#start').text(new Date(startDate).toISOString());
                createMN.find('#end').text(new Date(endDate).toISOString());

                createMN.find('span.date').text(new Date(startDate).toDateString());
                createMN.find('span.time').text(new Date(startDate).toLocaleTimeString() + " - " + new Date(endDate).toLocaleTimeString());
            }
        });
    }

});

