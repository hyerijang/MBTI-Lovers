
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
    value: "남자",
  },

  {
    value: "여자",
  }
];

console.log(listData);
createSelectBoxAndOptions("gender", listData)
