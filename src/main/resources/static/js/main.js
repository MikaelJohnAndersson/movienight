var vm = new Vue({
    el: '#nav',
    data: {
        user: 'username',
    }
});




$('#search').keyup(function() {
    var s = $('#search').val();
    $.getJSON('omdb/movies', {
        search: s
    })
        .done(function( data ) {
            $.each( data["Search"], function( i, item ) {
                console.log(item);
            });
        })
    .fail(function(error) {
        console.log(error);
    })
});