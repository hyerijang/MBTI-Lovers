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
    } else if (!input.files[0]) {
        alert("Please select a file before clicking 'Load'");
    } else {
        console.log("file upload");
        form = document.getElementById('imageform');
        if (form != null) {
            //파일 업로드
            form.target = "iframe1";
            form.submit();
            return true;
        }

    }
    
    return false;
}

