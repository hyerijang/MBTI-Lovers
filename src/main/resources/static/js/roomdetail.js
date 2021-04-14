"use strict";

var usernamePage = document.querySelector("#username-page");
var chatPage = document.querySelector("#chat-page");
var usernameForm = document.querySelector("#usernameForm");
var messageForm = document.querySelector("#messageForm");
var messageInput = document.querySelector("#message");
var messageArea = document.querySelector("#messageArea");
// var connectingElement = document.querySelector(".connecting"); //연결상태 표시
var stompClient = null;

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


//메세지 정보
var rid = document.querySelector("#rid").value.trim();
var sender = document.querySelector("#sender").value.trim();
var senderUid = document.querySelector("#senderUid").value.trim();
var currentTime = new Date().getTime();


//방 정보
// var output = null;
// var messageArray;
//채팅방 입장시 소켓 서버와 연결
window.onload = connect();
var messageRef;

function clearChatData() {
    //로컬스토리지 삭제
    localStorage.clear();
    console.log("로컬스토리지 삭제")
}

function connect() {

    // clearChatData();
    LoadFromFirebase();

    if (sender) {
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
    stompClient.subscribe("/sub/chat/room/" + rid, onMessageReceived);
    setCurrentTime();
    // stompClient.send(
    //     "/pub/chat.sendMessage",
    //     {},
    //     JSON.stringify({
    //         rid: rid,
    //         type: "JOIN",
    //         sender: sender,
    //         senderUid: senderUid,
    //         sentTimeAt: currentTime
    //     })
    // );
}

function onError(error) {
    // connectingElement.textContent = "Could not connect to WebSocket server. Please refresh this page to try again!";
    // connectingElement.style.color = "red";
}

//메세지 전송
function sendMessage(event) {
    setCurrentTime();
    var messageContent = messageInput.value.trim();
    if (messageContent && stompClient) {
        var chatMessage = {
            rid: rid,
            type: "CHAT",
            content: messageInput.value,
            sender: sender,
            senderUid: senderUid,
            sentTimeAt: currentTime
        };
        stompClient.send("/pub/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = "";
        SaveToFirebase(chatMessage);
        event.preventDefault();
    }

    event.preventDefault();

}

//메세지 수신
function onMessageReceived(payload) {
    //메세지 출력
    //받은 메세지를 json형태로 배열에 저장
    var message = JSON.parse(payload.body);

}

function printMessage(message) {
    console.log(message);

    var messageElement = document.createElement("div");

    var mediaBody = document.createElement("div");
    mediaBody.classList.add("media-body");

    if (message.type === "JOIN") {
        messageElement.classList.add("media", "media-meta-day"); // 임시 적용 css
        message.content = message.sender + "님이 들어왔습니다.";
    } else if (message.type === "LEAVE") {
        messageElement.classList.add("media", "media-meta-day"); // 임시 적용 css
        message.content = message.sender + "님이 나갔습니다.";
    } else if (sender == message.sender) {
        //메세지를 보낸 사람이 나인 경우
        messageElement.classList.add("media", "media-chat", "media-chat-reverse");
    } else {
        //다른 사람이 보낸 메세지의 경우
        messageElement.classList.add("media", "media-chat");

        //이름
        var avatarElement = document.createElement("i");
        var avatarText = document.createTextNode(message.sender);
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

messageForm.addEventListener("submit", sendMessage, true);


function SaveToFirebase(chatMessage) {

    var messageRefKey = messageRef.push().key; // 메세지 키값 구하기
    firebase.database().ref('Room/' + rid + '/' + messageRefKey).set(chatMessage);

}

function LoadFromFirebase() {
    if (rid) {
        if (messageRef)
            messageRef.off();//이전 메세지 ref 이벤트 제거

        messageRef = firebase.database().ref('Room/' + rid);

        messageRef.on('value', (snapshot) => {
            snapshot.forEach((childSnapshot) => {
                var childKey = childSnapshot.key;
                // console.log(childSnapshot.val());
                printMessage(childSnapshot.val());
            });
        });


    }
}


function setCurrentTime() {
    currentTime = new Date().getTime();

}