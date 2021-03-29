function createSelectBox(id, name, listData) {
  var select = document.createElement("select");
  select.id = id;
  select.name = name;

  //최상위 전체 option 삽입
  var option = document.createElement("option");
  option.text = "MBTI";
  select.appendChild(option);

  for (var i in listData) {
    var option = document.createElement("option");
    option.text = listData[i].value;
    select.appendChild(option);
  }

  return select;
}

/**
 * select -> option 자동으로 만들기
 */
function createSelectBoxAndOptions(selectId, listData) {
  var select = document.getElementById(selectId);
  for (var i in listData) {
    var option = document.createElement("option");
    option.text = listData[i].value;
    select.appendChild(option);
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

console.log(listData);
// var divId = document.getElementById("mdvi");
// var select = createSelectBox("boxId", "boxName", listData);
// divId.appendChild(select);
createSelectBoxAndOptions('mbti', listData)
