$(document).ready(function () {

    $('#search').keyup(function () {
        var s = $('#search').val();
        $.getJSON('omdb/movies', {
            search: s
        })
            .done(function (data) {
                console.log("sökresultat: " + data)
                $(".searchResult.row").empty();
                if(data){
                    $.each(data["Search"], function (i, item) {
                        let movieInfo = `<div class="col-md-3">
                                <div class="card"> 
                                <div class="card-header">${item.Title}</div>
                         <img src="${item.Poster}" class="card-img-top" alt="${item.Title} poster" />
                         <button class="card-footer btn btn-sm" data-toggle="modal" data-target="#movie-modal" data-movie="${item.Title}">Läs mer</button>
                        </div>
                    </div>`;
                        $(".searchResult.row").append(movieInfo);
                    });
                }
            })
            .fail(function (error) {
                console.log(error);
            })
    });


    $('#movie-modal').on('show.bs.modal', function (event) {
        console.log("opening modal")
        let button = $(event.relatedTarget) // Button that triggered the modal
        let movieTitle = button.data('movie') // Extract info from data-* attributes
        $.getJSON('/omdb/movies/' + movieTitle)
            .done(function (data) {
                console.log("modal" + data)
                let modal = $('#movie-modal')
                modal.find('.modal-title').text(data.Title);
                modal.find('.modal-img').attr({
                    'src': data.Poster,
                    'alt': data.Title + "poster"
                });
                modal.find('p.plot').text(data.Plot);
                modal.find('p.actors').text(data.Actors);
                modal.find('p.director').text(data.Director);
                modal.find('p.writer').text(data.Writer);
                modal.find('p.genre').text(data.Genre);
                modal.find('p.year').text(data.Year);
                modal.find('button.btn-movie-add').attr('data-movie-add', data.Title);
            }).fail(function (error) {
            console.log(error);
        })
    })

    let newMovieNight = {};

    $('.btn-movie-add').on('click', function(e) {
        let chosenMovie = $('.btn-movie-add').data('movie-add');
        let createMN = $('#create-movienight');
        createMN.find('span.movies').text(chosenMovie);
        newMovieNight.movie = chosenMovie;

    })


});