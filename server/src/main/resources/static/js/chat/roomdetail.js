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
var userName = document.querySelector("#sender").value.trim();
var userUid = document.querySelector("#senderUid").value.trim();
var currentTime = new Date().getTime();

//상대방 이름
var fname = document.getElementById("fname").value;

//상대방 프로필 이미지
var profileImgFileName = document.getElementById("f_profileImage").value;
var fid = document.getElementById("fid").value;
var relation = document.getElementById("f_relation").value;


//채팅방 입장시 소켓 서버와 연결
window.onload = connect();
var messageRef;


function connect() {

    //이전 메세지 로드
    LoadAllMessageFromFirebase();

    //친구관계인 경우에만 서로 채팅 가능
    if (relation == "FRIEND") {
        //소켓서버와 연결
        if (userName) {
            usernamePage.classList.add("hidden");
            chatPage.classList.remove("hidden");

            var socket = new SockJS("/ws-stomp");
            stompClient = Stomp.over(socket);

            stompClient.connect({}, onConnected, onError);
        }
    }
    else {
        console.log("친구관계가 아니므로 채팅을 보낼 수 없습니다.")
    }
    // event.preventDefault();
}

function onConnected() {
    // room 입장
    stompClient.subscribe("/sub/chat/room/" + rid, onMessageReceived);
    setCurrentTime();
}

function onError(error) {
    connectingElement.textContent = "Could not connect to WebSocket server. Please refresh this page to try again!";
    connectingElement.style.color = "red";
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
            sender: userName,
            senderUid: userUid,
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
    var message = JSON.parse(payload.body);
    printMessage(message);
    // LoadlastMessageFromFirebase();

}

var lastSender = null;
var index;
var lastMessageLocalDate = "";//이전에 읽은 메세지의 날짜
var lastMessageTime = ""; //이전에 읽은 메세지의 시분

function printMessage(message) {
    var messageElement;
    var mediaBody;
    var metaElement;


    var date = new Date(message.sentTimeAt);
    var messageLocalDate = date.toLocaleDateString().trim();


    //날짜 표시 선
    if (messageLocalDate !== lastMessageLocalDate) {
        messageElement = document.createElement("div");
        messageElement.classList.add("media", "media-meta-day"); // 임시 적용 css
        messageArea.appendChild(messageElement);

        mediaBody = document.createElement("div");
        mediaBody.classList.add("media-body");
        messageElement.appendChild(mediaBody);

        var textElement = document.createElement("p");
        mediaBody.appendChild(textElement);

        var messageText = document.createElement("p");
        messageText.classList.add("date-text");

        var todayLocalDate = new Date().toLocaleDateString().trim();
        if (messageLocalDate == todayLocalDate)
            messageText.innerText = "오늘";
        else
            messageText.innerText = messageLocalDate;

        textElement.appendChild(messageText);
        //시간 및 발신자 갱신
        lastMessageLocalDate = messageLocalDate;
        lastSender = null;
    }


    var strArray = date.toLocaleTimeString().replace(/:\d+ /, ' ').split(':');
    var messageTime = strArray[0] + ":" + strArray[1];


    if (lastSender == message.sender && lastMessageTime == messageTime) {
        messageElement = messageArea.lastChild;
        mediaBody = messageElement.childNodes[index - 1];
        metaElement = mediaBody.lastChild;

    } else {
        //새로운 그룹 생성
        messageElement = document.createElement("div");
        //상대방이 보낸 메세지의 경우 이름과 프로필 사진 표시
        if (userUid !== message.senderUid) {
            //이름
            var nameElement = document.createElement("p");
            var avatarText = document.createTextNode(fname);
            nameElement.appendChild(avatarText);
            nameElement.classList.add("sender-name");
            messageArea.appendChild(nameElement);

            //프로필 이미지
            var imageElement = document.createElement("img");
            imageElement.classList.add("avatar");
            setProfileImage(imageElement, fid, profileImgFileName);
            messageElement.appendChild(imageElement);
        }
        messageArea.appendChild(messageElement);


        //미디어바디 (텍스트 그룹) 생성
        mediaBody = document.createElement("div");
        mediaBody.classList.add("media-body");
        messageElement.appendChild(mediaBody);

        //메타태그(시간) 생성
        metaElement = document.createElement("p");
        metaElement.classList.add("meta-time");
        metaElement.textContent = messageTime;
        mediaBody.appendChild(metaElement);

        index = messageElement.childNodes.length;
    }


    if (userUid === message.senderUid) {
        //메세지를 보낸 사람이 나인 경우
        messageElement.classList.add("media", "media-chat", "media-chat-reverse");
    } else {
        //상대방이 보낸 메세지의 경우
        messageElement.classList.add("media", "media-chat");
    }

    var textElement = document.createElement("p");
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);
    mediaBody.insertBefore(textElement, metaElement);

    //스크롤 범위
    messageArea.scrollTop = messageArea.scrollHeight;
    //마지막 발신자 설정
    lastSender = message.sender;
    lastMessageLocalDate = messageLocalDate;
    lastMessageTime = messageTime;
}

//
// 메세지 저장 관련
//

messageForm.addEventListener("submit", sendMessage, true);

function SaveToFirebase(chatMessage) {

    var messageRefKey = messageRef.push().key; // 메세지 키값 구하기
    firebase.database().ref('Room/' + rid + '/' + messageRefKey).set(chatMessage);

}

function LoadAllMessageFromFirebase() {
    if (rid) {
        if (messageRef)
            messageRef.off();//이전 메세지 ref 이벤트 제거

        messageRef = firebase.database().ref('Room/' + rid);

        messageRef.once('value', (snapshot) => {
            snapshot.forEach((childSnapshot) => {
                var childKey = childSnapshot.key;
                // console.log(childSnapshot.val());
                printMessage(childSnapshot.val());
            });
        });

    }
}


function LoadlastMessageFromFirebase() {
    if (rid) {

        messageRef.limitToLast(1).once('value').then((snapshot) => {

            snapshot.forEach(function (child) {
                var message = child.val();
                console.log(message);
                printMessage(message);
            });

        });


    }
}

function setCurrentTime() {
    currentTime = new Date().getTime();
}

var state = null;
var title = null;
var url = location.href;

history.pushState(state, title, url);

function setProfileImage(previewImage, uid, profileImgFileName) {
    var profileImgPath = S3url + encodeURI(profileImgFileName);
    //기본 이미지
    previewImage.src = profileImgPath;

    //기본 이미지 없는 경우
    previewImage.onerror = function () {
        console.log("서버에 파일이 존재하지 않아 기본 프로필 이미지로 대체합니다")
        previewImage.src = defaultProfileImgPath;
    };


}
