var profileImage = document.getElementById('profileImage');
var input;


function handleFileSelect() {
    if (window.File && window.FileReader && window.FileList && window.Blob) {

    } else {
        alert('The File APIs are not fully supported in this browser.');
        return;
    }

    input = document.getElementById('fileinput');
    if (!input) {
        alert("Um, couldn't find the fileinput element.");
    } else if (!input.files) {
        alert("This browser doesn't seem to support the `files` property of file inputs.");
    }
        // else if (!input.files[0]) {
        // alert("Please select a file before clicking 'Load'");
    // }
    else {
        console.log("file upload");
        fileCheck(input);
        form = document.getElementById('imageform');
        if (form != null) {
            //DB에 파일명 저장
            if (profileImage)
                profileImage.value = fileName + "." + fileExt;
            console.log(profileImage.value)
            //파일 업로드
            form.target = "iframe1";
            form.submit();
            return true;
        }

    }

    return false;
}

var fileExt;
var fileName;

function fileCheck(input) {
    //파일 경로.
    var filePath = input.value;
    //전체경로를 \ 나눔.
    var filePathSplit = filePath.split('\\');
    //전체경로를 \로 나눈 길이.
    var filePathLength = filePathSplit.length;
    //마지막 경로를 .으로 나눔.
    var fileNameSplit = filePathSplit[filePathLength - 1].split('.');
    //파일명 : .으로 나눈 앞부분
    fileName = fileNameSplit[0];
    //파일 확장자 : .으로 나눈 뒷부분
    fileExt = fileNameSplit[1];
    //파일 크기
    var fileSize = input.files[0].size;

    console.log('파일 경로 : ' + filePath);
    console.log('파일명 : ' + fileName);
    console.log('파일 확장자 : ' + fileExt);
    console.log('파일 크기 : ' + fileSize);
}

