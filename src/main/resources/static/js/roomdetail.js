"use strict";

var usernamePage = document.querySelector("#username-page");
var chatPage = document.querySelector("#chat-page");
var usernameForm = document.querySelector("#usernameForm");
var messageForm = document.querySelector("#messageForm");
var messageInput = document.querySelector("#message");
var messageArea = document.querySelector("#messageArea");
// var connectingElement = document.querySelector(".connecting"); //연결상태 표시


var stompClient = null;
var username = null;

var rid = document.getElementById('rid').innerText;

var colors = [
    "#2196F3",
    "#32c787",
    "#00BCD4",
    "#ff5652",
    "#ffc107",
    "#ff85af",
    "#FF9800",
    "#39bbb0",
];

//채팅방 입장시 소켓 서버와 연결
window.onload = connect();
function connect() {
    username = document.querySelector("#sender").value.trim();

    if (username) {
        usernamePage.classList.add("hidden");
        chatPage.classList.remove("hidden");

        var socket = new SockJS("/ws-stomp");
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    // event.preventDefault();
}

function onConnected() {
    // room 개설
    stompClient.subscribe("/sub/chat/room/" +rid, onMessageReceived);

    // Tell your username to the server
    stompClient.send(
        "/sub/chat/room/"+rid,
        {},
        JSON.stringify({sender: username, type: "JOIN"})
    );

    // connectingElement.classList.add("hidden");
}

function onError(error) {
    // connectingElement.textContent = "Could not connect to WebSocket server. Please refresh this page to try again!";
    // connectingElement.style.color = "red";
}

function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: "CHAT",
        };
        stompClient.send("/sub/chat/room/" +rid, {}, JSON.stringify(chatMessage));
        messageInput.value = "";
    }
    event.preventDefault();
}

function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement("div");

    var mediaBody = document.createElement("div");
    mediaBody.classList.add("media-body");

    if (message.type === "JOIN") {
        messageElement.classList.add("media", "media-meta-day"); // 임시 적용 css
        message.content = message.sender + "님이 들어왔습니다.";
    } else if (message.type === "LEAVE") {
        messageElement.classList.add("media", "media-meta-day"); // 임시 적용 css
        message.content = message.sender + "님이 나갔습니다.";
    } else if (username == message.sender) {
        //메세지를 보낸 사람이 나인 경우
        messageElement.classList.add("media", "media-chat", "media-chat-reverse");
    } else {
        //다른 사람이 보낸 메세지의 경우
        messageElement.classList.add("media", "media-chat");

        //이름
        var avatarElement = document.createElement("i");
        var avatarText = document.createTextNode(message.sender[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style["background-color"] = getAvatarColor(message.sender);
        messageElement.appendChild(avatarElement);

        //프로필 이미지
        var imageElement = document.createElement("img");
        imageElement.classList.add("avatar");
        imageElement.src =
            "https://img.icons8.com/color/36/000000/administrator-male.png"; //임시
        messageElement.appendChild(imageElement);
    }

    var textElement = document.createElement("p");
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(mediaBody);
    mediaBody.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}

function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }
    var index = Math.abs(hash % colors.length);
    return colors[index];
}

// usernameForm.addEventListener("submit", connect, true);
messageForm.addEventListener("submit", sendMessage, true);
