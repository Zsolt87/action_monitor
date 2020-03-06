'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectButton = document.querySelector('#connectButton');

var stompClient = null;
var username = null;

function connect(event) {

    username = document.querySelector('#name').value.trim();

    if(username) {
        var socket = new SockJS('/actionmonitor');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}

function onConnected() {
    stompClient.subscribe('/topic/public', onMessageReceived);
    stompClient.send("/app/actionmonitor.register", {}, JSON.stringify({sender: username, type: 'JOIN'}))
    connectButton.style.backgroundColor = 'limegreen';
}

function onError(error) {
    connectButton.style.backgroundColor = 'red';
}

function send(event) {
    var messageContent = messageInput.value.trim();

    if(messageContent && stompClient) {
        var message = {
            sender: username,
            content: messageInput.value,
            type: 'REGULAR'
        };

        stompClient.send("/app/actionmonitor.send", {}, JSON.stringify(message));
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        message.content = message.sender + ' joined!';
        messageElement.appendChild(document.createTextNode(message.content));
    } else {
        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode("Message from " + message.sender + ":" + message.content);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', send, true)