/**
 * select -> option 자동으로 만들기
 */
function createSelectBoxAndOptions(selectId, listData) {
    var select = document.getElementById(selectId);
    var user_mbti = document.getElementById("user_mbti");
    for (var i in listData) {
        var option = document.createElement("option");
        option.text = listData[i].value;
        select.appendChild(option);
        if (user_mbti)
            if (select[i].value == user_mbti.value) {
                select[i].selected = true;
            }
    }
}

var listData = [
    {
        value: "INFP",
    },

    {
        value: "ENFP",
    },

    {
        value: "INFJ",
    },

    {
        value: "ENFJ",
    },

    {
        value: "INTJ",
    },

    {
        value: "ENTJ",
    },

    {
        value: "INTP",
    },

    {
        value: "ENTP",
    },

    {
        value: "ISFP",
    },

    {
        value: "ESFP",
    },

    {
        value: "ISTP",
    },

    {
        value: "ESTP",
    },

    {
        value: "ISFJ",
    },

    {
        value: "ESFJ",
    },

    {
        value: "ISTJ",
    },

    {
        value: "ESTJ",
    },
];

// console.log(listData);
// var divId = document.getElementById("mdvi");
// var select = createSelectBox("boxId", "boxName", listData);
// divId.appendChild(select);
createSelectBoxAndOptions('mbti', listData)
