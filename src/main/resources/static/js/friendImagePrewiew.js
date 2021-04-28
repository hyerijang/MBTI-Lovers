const S3url = 'https://mbti-image.s3.ap-northeast-2.amazonaws.com/image/';
const defaultProfileImgPath = S3url + 'defaultProfileImage.png';

let previewImages = document.querySelectorAll("#preview-image");
let uidlist = document.querySelectorAll("#uid");
let profileImgFileNameList = document.querySelectorAll("#profileImage");

var uid;

for (let i = 0; i < previewImages.length; i++) {
    let previewImage = previewImages.item(i);
    uid = uidlist.item(i).value;
    let profileImgFileName = profileImgFileNameList.item(i).value;

    let profileImgPath = S3url + uid + '/' + encodeURI(profileImgFileName);
    setFriendProfileImage(previewImage, profileImgPath);

}

function setFriendProfileImage(previewImage, profileImgPath) {

    //기본 이미지
    previewImage.src = profileImgPath;

    //기본 이미지 없는 경우
    previewImage.onerror = function () {
        console.log(uid + "의 프로필 이미지가 존재하지 않아 기본 프로필 이미지로 대체합니다")
        previewImage.src = defaultProfileImgPath;
    };

    // console.log(defaultProfileImgPath);
    return true;

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