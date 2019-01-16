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
                         <button class="card-footer btn btn-sm" data-toggle="modal" data-target="#movie-modal" data-movie="${item.Title}">Läs mer</button>
                        </div>
                    </div>`;
                $(".searchResult").append(movieInfo);
            });
        })
    .fail(function(error) {
        console.log(error);
    })
});




$('#movie-modal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget) // Button that triggered the modal
    let movieTitle = button.data('movie') // Extract info from data-* attributes
    $.getJSON('omdb/movies/'+ movieTitle)
        .done(function(data) {
            let modal = $('#movie-modal')
            modal.find('.modal-title').text(data.Title);
            modal.find('.modal-img').attr({
                'src': data.Poster,
                'alt': data.Title + "poster"
            })
            modal.find('p.plot').text(data.Plot);
            modal.find('p.actors').text(data.Actors);
            modal.find('p.director').text(data.Director);
            modal.find('p.writer').text(data.Writer);
            modal.find('p.genre').text(data.Genre);
            modal.find('p.year').text(data.Year);
        })
})