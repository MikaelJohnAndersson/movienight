$(document).ready(function () {

    $.ajax({
        type: 'Get',
        url: 'http://localhost:8080/user/authenticated',
        success: function(result) {
            if(result === true){
                $('#signinButton').addClass("d-none")
            }
        }
    });

    $('#search').keyup(function () {
        var s = $('#search').val();
        $.getJSON('omdb/movies', {
            search: s
        })
            .done(function (data) {
                $(".searchResult.row").empty();
                if(data){
                    $.each(data["Search"], function (i, item) {
                        if (item.Poster === "N/A") {
                            item.Poster = "https://via.placeholder.com/300x446?text=No+image+available"
                        }
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
        let button = $(event.relatedTarget) // Button that triggered the modal
        let movieTitle = button.data('movie') // Extract info from data-* attributes
        $.getJSON('/omdb/movies/' + movieTitle)
            .done(function (data) {
                let modal = $('#movie-modal')
                modal.find('.modal-title').text(data.Title);
                if (data.Poster === "N/A") {
                    data.Poster = "https://via.placeholder.com/300x446?text=No+image+available"
                }
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

    $('.btn-movie-add').on('click', function(e) {
        let chosenMovie = $('.btn-movie-add').data('movie-add');
        let createMN = $('#create-movienight');
        createMN.find('span.movie').text(chosenMovie);
    })

    //TODO: Fixa fulhack
    $('.btn-post-movienight').on('click', function (e) {

        let createMN = $('#create-movienight');

        let movienight = {}
        movienight.event = {
            start: createMN.find('#start').text(),
            end: createMN.find('#end').text(),
            title: createMN.find('span.movie').text()
        };

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/movienight',
            contentType: 'application/json',
            success: function(result) {
                alert("Filmkväll bokad!\nFilm: " + result.event.title + "\nMedlemmar: " + result.members.join(', ') + "\nHa så kul :)");
            },
            error: function (jqXHR, exception) {
                if(jqXHR.status == 401){
                    alert("Please authenticate with Google in order to create a movienight!")
                }
                if(jqXHR.status == 500){
                    alert("Någonting gick fel. Vänligen säkerställ att alla fält är ifyllda.")
                }
            },
            data: JSON.stringify(movienight)
        });

    })

});