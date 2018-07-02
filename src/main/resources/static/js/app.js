// STOMP Client
var stompClient = null;

// Endpoints Constants
var restEndpoint = "/app/string/reverse";
var webSocketEndpoint = "/websocket";
var topicSubscription = "/topic/strings";

/**
 * Socket connection via SockJS handler
 */
function connect() {
    var socket = new SockJS(webSocketEndpoint);
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function () {

        // Subscribe for topic
        stompClient.subscribe(topicSubscription, function (response) {
            var responseJSON = JSON.parse(response.body);

            showReversedString(responseJSON.lastString, responseJSON.allStrings);
        });


        // No errors. Set status to Connected in 2 sec
        setTimeout(setConnectionStatusToConnected, 2000);
    });
}

/**
 * Connection status handler
 */
function setConnectionStatusToConnected() {
    var connection = $("#connection");

    connection.html("Connected");
    connection.css("background-color","lightgreen");
}

/**
 * 'Send' button handler
 */
function sendString() {
    stompClient.send(restEndpoint, {}, JSON.stringify({'value' : $("#value").val()}));
}

/**
 * Response handler
 */
function showReversedString(lastString, allStrings) {
    $("#strings").append("<tr><td> String successfully saved as: <b>" + lastString + "</b>. Strings in Database: <b>" + allStrings + "</b></td></tr>");
}

/**
 * On page load handler
 */
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $("#connect").click(function () {
        connect();
    });

    $("#send").click(function () {
        sendString();
    });

    // Connect to Socket on Page Load
    connect();
});

