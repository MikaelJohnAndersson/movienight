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
            $(".searchResult").empty();
            $.each( data["Search"], function( i, item ) {
                let movieInfo =`<div class="col-md-3">
                                <div class="card"> 
                                <div class="card-header">${item.Title}</div>
                         <img src="${item.Poster}" class="card-img-top" alt="${item.Title} poster">
                         <a href="/omdb/movies/${item.Title}" class="card-footer btn btn-sm">Läs mer</a>
                        </div>
                    </div>`;
                $(".searchResult").append(movieInfo);
            });
        })
    .fail(function(error) {
        console.log(error);
    })
});