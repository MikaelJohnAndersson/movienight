$( document ).ready(function() {

    //OAuth2 client
    var auth2;

    //Initialize the OAuth2 client with our client credentials
    gapi.load('auth2', function() {
        auth2 = gapi.auth2.init({
            //TODO: Hide client id
            client_id: '210833549992-v1apmfej0r5na34nb3nfah0h3c99sgk4.apps.googleusercontent.com',
            //Other than email and profile, also requesting Google Calendar scope
            scope: 'https://www.googleapis.com/auth/calendar'
        });
    });


    $('#signinButton').on('click', function() {
        //Requesting offline access in order to use user data while user is offline
        auth2.grantOfflineAccess().then(signInCallback);
    });

    function signInCallback(authResult) {
        if (authResult['code']) {

            // Hide the sign-in button now that the user is authorized, for example:
            //TODO: Frontend
            $('#signinButton').attr('style', 'display: none');

            // Send the code to the server
            $.ajax({
                type: 'POST',
                url: 'http://localhost:8080/google/auth',
                // Always include an `X-Requested-With` header in every AJAX request,
                // to protect against CSRF attacks.
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                },
                contentType: 'application/octet-stream; charset=utf-8',
                success: function(result) {
                    //TODO: Error handling
                    console.log(result)
                },
                processData: false,
                data: authResult['code']
            });
        } else {
            //TODO: Error handling
            // There was an error.
        }
    }



});


