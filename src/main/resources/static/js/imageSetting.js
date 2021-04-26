document.querySelector('.custom-file-input').addEventListener('change', function (e) {
    var name = document.getElementById("fileinput").files[0].name;
    var nextSibling = e.target.nextElementSibling
    nextSibling.innerText = name
})

var previewImage = document.getElementById("preview-image")
var profileImgFileName = document.getElementById("profileImage").value;
var profileImgPath = '/images/' + profileImgFileName;
var defaultProfileImgPath = '/images/defaultProfileImage.png';

setProfileImage()
function setProfileImage() {
    var img = new Image();
    img.src = profileImgPath;

    //기본 이미지
    previewImage.src = profileImgPath;

    //기본 이미지 없는 경우
    previewImage.onerror = function () {
        console.log(profileImgPath);
        console.log("서버에 파일이 존재하지 않아 기본 프로필 이미지로 대체합니다")
        previewImage.src = defaultProfileImgPath;
    };


}

function readImage(input) {
    // 인풋 태그에 파일이 있는 경우
    if (input.files && input.files[0]) {
        // 이미지 파일인지 검사 (생략)
        // FileReader 인스턴스 생성
        const reader = new FileReader()
        // 이미지가 로드가 된 경우
        reader.onload = e => {
            previewImage = document.getElementById("preview-image")
            previewImage.src = e.target.result
        }
        // reader가 이미지 읽도록 하기
        reader.readAsDataURL(input.files[0])
    }
}

// input file에 change 이벤트 부여
const inputImage = document.getElementById("fileinput")
inputImage.addEventListener("change", e => {
    readImage(e.target)
})