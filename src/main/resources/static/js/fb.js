
var firebaseConfig = {
    apiKey: "AIzaSyARdEjb7_P-gg18hcxVi-9-mJ3xzCEEGdc",
    authDomain: "mbti-lovers-4b1ae.firebaseapp.com",
    databaseURL: "https://mbti-lovers-4b1ae-default-rtdb.firebaseio.com",
    projectId: "mbti-lovers-4b1ae",
    storageBucket: "mbti-lovers-4b1ae.appspot.com",
    messagingSenderId: "820993343179",
    appId: "1:820993343179:web:e933ea41b39d8cc500654e",
    measurementId: "G-2702BXJEFD"
};

// Initialize Firebase

firebase.initializeApp(firebaseConfig);

firebase.analytics();

// firebase에서 읽기

var demo = document.getElementById("demo");

var preObject = document.getElementById("object");

var dbRef = firebase.database().ref().child("Demo");

//dbRef.on('value',snap => demo.innerHTML = snap.val());

dbRef.on('value',snap => {

    preObject.innerText = JSON.stringify(snap.val(),null,3);

});